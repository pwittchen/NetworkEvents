package com.github.pwittchen.networkevents.library.receiver;

import android.content.Context;
import android.content.Intent;

import com.github.pwittchen.networkevents.library.ConnectivityStatus;
import com.github.pwittchen.networkevents.library.NetworkHelper;
import com.github.pwittchen.networkevents.library.Ping;
import com.squareup.otto.Bus;

public final class NetworkConnectionChangeReceiver extends BaseBroadcastReceiver {

    public NetworkConnectionChangeReceiver(Bus bus) {
        super(bus);
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        final ConnectivityStatus connectivityStatus = NetworkHelper.getConnectivityStatus(context);
        if (statusNotChanged(connectivityStatus)) return;

        postConnectivityChanged(connectivityStatus, new Runnable() {
            @Override
            public void run() {
                if (connectivityStatus == ConnectivityStatus.WIFI_CONNECTED) {
                    new Ping(context).execute();
                }
            }
        });
    }
}