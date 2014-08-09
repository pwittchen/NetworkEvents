package com.pwitchen.icsl.library.event;

import android.util.Log;

import com.pwitchen.icsl.library.receiver.ConnectivityStatus;

public class ConnectivityStatusChangedEvent {
    private final static String TAG = "InternetConnectionStateListener";
    private ConnectivityStatus connectivityStatus;

    public ConnectivityStatusChangedEvent(ConnectivityStatus connectivityStatus) {
        this.connectivityStatus = connectivityStatus;
        String message = String.format("ConnectivityStatusChangedEvent: %s", connectivityStatus.toString());
        Log.d(TAG, message);
    }

    public ConnectivityStatus getConnectivityStatus() {
        return connectivityStatus;
    }
}
