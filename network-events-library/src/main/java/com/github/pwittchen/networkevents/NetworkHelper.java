package com.github.pwittchen.networkevents;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class NetworkHelper {
    public static ConnectivityStatus getConnectivityStatus(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return ConnectivityStatus.WIFI_CONNECTED;
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return ConnectivityStatus.MOBILE_CONNECTED;
            }
        }
        return ConnectivityStatus.OFFLINE;
    }

    public static boolean ping(String remoteHost) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(remoteHost).openConnection();
            connection.setRequestMethod("HEAD");
            return (connection.getResponseCode() == 200);
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isConnectedToAnyNetwork(Context context) {
        boolean mobileNetworkActive = getConnectivityStatus(context) == ConnectivityStatus.MOBILE_CONNECTED;
        boolean wifiNetworkActive = getConnectivityStatus(context) == ConnectivityStatus.WIFI_CONNECTED;
        return mobileNetworkActive || wifiNetworkActive;
    }

    public static WifiInfo getWifiInfo(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.getConnectionInfo();
    }

    public static List<ScanResult> getWifiScanResults(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.getScanResults();
    }

    public static void startWifiScan(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();
    }
}