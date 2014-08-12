package com.pwittchen.icsl.library.config;

public class ICSLConfigBuilder {
    private String remoteHostForPing;
    private String intentNameInternetConnectionChange;
    private String intentNameExtraInternetConnectionChange;
    private String intentNameWifiScanFinished;
    private boolean scanWiFiAccessPointsInBackground;
    private int wifiScanIntervalInMilliseconds;

    public static ICSLConfigBuilder create() {
        return new ICSLConfigBuilder();
    }

    public ICSLConfigBuilder setRemoteHostForPing(String remoteHostForPing) {
        this.remoteHostForPing = remoteHostForPing;
        return this;
    }

    public ICSLConfigBuilder setIntentNameInternetConnectionChange(String intentNameInternetConnectionChange) {
        this.intentNameInternetConnectionChange = intentNameInternetConnectionChange;
        return this;
    }

    public ICSLConfigBuilder setIntentNameExtraInternetConnectionChange(String intentNameExtraInternetConnectionChange) {
        this.intentNameExtraInternetConnectionChange = intentNameExtraInternetConnectionChange;
        return this;
    }

    public ICSLConfigBuilder setIntentNameWifiScanFinished(String intentNameWifiScanFinished) {
        this.intentNameWifiScanFinished = intentNameWifiScanFinished;
        return this;
    }

    public ICSLConfigBuilder setWifiScanIntervalInMilliseconds(int wifiScanIntervalInMilliseconds) {
        this.wifiScanIntervalInMilliseconds = wifiScanIntervalInMilliseconds;
        return this;
    }

    public ICSLConfigBuilder setScanWiFiAccessPointsInBackground(boolean scanWiFiAccessPointsInBackground) {
        this.scanWiFiAccessPointsInBackground = scanWiFiAccessPointsInBackground;
        return this;
    }

    public ICSLConfig build() {
        return new ICSLConfig(this);
    }

    public String getRemoteHostForPing() {
        return remoteHostForPing;
    }

    public String getIntentNameInternetConnectionChange() {
        return intentNameInternetConnectionChange;
    }

    public String getIntentNameExtraInternetConnectionChange() {
        return intentNameExtraInternetConnectionChange;
    }

    public String getIntentNameWifiScanFinished() {
        return intentNameWifiScanFinished;
    }

    public boolean isScanWiFiAccessPointsInBackground() {
        return scanWiFiAccessPointsInBackground;
    }

    public int getWifiScanIntervalInMilliseconds() {
        return wifiScanIntervalInMilliseconds;
    }
}
