/*
 * Copyright (C) 2015 Piotr Wittchen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.pwittchen.networkevents.library;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import com.github.pwittchen.networkevents.library.receiver.InternetConnectionChangeReceiver;
import com.github.pwittchen.networkevents.library.receiver.NetworkConnectionChangeReceiver;
import com.github.pwittchen.networkevents.library.receiver.WifiSignalStrengthChangeReceiver;
import com.squareup.otto.Bus;

/**
 * NetworkEvents - Android library listening network events.
 * It is able to detect ConnectivityStatus when it changes:
 * <ul>
 * <li>WIFI_CONNECTED("connected to WiFi")</li>
 * <li>WIFI_CONNECTED_HAS_INTERNET("connected to WiFi (Internet available)")</li>
 * <li>WIFI_CONNECTED_HAS_NO_INTERNET("connected to WiFi (Internet not available)")</li>
 * <li>MOBILE_CONNECTED("connected to mobile network")</li>
 * <li>OFFLINE("offline")</li>
 * </ul>
 * In addition it is able to detect situation when strength
 * of the Wifi signal was changed with WifiSignalStrengthChanged event.
 */
public final class NetworkEvents {
    private boolean wifiAccessPointsScanEnabled = true;
    private final Validator validator = new Validator();
    private final Context context;
    private final PingWrapper pingWrapper;
    private final NetworkConnectionChangeReceiver networkConnectionChangeReceiver;
    private final InternetConnectionChangeReceiver internetConnectionChangeReceiver;
    private final WifiSignalStrengthChangeReceiver wifiSignalStrengthChangeReceiver;

    /**
     * initializes NetworkEvents object
     *
     * @param context Android context
     * @param bus     Otto event bus
     */
    public NetworkEvents(Context context, Bus bus) {
        validator.checkNotNull(context, "context == null");
        validator.checkNotNull(bus, "bus == null");
        this.context = context;
        this.pingWrapper = new PingWrapper(context);
        this.networkConnectionChangeReceiver = new NetworkConnectionChangeReceiver(bus, pingWrapper);
        this.internetConnectionChangeReceiver = new InternetConnectionChangeReceiver(bus);
        this.wifiSignalStrengthChangeReceiver = new WifiSignalStrengthChangeReceiver(bus);
    }

    /**
     * sets url used for ping during Internet connection check
     *
     * @param url pingUrl
     * @return NetworkEvents object
     */
    public NetworkEvents withPingUrl(String url) {
        validator.checkNotNull(url, "url == null");
        validator.checkUrl(url, "invalid url");
        checkPingEnabled("setting pingUrl, but ping is disabled");
        pingWrapper.setUrl(url);
        return this;
    }

    /**
     * sets timeout for ping used during Internet connection check
     *
     * @param timeout ping timeout in milliseconds
     * @return NetworkEvents object
     */
    public NetworkEvents withPingTimeout(int timeout) {
        validator.checkPositive(timeout, "timeout has to be positive value");
        checkPingEnabled("setting pingTimeout, but ping is disabled");
        pingWrapper.setTimeout(timeout);
        return this;
    }

    private void checkPingEnabled(String message) {
        if (!pingWrapper.isPingEnabled()) {
            throw new IllegalStateException(message);
        }
    }

    /**
     * disables ping used for Internet connection check
     * when it will be called, ConnectivityStatus will never be equal to:
     * WIFI_CONNECTED_HAS_INTERNET or WIFI_CONNECTED_HAS_NO_INTERNET
     *
     * @return NetworkEvents object
     */
    public NetworkEvents withoutPing() {
        pingWrapper.disablePing();
        return this;
    }

    /**
     * disables wifi access points scan
     * when it will be called, WifiSignalStrengthChanged event will never occur
     *
     * @return NetworkEvents object
     */
    public NetworkEvents withoutWifiAccessPointsScan() {
        this.wifiAccessPointsScanEnabled = false;
        return this;
    }

    /**
     * registers NetworkEvents
     * should be executed in onCreate() method in activity
     * or during creating instance of class extending Application
     */
    public void register() {
        registerNetworkConnectionChangeReceiver();
        registerInternetConnectionChangeReceiver();

        if (wifiAccessPointsScanEnabled) {
            registerWifiSignalStrengthChangeReceiver();
            // start WiFi scan in order to refresh access point list
            // if this won't be called WifiSignalStrengthChanged may never occur
            NetworkHelper.startWifiScan(context);
        }
    }

    /**
     * unregisters NetworkEvents
     * should be executed in onDestroy() method in activity
     * or during destroying instance of class extending Application
     */
    public void unregister() {
        context.unregisterReceiver(networkConnectionChangeReceiver);
        context.unregisterReceiver(internetConnectionChangeReceiver);
        if (wifiAccessPointsScanEnabled) {
            context.unregisterReceiver(wifiSignalStrengthChangeReceiver);
        }
    }

    private void registerNetworkConnectionChangeReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        context.registerReceiver(networkConnectionChangeReceiver, filter);
    }

    private void registerInternetConnectionChangeReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(NetworkEventsConfig.INTENT);
        context.registerReceiver(internetConnectionChangeReceiver, filter);
    }

    private void registerWifiSignalStrengthChangeReceiver() {
        IntentFilter filter = new IntentFilter(WifiManager.RSSI_CHANGED_ACTION);
        context.registerReceiver(wifiSignalStrengthChangeReceiver, filter);
    }
}