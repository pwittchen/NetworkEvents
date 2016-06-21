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
package com.github.pwittchen.networkevents.greenrobot.app;

import android.app.Activity;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.github.pwittchen.networkevents.library.BusWrapper;
import com.github.pwittchen.networkevents.library.NetworkEvents;
import com.github.pwittchen.networkevents.library.event.ConnectivityChanged;
import com.github.pwittchen.networkevents.library.event.WifiSignalStrengthChanged;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
  private BusWrapper busWrapper;
  private NetworkEvents networkEvents;

  private TextView connectivityStatus;
  private TextView mobileNetworkType;
  private ListView accessPoints;

  @SuppressWarnings("unused") @Subscribe public void onEvent(ConnectivityChanged event) {
    connectivityStatus.setText(event.getConnectivityStatus().toString());
    mobileNetworkType.setText(event.getMobileNetworkType().toString());
  }

  @SuppressWarnings("unused") @Subscribe public void onEvent(WifiSignalStrengthChanged event) {
    List<String> wifiScanResults = new ArrayList<>();

    for (ScanResult scanResult : event.getWifiScanResults()) {
      wifiScanResults.add(scanResult.SSID);
    }

    int itemLayoutId = android.R.layout.simple_list_item_1;
    accessPoints.setAdapter(new ArrayAdapter<>(this, itemLayoutId, wifiScanResults));
    String message = getString(R.string.wifi_signal_strength_changed);
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    connectivityStatus = (TextView) findViewById(R.id.connectivity_status);
    mobileNetworkType = (TextView) findViewById(R.id.mobile_network_type);
    accessPoints = (ListView) findViewById(R.id.access_points);
    final EventBus bus = new EventBus();
    busWrapper = getGreenRobotBusWrapper(bus);
    networkEvents = new NetworkEvents(getApplicationContext(), busWrapper).enableWifiScan();
  }

  @NonNull private BusWrapper getGreenRobotBusWrapper(final EventBus bus) {
    return new BusWrapper() {
      @Override public void register(Object object) {
        bus.register(object);
      }

      @Override public void unregister(Object object) {
        bus.unregister(object);
      }

      @Override public void post(Object event) {
        bus.post(event);
      }
    };
  }

  @Override protected void onStart() {
    super.onStart();
    busWrapper.register(this);
    networkEvents.register();
  }

  @Override protected void onStop() {
    busWrapper.unregister(this);
    networkEvents.unregister();
    super.onStop();
  }
}
