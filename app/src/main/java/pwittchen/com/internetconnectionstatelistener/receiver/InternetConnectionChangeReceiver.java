package pwittchen.com.internetconnectionstatelistener.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import pwittchen.com.internetconnectionstatelistener.config.Config;

public class InternetConnectionChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Config.getIntentName())) {
            boolean connectedToInternet = intent.getBooleanExtra(Config.getIntentNameExtra(), false);
            ConnectivityStatus connectivityStatus = (connectedToInternet) ? ConnectivityStatus.WIFI_CONNECTED_ONLINE : ConnectivityStatus.WIFI_CONNECTED_OFFLINE;
            String message = String.format("InternetConnectionStateChanged: %s", connectivityStatus);
            Log.d("InternetConnectionListener", message);
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}
