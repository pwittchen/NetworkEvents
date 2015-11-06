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
package com.github.pwittchen.networkevents.dagger.app;

import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.github.pwittchen.networkevents.library.BusWrapper;
import com.github.pwittchen.networkevents.library.NetworkEvents;
import com.github.pwittchen.networkevents.library.event.ConnectivityChanged;
import com.github.pwittchen.networkevents.library.event.WifiSignalStrengthChanged;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class MainActivity extends BaseActivity {
  @Inject public BusWrapper busWrapper;
  @Inject public NetworkEvents networkEvents;
  @InjectView(R.id.connectivity_status) protected TextView connectivityStatus;
  @InjectView(R.id.access_points) protected ListView accessPoints;
  @InjectView(R.id.mobile_network_type) protected TextView mobileNetworkType;

  @Subscribe @SuppressWarnings("unused") public void onEvent(ConnectivityChanged event) {
    connectivityStatus.setText(event.getConnectivityStatus().toString());
    mobileNetworkType.setText(event.getMobileNetworkType().toString());
  }

  @Subscribe @SuppressWarnings("unused") public void onEvent(WifiSignalStrengthChanged event) {
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
    ButterKnife.inject(this);
  }

  @Override protected void onResume() {
    super.onResume();
    busWrapper.register(this);
    networkEvents.register();
  }

  @Override protected void onPause() {
    super.onPause();
    busWrapper.unregister(this);
    networkEvents.unregister();
  }
}
