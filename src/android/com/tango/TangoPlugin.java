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

import java.util.Set;

import com.tango.Tango;
import com.tango.custom.trigger.TangoAutomation;

public class TangoPlugin extends CordovaPlugin {

	private static final String TAG = "TangoPlugin";

	public void initialize(CordovaInterface cordova, CordovaWebView webView){
		super.initialize(cordova, webView);
	}

	public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException{
		if("initialize".equals(action)){
			Tango.initialize((Application)(webView.getContext().getApplicationContext()));
		}else if("trigger".equals(action)){
			String triggerPhrase = args.getString(0);
			int result = TangoAutomation.trigger(triggerPhrase);

			if(result == TangoAutomation.CAMPAIGN_TRIGGERED){
				callbackContext.success("Campaign Triggered Successfully!");
			}else{
				callbackContext.error("Campaign Failed to trigger with code: " + result);
			}
		}else if("addSegment".equals(action)){
			//Set<String> segments = new Set<>();
			JSONArray[] tags = args.getJSONArray(0);
			
		}else if("removeSegment".equals(action)){

		}else if("getSegments".equals(action)){

		}
	}
}