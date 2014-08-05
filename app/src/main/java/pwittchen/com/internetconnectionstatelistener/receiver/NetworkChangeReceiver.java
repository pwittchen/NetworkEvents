package pwittchen.com.internetconnectionstatelistener.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import pwittchen.com.internetconnectionstatelistener.BaseApplication;
import pwittchen.com.internetconnectionstatelistener.task.PingToRemoteHostTask;
import pwittchen.com.internetconnectionstatelistener.helper.NetworkHelper;

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityStatus connectivityStatus = NetworkHelper.getConnectivityStatus();
        String message = String.format("Network state changed: %s", connectivityStatus.toString());
        Log.d("InternetConnectionListener", message);
        Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_SHORT).show();
        if (connectivityStatus == ConnectivityStatus.WIFI_CONNECTED) {
            new PingToRemoteHostTask().execute();
        }
    }
}
