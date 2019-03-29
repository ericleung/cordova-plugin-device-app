package me.ericdev.cordova.plugins.deviceapp;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public class DeviceAppPlugin extends CordovaPlugin {
	
	private Bundle extras;
    public static String TAG = "CordovaActivity";
	
	@Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        
//        Context context = this.cordova.getActivity().getApplicationContext();
//		downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        this.extras = this.cordova.getActivity().getIntent().getExtras();

    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        if ("authCode".equals(action)) {
            JSONObject r = new JSONObject();

            if(this.extras != null){

                Log.d(TAG, "extras 不为空");

                r.put("authCode", this.extras.getString("authCode", "NULL"));
            }else{

                Log.d(TAG, "extras 为空");
                r.put("authCode", "NULL");
            }

            callbackContext.success(r);
        } else if ("messageCount".equals(action)) {

            JSONObject result = new JSONObject();

            try{
                if (args != null && args.get(0) != null) {
                    Integer messageCount = args.getInt(0);
                    Intent intent = new Intent("com_gree_device_unread_message");
                    intent.putExtra("com_gree_device_packagename", "com.gree.deviceApp");
                    intent.putExtra("com_gree_device_classname", "com.gree.deviceApp.MainActivity");
                    intent.putExtra("com_gree_device_unread_count", messageCount);
                    this.cordova.getActivity().sendBroadcast(intent);

                    result.put("messageCount", messageCount);
                    result.put("success", true);
                } else {
                    result.put("error", "messageCount is null");
                    result.put("success", false);
                }
                callbackContext.success(result);
            }catch (Exception ex) {
                result.put("error", ex.getMessage());
                result.put("success", false);
                callbackContext.success(result);
            }


        } else {
            return false;
        }
        return true;

    	
    }
}
