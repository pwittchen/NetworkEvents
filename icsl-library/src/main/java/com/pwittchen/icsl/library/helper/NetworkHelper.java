package com.pwittchen.icsl.library.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.pwittchen.icsl.library.receiver.ConnectivityStatus;

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

    public static boolean pingRemoteHostSync(String remoteHost) {
        try {
            URL url = new URL(remoteHost);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("User-Agent", "test");
            httpURLConnection.setRequestProperty("Connection", "close");
            httpURLConnection.setConnectTimeout(1000);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
    }

    private static WifiManager getWifiManager(Context context) {
        return (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    public static WifiInfo getWiFiInfo(Context context) {
        return getWifiManager(context).getConnectionInfo();
    }

    public static List<ScanResult> getAccessPointList(Context context) {
        return getWifiManager(context).getScanResults();
    }

    /**
     * Changes WiFi state to opposite
     * and then sets it back to the previous state
     */
    public static void restartWiFiState(Context context) {
        if(getWifiManager(context).isWifiEnabled()) {
            getWifiManager(context).setWifiEnabled(false);
            getWifiManager(context).setWifiEnabled(true);
        } else {
            getWifiManager(context).setWifiEnabled(true);
            getWifiManager(context).setWifiEnabled(false);
        }
    }
}
