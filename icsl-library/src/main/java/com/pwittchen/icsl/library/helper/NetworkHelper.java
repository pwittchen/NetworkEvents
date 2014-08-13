package com.pwittchen.icsl.library.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.pwittchen.icsl.library.receiver.ConnectivityStatus;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

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

    public static WifiInfo getWiFiInfo(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.getConnectionInfo();
    }

    public static boolean isWifiEnabled(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED);
    }

    /**
     * Calculates distance to the Access Point
     * basing on signal level in dB and frequency in MHz
     * Method based on this information:
     * http://stackoverflow.com/a/18359639/1150795
     * http://en.wikipedia.org/wiki/Free-space_path_loss
     * http://rvmiller.com/2013/05/part-1-wifi-based-trilateration-on-android/
     *
     * @param signalLevelInDb
     * @param freqInMHz
     * @return distance in meters
     */
    public static double calculateDistance(double signalLevelInDb, double freqInMHz) {
        double exp = (27.55 - (20 * Math.log10(freqInMHz)) - Math.abs(signalLevelInDb)) / 20.0;
        return Math.pow(10.0, exp);
    }
}
