package com.pwitchen.icsl.library.event;

import android.net.wifi.WifiInfo;
import android.util.Log;

import com.pwitchen.icsl.library.receiver.ConnectivityStatus;

public class ConnectivityStatusChangedEvent {
    private final static String TAG = "InternetConnectionStateListener";
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
