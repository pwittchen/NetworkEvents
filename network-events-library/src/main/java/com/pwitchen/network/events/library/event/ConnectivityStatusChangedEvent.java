package com.pwitchen.network.events.library.event;

import android.net.wifi.WifiInfo;
import android.util.Log;

import com.pwitchen.network.events.library.receiver.ConnectivityStatus;

/**
 * Event pushed to Otto Event Bus when ConnectivityStatus changes;
 * E.g. when WiFi is turned on or off or when mobile network is turned on or off
 * it also occurs when WiFi is on, but there is no Internet connection or when there is Internet connection
 */
public class ConnectivityStatusChangedEvent {
    private final static String TAG = "NetworkEvents";
    private ConnectivityStatus connectivityStatus;
    private WifiInfo wifiInfo;

    public ConnectivityStatusChangedEvent(ConnectivityStatus connectivityStatus, WifiInfo wifiInfo) {
        this.wifiInfo = wifiInfo;
        this.connectivityStatus = connectivityStatus;
        String message = String.format("ConnectivityStatusChangedEvent: %s, WiFi Info: %s", connectivityStatus.toString(), wifiInfo.toString());
        Log.d(TAG, message);
    }

    public ConnectivityStatus getConnectivityStatus() {
        return connectivityStatus;
    }

    public WifiInfo getWifiInfo() {
        return wifiInfo;
    }
}
