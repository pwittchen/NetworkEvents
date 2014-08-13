package com.pwittchen.icsl.library.event;

import android.net.wifi.ScanResult;
import android.util.Log;

import java.util.List;

/**
 * Event pushed to Otto Event Bus when Wifi Scan is finished
 * It occurs every given interval when Wifi Scan was enabled
 * in constructor of InternetConnectionStateListener or ICLSConfig
 * If it was not enabled, this event will never occur
 */
public class WifiScanFinishedEvent {

    private List<ScanResult> accessPointList;
    private final static String TAG = "InternetConnectionStateListener";

    public WifiScanFinishedEvent(List<ScanResult> accessPointList) {
        this.accessPointList = accessPointList;
        String message = String.format("WiFiScan finished event: %d access points available", accessPointList.size());
        Log.d(TAG, message);
    }

    public List<ScanResult> getAccessPointList() {
        return accessPointList;
    }
}
