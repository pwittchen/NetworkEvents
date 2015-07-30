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

import com.github.pwittchen.networkevents.library.logger.Logger;
import com.github.pwittchen.networkevents.library.logger.NetworkEventsLogger;
import com.github.pwittchen.networkevents.library.internet.OnlineChecker;
import com.github.pwittchen.networkevents.library.internet.OnlineCheckerImpl;
import com.github.pwittchen.networkevents.library.receiver.InternetConnectionChangeReceiver;
import com.github.pwittchen.networkevents.library.receiver.NetworkConnectionChangeReceiver;
import com.github.pwittchen.networkevents.library.receiver.WifiSignalStrengthChangeReceiver;

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
    private boolean wifiAccessPointsScanEnabled = false;
    private final Context context;
    private final OnlineChecker onlineChecker;
    private final NetworkConnectionChangeReceiver networkConnectionChangeReceiver;
    private final InternetConnectionChangeReceiver internetConnectionChangeReceiver;
    private final WifiSignalStrengthChangeReceiver wifiSignalStrengthChangeReceiver;

    /**
     * initializes NetworkEvents object
     * with NetworkEventsLogger as default logger
     *
     * @param context    Android context
     * @param busWrapper Wrapper for event bus
     */
    public NetworkEvents(Context context, BusWrapper busWrapper) {
        this(context, busWrapper, new NetworkEventsLogger());
    }

    /**
     * initializes NetworkEvents object
     *
     * @param context    Android context
     * @param busWrapper Wrapper fo event bus
     * @param logger     message logger (NetworkEventsLogger logs messages to LogCat)
     */
    public NetworkEvents(Context context, BusWrapper busWrapper, Logger logger) {
        checkNotNull(context, "context == null");
        checkNotNull(busWrapper, "busWrapper == null");
        checkNotNull(logger, "logger == null");
        this.context = context;
        this.onlineChecker = new OnlineCheckerImpl(context);
        this.networkConnectionChangeReceiver = new NetworkConnectionChangeReceiver(busWrapper, logger, onlineChecker);
        this.internetConnectionChangeReceiver = new InternetConnectionChangeReceiver(busWrapper, logger);
        this.wifiSignalStrengthChangeReceiver = new WifiSignalStrengthChangeReceiver(busWrapper, logger, context);
    }

    /**
     * enables wifi access points scan
     * when it's not called, WifiSignalStrengthChanged event will never occur
     *
     * @return NetworkEvents object
     */
    public NetworkEvents enableWifiScan() {
        this.wifiAccessPointsScanEnabled = true;
        return this;
    }

    /**
     * enables internet connection check
     * when it's not called, WIFI_CONNECTED_HAS_INTERNET
     * and WIFI_CONNECTED_HAS_NO_INTERNET ConnectivityStatus will never be set
     * Please, be careful! Internet connection check may contain bugs
     * that's why it's disabled by default.
     *
     * @return NetworkEvents object
     */
    public NetworkEvents enableInternetCheck() {
        networkConnectionChangeReceiver.enableInternetCheck();
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
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            wifiManager.startScan();
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
        filter.addAction(InternetConnectionChangeReceiver.INTENT);
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