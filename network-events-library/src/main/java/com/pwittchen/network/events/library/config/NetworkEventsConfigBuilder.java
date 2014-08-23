package com.pwittchen.network.events.library.config;

public class NetworkEventsConfigBuilder {
    private String remoteHostForPing;
    private String intentNameInternetConnectionChange;
    private String intentNameExtraInternetConnectionChange;
    public static NetworkEventsConfigBuilder create() {
        return new NetworkEventsConfigBuilder();
    }

    public NetworkEventsConfigBuilder setRemoteHostForPing(String remoteHostForPing) {
        this.remoteHostForPing = remoteHostForPing;
        return this;
    }

    public NetworkEventsConfigBuilder setIntentNameInternetConnectionChange(String intentNameInternetConnectionChange) {
        this.intentNameInternetConnectionChange = intentNameInternetConnectionChange;
        return this;
    }

    public NetworkEventsConfigBuilder setIntentNameExtraInternetConnectionChange(String intentNameExtraInternetConnectionChange) {
        this.intentNameExtraInternetConnectionChange = intentNameExtraInternetConnectionChange;
        return this;
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

    public NetworkEventsConfig build() {
        return new NetworkEventsConfig(this);
    }
}
