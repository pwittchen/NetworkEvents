package com.pwittchen.icsl.library;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;

import com.pwittchen.icsl.library.config.ICSLConfig;
import com.pwittchen.icsl.library.receiver.InternetConnectionChangeReceiver;
import com.pwittchen.icsl.library.receiver.NetworkChangeReceiver;
import com.pwittchen.icsl.library.receiver.WifiAccessPointsRefreshedReceiver;
import com.squareup.otto.Bus;

public class InternetConnectionStateListener {
    private Context context;
    private Bus eventBus;
    private NetworkChangeReceiver networkChangeReceiver;
    private InternetConnectionChangeReceiver internetConnectionChangeReceiver;
    private WifiAccessPointsRefreshedReceiver wifiAccessPointsRefreshedReceiver;

    /**
     * @param context Android Context
     * @param eventBus instance of the Otto Event Bus
     */
    public InternetConnectionStateListener(Context context, Bus eventBus) {
        this.context = context;
        this.eventBus = eventBus;
    }

    /**
     * registers InternetConnectionStateListener
     * should be executed in onCreate() method in activity
     * or during creating instance of class extending Application
     */
    public void register() {
        registerNetworkChangeReceiver();
        registerInternetConnectionChangeReceiver();
        registerWifiAccessPointsRefreshedReceiver();
    }

    /**
     * unregisters InternetConnectionStateListener
     * should be executed in onDestroy() method in activity
     * or during destroying instance of class extending Application
     */
    public void unregister() {
        context.unregisterReceiver(networkChangeReceiver);
        context.unregisterReceiver(internetConnectionChangeReceiver);
        context.unregisterReceiver(wifiAccessPointsRefreshedReceiver);
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
        filter.addAction(ICSLConfig.getIntentNameForInternetConnectionChange());
        context.registerReceiver(internetConnectionChangeReceiver, filter);
    }

    private void registerWifiAccessPointsRefreshedReceiver() {
        wifiAccessPointsRefreshedReceiver = new WifiAccessPointsRefreshedReceiver(eventBus);
        IntentFilter filter = new IntentFilter(WifiManager.RSSI_CHANGED_ACTION);
        context.registerReceiver(wifiAccessPointsRefreshedReceiver, filter);
    }
}
