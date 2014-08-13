package com.pwittchen.icsl.library.config;

public class ICSLConfigBuilder {
    private String remoteHostForPing;
    private String intentNameInternetConnectionChange;
    private String intentNameExtraInternetConnectionChange;
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

    public String getRemoteHostForPing() {
        return remoteHostForPing;
    }

    public String getIntentNameInternetConnectionChange() {
        return intentNameInternetConnectionChange;
    }

    public String getIntentNameExtraInternetConnectionChange() {
        return intentNameExtraInternetConnectionChange;
    }

    public ICSLConfig build() {
        return new ICSLConfig(this);
    }
}
