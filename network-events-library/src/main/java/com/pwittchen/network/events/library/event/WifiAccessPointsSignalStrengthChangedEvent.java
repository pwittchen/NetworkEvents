package com.pwittchen.network.events.library.event;

import android.content.Context;
import android.util.Log;

import com.pwittchen.network.events.library.helper.NetworkHelper;

/**
 * Event pushed to Otto Event Bus when List of WiFi Access Points was refreshed
 * and their signal strength was changed
 */
public class WifiAccessPointsSignalStrengthChangedEvent {
    private final static String TAG = "NetworkEvents";

    public WifiAccessPointsSignalStrengthChangedEvent(Context context) {
        // we need to start WiFi scan after refreshing access points
        // in order to get fresh access points list
        NetworkHelper.startWifiScan(context);
        Log.d(TAG, "WifiAccessPointsSignalStrengthChangedEvent");
    }
}
