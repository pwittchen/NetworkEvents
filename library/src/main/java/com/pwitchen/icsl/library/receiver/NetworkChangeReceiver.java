package com.pwitchen.icsl.library.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.pwitchen.icsl.library.event.ConnectivityStatusChangedEvent;
import com.pwitchen.icsl.library.helper.NetworkHelper;
import com.pwitchen.icsl.library.task.PingToRemoteHostTask;
import com.squareup.otto.Bus;


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
