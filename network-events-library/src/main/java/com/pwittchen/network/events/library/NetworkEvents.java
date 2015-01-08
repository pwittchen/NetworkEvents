package com.pwittchen.network.events.library;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;

import com.pwittchen.network.events.library.config.NetworkEventsConfig;
import com.pwittchen.network.events.library.helper.NetworkHelper;
import com.pwittchen.network.events.library.receiver.InternetConnectionChangeReceiver;
import com.pwittchen.network.events.library.receiver.NetworkConnectionChangeReceiver;
import com.pwittchen.network.events.library.receiver.WifiSignalStrengthChangeReceiver;
import com.squareup.otto.Bus;

public class NetworkEvents {
    private Context context;
    private Bus eventBus;
    private NetworkConnectionChangeReceiver networkConnectionChangeReceiver;
    private InternetConnectionChangeReceiver internetConnectionChangeReceiver;
    private WifiSignalStrengthChangeReceiver wifiSignalStrengthChangeReceiver;

    /**
     * @param context  Android Context
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
     */
    public void register() {
        registerNetworkConnectionChangeReceiver();
        registerInternetConnectionChangeReceiver();
        registerWifiSignalStrengthChangeReceiver();
        /**
         * start WiFi scan in order to refresh access point list
         * if this won't be called WifiAccessPointsRefreshedEvent may never occur
         */
        NetworkHelper.startWifiScan(context);
    }

    /**
     * unregisters NetworkEvents
     * should be executed in onDestroy() method in activity
     * or during destroying instance of class extending Application
     */
    public void unregister() {
        context.unregisterReceiver(networkConnectionChangeReceiver);
        context.unregisterReceiver(internetConnectionChangeReceiver);
        context.unregisterReceiver(wifiSignalStrengthChangeReceiver);
    }

    private void registerNetworkConnectionChangeReceiver() {
        networkConnectionChangeReceiver = new NetworkConnectionChangeReceiver(eventBus);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        context.registerReceiver(networkConnectionChangeReceiver, filter);
    }

    private void registerInternetConnectionChangeReceiver() {
        internetConnectionChangeReceiver = new InternetConnectionChangeReceiver(eventBus);
        IntentFilter filter = new IntentFilter();
        filter.addAction(NetworkEventsConfig.getIntentNameForInternetConnectionChange());
        context.registerReceiver(internetConnectionChangeReceiver, filter);
    }

    private void registerWifiSignalStrengthChangeReceiver() {
        wifiSignalStrengthChangeReceiver = new WifiSignalStrengthChangeReceiver(eventBus);
        IntentFilter filter = new IntentFilter(WifiManager.RSSI_CHANGED_ACTION);
        context.registerReceiver(wifiSignalStrengthChangeReceiver, filter);
    }
}