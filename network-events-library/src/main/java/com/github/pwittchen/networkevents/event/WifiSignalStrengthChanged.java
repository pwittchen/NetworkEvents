package com.github.pwittchen.networkevents.event;

import android.content.Context;
import android.util.Log;

import com.github.pwittchen.networkevents.NetworkEventsConfig;
import com.github.pwittchen.networkevents.NetworkHelper;

/**
 * Event pushed to Otto Event Bus when Wifi Signal strength was changed
 * and list of WiFi Access Points was refreshed
 */
public class WifiSignalStrengthChanged {
    public WifiSignalStrengthChanged(Context context) {
        /**
         * We need to start WiFi scan after refreshing access points
         * in order to get fresh access points list
         */
        NetworkHelper.startWifiScan(context);
        Log.d(NetworkEventsConfig.TAG, "WifiSignalStrengthChanged");
    }
}