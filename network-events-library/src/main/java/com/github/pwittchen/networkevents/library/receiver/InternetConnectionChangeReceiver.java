package com.github.pwittchen.networkevents.library.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.github.pwittchen.networkevents.library.ConnectivityStatus;
import com.github.pwittchen.networkevents.library.NetworkEventsConfig;
import com.github.pwittchen.networkevents.library.event.ConnectivityChanged;
import com.squareup.otto.Bus;

public final class InternetConnectionChangeReceiver extends BroadcastReceiver {
    private final Bus bus;

    public InternetConnectionChangeReceiver(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(NetworkEventsConfig.INTENT)) {
            boolean connectedToInternet = intent.getBooleanExtra(NetworkEventsConfig.INTENT_EXTRA, false);

            ConnectivityStatus connectivityStatus
                    = (connectedToInternet)
                    ? ConnectivityStatus.WIFI_CONNECTED_HAS_INTERNET
                    : ConnectivityStatus.WIFI_CONNECTED_HAS_NO_INTERNET;

            bus.post(new ConnectivityChanged(connectivityStatus));
        }
    }
}
