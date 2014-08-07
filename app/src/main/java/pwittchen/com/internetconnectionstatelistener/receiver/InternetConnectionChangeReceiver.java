package pwittchen.com.internetconnectionstatelistener.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import pwittchen.com.internetconnectionstatelistener.R;
import pwittchen.com.internetconnectionstatelistener.helper.StringHelper;

public class InternetConnectionChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(StringHelper.getString(context, R.string.internet_connection_state_change_intent))) {
            boolean connectedToInternet = intent.getBooleanExtra(StringHelper.getString(context, R.string.internet_connection_intent_extra), false);
            ConnectivityStatus connectivityStatus = (connectedToInternet) ? ConnectivityStatus.WIFI_CONNECTED_ONLINE : ConnectivityStatus.OFFLINE;
            String message = String.format("Internet connection state changed: %s", connectivityStatus);
            Log.d("InternetConnectionListener", message);
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}
