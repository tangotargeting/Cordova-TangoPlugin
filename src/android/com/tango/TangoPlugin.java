package com.tango.cordova;

import android.app.Application;
import android.content.Context;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import java.util.Set;
import java.util.HashSet;

import com.tango.core.Tango;
import com.tango.custom.trigger.TangoAutomation;

public class TangoPlugin extends CordovaPlugin {

	private static final String TAG = "TangoPlugin";

	public void initialize(CordovaInterface cordova, CordovaWebView webView){
		super.initialize(cordova, webView);
	}

	public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException{
		Log.d(TAG, "TangoPlugin action => " + action);

		if("initializeTango".equals(action)){
			//Tango.init((Application)(webView.getContext().getApplicationContext()));
			// Tango is self initialized on Android
			return true;
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