package com.pwittchen.icsl.library.config;

public class ICSLConfig {
    // Setting default values of the configuration
    private static String remoteHostForPing = "http://www.google.com/";
    private static String intentNameForInternetConnectionChange = "pwittchen.intent.action.INTERNET_CONNECTION_STATE_CHANGED";
    private static String intentNameExtraForInternetConnectionChange = "connectedToInternet";

    public ICSLConfig(ICSLConfigBuilder builder) {
        remoteHostForPing = builder.getRemoteHostForPing();
        intentNameForInternetConnectionChange = builder.getIntentNameInternetConnectionChange();
        intentNameExtraForInternetConnectionChange = builder.getIntentNameExtraInternetConnectionChange();
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
}
