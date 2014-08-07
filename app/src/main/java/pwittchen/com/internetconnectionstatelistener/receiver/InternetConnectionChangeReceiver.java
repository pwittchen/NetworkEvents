package pwittchen.com.internetconnectionstatelistener.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import pwittchen.com.internetconnectionstatelistener.config.ICSLConfig;

public class InternetConnectionChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ICSLConfig.getIntentName())) {
            boolean connectedToInternet = intent.getBooleanExtra(ICSLConfig.getIntentNameExtra(), false);
            ConnectivityStatus connectivityStatus = (connectedToInternet) ? ConnectivityStatus.WIFI_CONNECTED_HAS_INTERNET : ConnectivityStatus.WIFI_CONNECTED_HAS_NO_INTERNET;
            String message = String.format("InternetConnectionStateChanged: %s", connectivityStatus);
            Log.d("InternetConnectionListener", message);
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}
