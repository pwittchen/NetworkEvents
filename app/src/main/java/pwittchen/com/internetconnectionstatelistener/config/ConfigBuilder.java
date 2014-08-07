package pwittchen.com.internetconnectionstatelistener.config;

public class ConfigBuilder {
    private String remoteHostForPing;
    private String intentName;
    private String intentNameExtra;

    public static ConfigBuilder create() {
        return new ConfigBuilder();
    }

    public ConfigBuilder setRemoteHostForPing(String remoteHostForPing) {
        this.remoteHostForPing = remoteHostForPing;
        return this;
    }

    public ConfigBuilder setIntentName(String intentName) {
        this.intentName = intentName;
        return this;
    }

    public ConfigBuilder setIntentNameExtra(String intentNameExtra) {
        this.intentNameExtra = intentNameExtra;
        return this;
    }

    public Config build() {
        return new Config(this);
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
