package com.pwittchen.icsl.library.config;

public class ICSLConfig {
    // Setting default values of the configuration
    private static String remoteHostForPing = "http://www.google.com/";
    private static String intentNameForInternetConnectionChange = "pwittchen.intent.action.INTERNET_CONNECTION_STATE_CHANGED";
    private static String intentNameForWifiAccessPointsScanFinished = "pwittchen.intent.action.WIFI_ACCESS_POINTS_SCAN_FINISHED";
    private static String intentNameExtraForInternetConnectionChange = "connectedToInternet";
    private static boolean scanWifiAccessPointsInBackground = false;
    private static boolean enableWifiRestartInWifiScan = false;
    private static int wifiScanUpdateIntervalInMilliseconds = 60 * 1000;

    public ICSLConfig(ICSLConfigBuilder builder) {
        remoteHostForPing = builder.getRemoteHostForPing();
        intentNameForInternetConnectionChange = builder.getIntentNameInternetConnectionChange();
        intentNameExtraForInternetConnectionChange = builder.getIntentNameExtraInternetConnectionChange();
        intentNameForWifiAccessPointsScanFinished = builder.getIntentNameWifiScanFinished();
        wifiScanUpdateIntervalInMilliseconds = builder.getWifiScanIntervalInMilliseconds();
        scanWifiAccessPointsInBackground = builder.isScanWiFiAccessPointsInBackground();
        enableWifiRestartInWifiScan = builder.isEnableWifiRestartInWifiScan();
    }

    public static String getRemoteHostForPing() {
        return remoteHostForPing;
    }

    public static void setRemoteHostForPing(String remoteHostForPing) {
        ICSLConfig.remoteHostForPing = remoteHostForPing;
    }

    public static String getIntentNameForInternetConnectionChange() {
        return intentNameForInternetConnectionChange;
    }

    public static void setIntentNameForInternetConnectionChange(String intentNameForInternetConnectionChange) {
        ICSLConfig.intentNameForInternetConnectionChange = intentNameForInternetConnectionChange;
    }

    public static String getIntentNameExtraForInternetConnectionChange() {
        return intentNameExtraForInternetConnectionChange;
    }

    public static void setIntentNameExtraForInternetConnectionChange(String intentNameExtraForInternetConnectionChange) {
        ICSLConfig.intentNameExtraForInternetConnectionChange = intentNameExtraForInternetConnectionChange;
    }

    public static String getIntentNameForWifiAccessPointsScanFinished() {
        return intentNameForWifiAccessPointsScanFinished;
    }

    public static void setIntentNameForWifiAccessPointsScanFinished(String intentNameForWifiAccessPointsScanFinished) {
        ICSLConfig.intentNameForWifiAccessPointsScanFinished = intentNameForWifiAccessPointsScanFinished;
    }

    public static int getWifiScanUpdateIntervalInMilliseconds() {
        return wifiScanUpdateIntervalInMilliseconds;
    }

    public static void setWifiScanUpdateIntervalInMilliseconds(int wifiScanUpdateIntervalInMilliseconds) {
        ICSLConfig.wifiScanUpdateIntervalInMilliseconds = wifiScanUpdateIntervalInMilliseconds;
    }

    public static boolean isScanWifiAccessPointsInBackground() {
        return scanWifiAccessPointsInBackground;
    }

    public static void setScanWifiAccessPointsInBackground(boolean scanWifiAccessPointsInBackground) {
        ICSLConfig.scanWifiAccessPointsInBackground = scanWifiAccessPointsInBackground;
    }

    public static boolean isEnableWifiRestartInWifiScan() {
        return enableWifiRestartInWifiScan;
    }

    /**
     * Turns on/off WiFi after WiFi scanning
     * It's helpful for testing user location in the building's room
     * basing on WiFi access points
     */
    public static void setEnableWifiRestartInWifiScan(boolean enableWifiRestartInWifiScan) {
        ICSLConfig.enableWifiRestartInWifiScan = enableWifiRestartInWifiScan;
    }
}
