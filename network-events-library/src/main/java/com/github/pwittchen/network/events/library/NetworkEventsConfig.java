package com.github.pwittchen.network.events.library;

public class NetworkEventsConfig {
    /**
     * Setting default values of the configuration
     */
    public final static String TAG = "NetworkEvents";
    private static String remoteHostForPing = "http://www.google.com/";
    private static String intentNameForInternetConnectionChange = "network.events.intent.action.INTERNET_CONNECTION_STATE_CHANGED";
    private static String intentNameExtraForInternetConnectionChange = "network.events.intent.extra.CONNECTED_TO_INTERNET";

    public static String getRemoteHostForPing() {
        return remoteHostForPing;
    }

    public static void setRemoteHostForPing(String remoteHostForPing) {
        NetworkEventsConfig.remoteHostForPing = remoteHostForPing;
    }

    public static String getIntentNameForInternetConnectionChange() {
        return intentNameForInternetConnectionChange;
    }

    public static void setIntentNameForInternetConnectionChange(String intentNameForInternetConnectionChange) {
        NetworkEventsConfig.intentNameForInternetConnectionChange = intentNameForInternetConnectionChange;
    }

    public static String getIntentNameExtraForInternetConnectionChange() {
        return intentNameExtraForInternetConnectionChange;
    }

    public static void setIntentNameExtraForInternetConnectionChange(String intentNameExtraForInternetConnectionChange) {
        NetworkEventsConfig.intentNameExtraForInternetConnectionChange = intentNameExtraForInternetConnectionChange;
    }
}