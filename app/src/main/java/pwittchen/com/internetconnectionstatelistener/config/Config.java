package pwittchen.com.internetconnectionstatelistener.config;

public class Config {
    /**
     * Setting default values of the configuration
     */
    private static String remoteHostForPing = "http://www.google.com/";
    private static String intentName = "pwittchen.intent.action.INTERNET_CONNECTION_STATE_CHANGED";
    private static String intentNameExtra = "connectedToInternet";

    public Config(ConfigBuilder builder) {
        remoteHostForPing = builder.getRemoteHostForPing();
        intentName = builder.getIntentName();
        intentNameExtra = builder.getIntentNameExtra();
    }

    public static String getRemoteHostForPing() {
        return remoteHostForPing;
    }

    public static void setRemoteHostForPing(String remoteHostForPing) {
        Config.remoteHostForPing = remoteHostForPing;
    }

    public static String getIntentName() {
        return intentName;
    }

    public static void setIntentName(String intentName) {
        Config.intentName = intentName;
    }

    public static String getIntentNameExtra() {
        return intentNameExtra;
    }

    public static void setIntentNameExtra(String intentNameExtra) {
        Config.intentNameExtra = intentNameExtra;
    }
}
