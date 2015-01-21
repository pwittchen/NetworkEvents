package com.github.pwittchen.network.events.library.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.github.pwittchen.network.events.library.ConnectivityStatus;
import com.github.pwittchen.network.events.library.NetworkEventsConfig;
import com.github.pwittchen.network.events.library.event.ConnectivityChanged;
import com.squareup.otto.Bus;

public class InternetConnectionChangeReceiver extends BroadcastReceiver {

    private Bus eventBus;

    public InternetConnectionChangeReceiver(Bus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(NetworkEventsConfig.getIntentNameForInternetConnectionChange())) {
            boolean connectedToInternet = intent.getBooleanExtra(NetworkEventsConfig.getIntentNameExtraForInternetConnectionChange(), false);
            ConnectivityStatus connectivityStatus = (connectedToInternet) ? ConnectivityStatus.WIFI_CONNECTED_HAS_INTERNET : ConnectivityStatus.WIFI_CONNECTED_HAS_NO_INTERNET;
            eventBus.post(new ConnectivityChanged(connectivityStatus));
        }
    }
}
