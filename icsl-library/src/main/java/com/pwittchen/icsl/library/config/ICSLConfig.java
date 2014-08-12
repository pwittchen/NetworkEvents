package com.pwittchen.icsl.library.config;

public class ICSLConfig {
    // Setting default values of the configuration
    private static String remoteHostForPing = "http://www.google.com/";
    private static String intentName = "pwittchen.intent.action.INTERNET_CONNECTION_STATE_CHANGED";
    private static String intentNameExtra = "connectedToInternet";

    public ICSLConfig(ICSLConfigBuilder builder) {
        remoteHostForPing = builder.getRemoteHostForPing();
        intentName = builder.getIntentName();
        intentNameExtra = builder.getIntentNameExtra();
    }

    public static String getRemoteHostForPing() {
        return remoteHostForPing;
    }

    public static void setRemoteHostForPing(String remoteHostForPing) {
        ICSLConfig.remoteHostForPing = remoteHostForPing;
    }

    public static String getIntentName() {
        return intentName;
    }

    public static void setIntentName(String intentName) {
        ICSLConfig.intentName = intentName;
    }

    public static String getIntentNameExtra() {
        return intentNameExtra;
    }

    public static void setIntentNameExtra(String intentNameExtra) {
        ICSLConfig.intentNameExtra = intentNameExtra;
    }
}
