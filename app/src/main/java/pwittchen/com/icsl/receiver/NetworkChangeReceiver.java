package pwittchen.com.icsl.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import pwittchen.com.icsl.helper.NetworkHelper;
import pwittchen.com.icsl.task.PingToRemoteHostTask;

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityStatus connectivityStatus = NetworkHelper.getConnectivityStatus(context);
        String message = String.format("NetworkStateChanged: %s", connectivityStatus.toString());
        Log.d("InternetConnectionListener", message);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        if (connectivityStatus == ConnectivityStatus.WIFI_CONNECTED) {
            new PingToRemoteHostTask(context).execute();
        }
    }
}
