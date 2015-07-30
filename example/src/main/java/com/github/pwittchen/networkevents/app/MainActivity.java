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
package com.github.pwittchen.networkevents.app;

import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pwittchen.networkevents.library.BusWrapper;
import com.github.pwittchen.networkevents.library.NetworkEvents;
import com.github.pwittchen.networkevents.library.event.ConnectivityChanged;
import com.github.pwittchen.networkevents.library.event.WifiSignalStrengthChanged;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BusWrapper busWrapper;
    private NetworkEvents networkEvents;

    private TextView connectivityStatus;
    private TextView mobileNetworkType;
    private ListView accessPoints;

    @Subscribe
    @SuppressWarnings("unused")
    public void onEvent(ConnectivityChanged event) {
        connectivityStatus.setText(event.getConnectivityStatus().toString());
        mobileNetworkType.setText(event.getMobileNetworkType().toString());
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onEvent(WifiSignalStrengthChanged event) {
        List<String> wifiScanResults = new ArrayList<>();

        for (ScanResult scanResult : event.getWifiScanResults()) {
            wifiScanResults.add(scanResult.SSID);
        }

        accessPoints.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, wifiScanResults));
        Toast.makeText(this, getString(R.string.wifi_signal_strength_changed), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectivityStatus = (TextView) findViewById(R.id.connectivity_status);
        mobileNetworkType = (TextView) findViewById(R.id.mobile_network_type);
        accessPoints = (ListView) findViewById(R.id.access_points);
        busWrapper = getOttoBusWrapper(new Bus());
        networkEvents = new NetworkEvents(this, busWrapper)
                .enableWifiScan();
    }

    @NonNull
    private BusWrapper getOttoBusWrapper(final Bus bus) {
        return new BusWrapper() {
            @Override
            public void register(Object object) {
                bus.register(object);
            }

            @Override
            public void unregister(Object object) {
                bus.unregister(object);
            }

            @Override
            public void post(Object event) {
                bus.post(event);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        busWrapper.register(this);
        networkEvents.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        busWrapper.unregister(this);
        networkEvents.unregister();
    }
}
