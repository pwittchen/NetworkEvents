package pwittchen.com.internetconnectionstatelistener.receiver;

public enum ConnectivityStatus {
    WIFI_CONNECTED("connected to WiFi"),
    WIFI_CONNECTED_ONLINE("connected to WiFi (internet available)"),
    MOBILE_CONNECTED("connected to mobile network"),
    OFFLINE("offline");

    private String status;

    ConnectivityStatus(String status) {
        this.status = status;
    }
}
