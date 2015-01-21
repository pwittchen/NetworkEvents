package com.github.pwittchen.network.events.library.event;

import android.util.Log;

import com.github.pwittchen.network.events.library.NetworkEventsConfig;
import com.github.pwittchen.network.events.library.ConnectivityStatus;

/**
 * Event pushed to Otto Event Bus when ConnectivityStatus changes;
 * E.g. when WiFi is turned on or off or when mobile network is turned on or off
 * it also occurs when WiFi is on, but there is no Internet connection or when there is Internet connection
 */
public class ConnectivityChanged {
    private ConnectivityStatus connectivityStatus;

    public ConnectivityChanged(ConnectivityStatus connectivityStatus) {
        this.connectivityStatus = connectivityStatus;
        String message = String.format("ConnectivityChanged: %s", connectivityStatus.toString());
        Log.d(NetworkEventsConfig.TAG, message);
    }

    public ConnectivityStatus getConnectivityStatus() {
        return connectivityStatus;
    }

}