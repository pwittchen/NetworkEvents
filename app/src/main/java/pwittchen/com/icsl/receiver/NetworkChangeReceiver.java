package pwittchen.com.icsl.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.squareup.otto.Bus;

import pwittchen.com.icsl.event.ConnectivityStatusChangedEvent;
import pwittchen.com.icsl.helper.NetworkHelper;
import pwittchen.com.icsl.task.PingToRemoteHostTask;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private Bus eventBus;

    public NetworkChangeReceiver(Bus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityStatus connectivityStatus = NetworkHelper.getConnectivityStatus(context);
        eventBus.post(new ConnectivityStatusChangedEvent(connectivityStatus));

        if (connectivityStatus == ConnectivityStatus.WIFI_CONNECTED) {
            new PingToRemoteHostTask(context).execute();
        }
    }
}
