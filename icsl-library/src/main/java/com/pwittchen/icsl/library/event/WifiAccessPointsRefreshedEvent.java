package com.pwittchen.icsl.library.event;

import android.content.Context;
import android.util.Log;

import com.pwittchen.icsl.library.helper.NetworkHelper;

/**
 * Event pushed to Otto Event Bus when List of WiFi Access Points was refreshed
 */
public class WifiAccessPointsRefreshedEvent {
    private final static String TAG = "InternetConnectionStateListener";

    public WifiAccessPointsRefreshedEvent(Context context) {
        // we need to start WiFi scan after refreshing access points
        // in order to get fresh access points list
        NetworkHelper.startWifiScan(context);
        Log.d(TAG, "WifiAccessPointsRefreshedEvent");
    }
}
