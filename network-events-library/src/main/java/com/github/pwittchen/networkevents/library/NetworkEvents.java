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
    private final Context context;
    private final NetworkConnectionChangeReceiver networkConnectionChangeReceiver;
    private final InternetConnectionChangeReceiver internetConnectionChangeReceiver;
    private final WifiSignalStrengthChangeReceiver wifiSignalStrengthChangeReceiver;

    /**
     * Initializes NetworkEvents and necessary objects
     * @param context Android Context
     * @param bus     instance of the Otto Event Bus
     */
    public NetworkEvents(Context context, Bus bus) {
        checkNotNull(context, "context == null");
        checkNotNull(bus, "bus == null");
        this.context = context;
        this.networkConnectionChangeReceiver = new NetworkConnectionChangeReceiver(bus);
        this.internetConnectionChangeReceiver = new InternetConnectionChangeReceiver(bus);
        this.wifiSignalStrengthChangeReceiver = new WifiSignalStrengthChangeReceiver(bus);
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
         * if this won't be called WifiSignalStrengthChanged may never occur
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
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
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

    private void checkNotNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }
}