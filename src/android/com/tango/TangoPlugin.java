package com.tango.cordova;

import android.app.Application;
import android.content.Context;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import android.support.v4.content.LocalBroadcastManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.Intent;

import android.util.Log;

import java.util.Set;
import java.util.HashSet;

import com.tango.core.Tango;
import com.tango.custom.trigger.TangoAutomation;

public class TangoPlugin extends CordovaPlugin {

	private static final String TAG = "TangoPlugin";

	private static final String KEY_CTA = "com.tangotargeting.intent.extra.CTA";

	private static final String ACTION_CTA = "com.tangotargeting.intent.action.CUSTOM_CTA";

	private BroadcastReceiver ctaReceiver;

	public void initialize(CordovaInterface cordova, CordovaWebView webView){
		super.initialize(cordova, webView);

		ctaReceiver = new BroadcastReceiver(){

			@Override 
			public void onReceive(Context context, Intent intent) {
				final String action = intent.getStringExtra(KEY_CTA);
				Log.d(TAG, "TangoPlugin Custom Action Received => " + action);
				if (action != null) {
					final String method = String.format("javascript:window.TangoPlugin.onCustomAction('%s');", action);
					TangoPlugin.this.cordova.getActivity().runOnUiThread( new Runnable() {
			            @Override
			            public void run() {
			                TangoPlugin.this.webView.loadUrl(method);
			                Log.d(TAG, "TangoPlugin Custom Action Executed in WebView => " + action);
			            }
			        });
				}
			}
		};

		registerReceiver(ctaReceiver, new IntentFilter(ACTION_CTA));
	}

	public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException{
		Log.d(TAG, "TangoPlugin action => " + action);

		if("initializeTango".equals(action)){
			return true; // Tango is self initialized on Android
		}else if("trigger".equals(action)){
			String triggerPhrase = args.getString(0);
			Log.d(TAG, "TangoPlugin trigger => " + triggerPhrase);
			
			int result = TangoAutomation.trigger(triggerPhrase);

			if(result == TangoAutomation.CAMPAIGN_TRIGGERED){
				callbackContext.success("Campaign Triggered Successfully!");
				return true;
			}else{
				callbackContext.error("Campaign Failed to trigger with code: " + result);
				return false;
			}
		}else if("addSegments".equals(action)){
			Set<String> tags;
			JSONArray jsonTags = args.getJSONArray(0);

			try{
				tags = toStringSet(jsonTags);
			}catch(JSONException e){
				callbackContext.error(e.getMessage());
				return false;
			}

			Tango.addTags(tags);

			callbackContext.success(jsonTags);
		}

		return true;
	}

	protected void registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
		super.webView.getContext().registerReceiver(receiver, filter);

		Log.d(TAG, "TangoPlugin Custom Call to Action Receiver registered!");
        //LocalBroadcastManager.getInstance(super.webView.getContext()).registerReceiver(receiver, filter);
    }

    protected void unregisterReceiver(BroadcastReceiver receiver) {
    	super.webView.getContext().unregisterReceiver(receiver);

		Log.d(TAG, "TangoPlugin Custom Call to Action Receiver unregistered!");
        //LocalBroadcastManager.getInstance(super.webView.getContext()).unregisterReceiver(receiver);
    }

	public void onDestroy() {
		unregisterReceiver(ctaReceiver);
    }

	private Set<String> toStringSet(JSONArray jsonArray) throws JSONException{
        Set<String> tags = new HashSet<String>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                tags.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tags;
    }
}