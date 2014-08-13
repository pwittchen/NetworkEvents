package com.pwittchen.icsl.library.event;

import android.util.Log;

/**
 * Event pushed to Otto Event Bus when List of WiFi Access Points was refreshed
 */
public class WifiAccessPointsRefreshedEvent {
    private final static String TAG = "InternetConnectionStateListener";

    public WifiAccessPointsRefreshedEvent() {
        Log.d(TAG, "WifiAccessPointsRefreshedEvent");
    }
}
