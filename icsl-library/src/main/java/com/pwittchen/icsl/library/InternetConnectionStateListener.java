package com.pwittchen.icsl.library;

import android.content.Context;
import android.content.IntentFilter;

import com.pwittchen.icsl.library.config.ICSLConfig;
import com.pwittchen.icsl.library.receiver.InternetConnectionChangeReceiver;
import com.pwittchen.icsl.library.receiver.NetworkChangeReceiver;
import com.squareup.otto.Bus;

public class InternetConnectionStateListener {
    private Context context;
    private Bus eventBus;
    private NetworkChangeReceiver networkChangeReceiver;
    private InternetConnectionChangeReceiver internetConnectionChangeReceiver;

    public InternetConnectionStateListener(Context context, Bus eventBus) {
        this.context = context;
        this.eventBus = eventBus;
    }

    public void register() {
        registerNetworkChangeReceiver();
        registerInternetConnectionChangeReceiver();
    }

    public void unregister() {
        context.unregisterReceiver(networkChangeReceiver);
        context.unregisterReceiver(internetConnectionChangeReceiver);
    }

    private void registerNetworkChangeReceiver() {
        networkChangeReceiver = new NetworkChangeReceiver(eventBus);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        context.registerReceiver(networkChangeReceiver, filter);
    }

    private void registerInternetConnectionChangeReceiver() {
        internetConnectionChangeReceiver = new InternetConnectionChangeReceiver(eventBus);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ICSLConfig.getIntentName());
        context.registerReceiver(internetConnectionChangeReceiver, filter);
    }
}
