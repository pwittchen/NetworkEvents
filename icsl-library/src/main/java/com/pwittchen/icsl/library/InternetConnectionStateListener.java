package com.pwittchen.icsl.library;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.pwittchen.icsl.library.config.ICSLConfig;
import com.pwittchen.icsl.library.receiver.InternetConnectionChangeReceiver;
import com.pwittchen.icsl.library.receiver.NetworkChangeReceiver;
import com.pwittchen.icsl.library.receiver.WifiScanReceiver;
import com.pwittchen.icsl.library.service.WifiAccessPointScanService;
import com.squareup.otto.Bus;

public class InternetConnectionStateListener {
    private Context context;
    private Bus eventBus;
    private NetworkChangeReceiver networkChangeReceiver;
    private InternetConnectionChangeReceiver internetConnectionChangeReceiver;
    private WifiScanReceiver wifiScanReceiver;

    public InternetConnectionStateListener(Context context, Bus eventBus) {
        this.context = context;
        this.eventBus = eventBus;
    }

    public InternetConnectionStateListener(Context context, Bus eventBus, boolean scanWiFiAccessPointsInBackground) {
        this.context = context;
        this.eventBus = eventBus;
        ICSLConfig.setScanWifiAccessPointsInBackground(scanWiFiAccessPointsInBackground);
        startWifiAccessPointScanService(context);
    }

    public InternetConnectionStateListener(Context context, Bus eventBus, boolean scanWiFiAccessPointsInBackground, int wifiScanIntervalInMilliseconds) {
        this.context = context;
        this.eventBus = eventBus;
        ICSLConfig.setScanWifiAccessPointsInBackground(scanWiFiAccessPointsInBackground);
        ICSLConfig.setWifiScanUpdateIntervalInMilliseconds(wifiScanIntervalInMilliseconds);
        startWifiAccessPointScanService(context);
    }

    private void startWifiAccessPointScanService(Context context) {
        if(ICSLConfig.isScanWifiAccessPointsInBackground()) {
            Intent intent = new Intent(context, WifiAccessPointScanService.class);
            context.startService(intent);
        }
    }

    public void register() {
        registerNetworkChangeReceiver();
        registerInternetConnectionChangeReceiver();
        if (ICSLConfig.isScanWifiAccessPointsInBackground()) {
            registerWifiScanReceiver();
        }
    }

    public void unregister() {
        context.unregisterReceiver(networkChangeReceiver);
        context.unregisterReceiver(internetConnectionChangeReceiver);
        if (ICSLConfig.isScanWifiAccessPointsInBackground()) {
            context.unregisterReceiver(wifiScanReceiver);
        }
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

    private void registerWifiScanReceiver() {
        wifiScanReceiver = new WifiScanReceiver(eventBus);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ICSLConfig.getIntentNameForWifiAccessPointsScanFinished());
        context.registerReceiver(wifiScanReceiver, filter);
    }
}
