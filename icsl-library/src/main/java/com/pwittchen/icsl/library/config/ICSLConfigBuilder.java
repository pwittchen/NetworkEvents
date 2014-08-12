package com.pwittchen.icsl.library.config;

public class ICSLConfigBuilder {
    private String remoteHostForPing;
    private String intentName;
    private String intentNameExtra;

    public static ICSLConfigBuilder create() {
        return new ICSLConfigBuilder();
    }

    public ICSLConfigBuilder setRemoteHostForPing(String remoteHostForPing) {
        this.remoteHostForPing = remoteHostForPing;
        return this;
    }

    public ICSLConfigBuilder setIntentName(String intentName) {
        this.intentName = intentName;
        return this;
    }

    public ICSLConfigBuilder setIntentNameExtra(String intentNameExtra) {
        this.intentNameExtra = intentNameExtra;
        return this;
    }

    public ICSLConfig build() {
        return new ICSLConfig(this);
    }

    public String getRemoteHostForPing() {
        return remoteHostForPing;
    }

    public String getIntentName() {
        return intentName;
    }

    public String getIntentNameExtra() {
        return intentNameExtra;
    }
}
