package com.pwittchen.network.events.library;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;

import com.pwittchen.network.events.library.config.NetworkEventsConfig;
import com.pwittchen.network.events.library.helper.NetworkHelper;
import com.pwittchen.network.events.library.receiver.InternetConnectionChangeReceiver;
import com.pwittchen.network.events.library.receiver.NetworkChangeReceiver;
import com.pwittchen.network.events.library.receiver.WifiAccessPointsRefreshedReceiver;
import com.squareup.otto.Bus;

public class NetworkEvents {
    private Context context;
    private Bus eventBus;
    private NetworkChangeReceiver networkChangeReceiver;
    private InternetConnectionChangeReceiver internetConnectionChangeReceiver;
    private WifiAccessPointsRefreshedReceiver wifiAccessPointsRefreshedReceiver;

    /**
     * @param context Android Context
     * @param eventBus instance of the Otto Event Bus
     */
    public NetworkEvents(Context context, Bus eventBus) {
        this.context = context;
        this.eventBus = eventBus;
    }

    /**
     * registers NetworkEvents
     * should be executed in onCreate() method in activity
     * or during creating instance of class extending Application
     *
     */
    public void register() {
        registerNetworkChangeReceiver();
        registerInternetConnectionChangeReceiver();
        registerWifiAccessPointsRefreshedReceiver();
        // start WiFi scan in order to refresh access point list
        // if this won't be called WifiAccessPointsRefreshedEvent may never occur
        NetworkHelper.startWifiScan(context);
    }

    /**
     * unregisters NetworkEvents
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
        filter.addAction(NetworkEventsConfig.getIntentNameForInternetConnectionChange());
        context.registerReceiver(internetConnectionChangeReceiver, filter);
    }

    private void registerWifiAccessPointsRefreshedReceiver() {
        wifiAccessPointsRefreshedReceiver = new WifiAccessPointsRefreshedReceiver(eventBus);
        IntentFilter filter = new IntentFilter(WifiManager.RSSI_CHANGED_ACTION);
        context.registerReceiver(wifiAccessPointsRefreshedReceiver, filter);
    }
}
