package pwittchen.com.icsl.event;

import android.util.Log;

import pwittchen.com.icsl.receiver.ConnectivityStatus;

public class ConnectivityStatusChangedEvent {
    private ConnectivityStatus connectivityStatus;

    public ConnectivityStatusChangedEvent(ConnectivityStatus connectivityStatus) {
        this.connectivityStatus = connectivityStatus;
        String message = String.format("ConnectivityStatusChangedEvent: %s", connectivityStatus.toString());
        Log.d("InternetConnectionListener", message);
    }

    public ConnectivityStatus getConnectivityStatus() {
        return connectivityStatus;
    }
}
