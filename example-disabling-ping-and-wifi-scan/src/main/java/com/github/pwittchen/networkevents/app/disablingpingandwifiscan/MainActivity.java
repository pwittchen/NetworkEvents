package com.github.pwittchen.networkevents.app.disablingpingandwifiscan;

import android.app.Activity;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pwittchen.networkevents.library.NetworkEvents;
import com.github.pwittchen.networkevents.library.NetworkHelper;
import com.github.pwittchen.networkevents.library.event.ConnectivityChanged;
import com.github.pwittchen.networkevents.library.event.WifiSignalStrengthChanged;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private Bus bus;
    private NetworkEvents networkEvents;

    private TextView connectivityStatus;
    private ListView accessPoints;

    @Subscribe
    @SuppressWarnings("unused")
    public void onConnectivityChanged(ConnectivityChanged event) {
        /**
         * In this example ConnectivityStatus should never be equal to:
         * - WIFI_CONNECTED_HAS_INTERNET
         * - WIFI_CONNECTED_HAS_NO_INTERNET
         * due to disabled Ping in onCreate() method.
         * Only the following statuses can occur:
         * - WIFI_CONNECTED
         * - MOBILE_CONNECTED
         * - OFFLINE
         */
        connectivityStatus.setText(event.getConnectivityStatus().toString());
    }

    /**
     * In this example, this event should never occur
     * due to disabled Wifi Access Points Scan in onCreate() method
     * @param event WifiSignalStrengthChanged
     */
    @Subscribe
    @SuppressWarnings("unused")
    public void onWifiSignalStrengthChanged(WifiSignalStrengthChanged event) {
        List<String> wifiScanResults = new ArrayList<>();

        for (ScanResult scanResult : NetworkHelper.getWifiScanResults(this)) {
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
        accessPoints = (ListView) findViewById(R.id.access_points);

        bus = new Bus();
        networkEvents = new NetworkEvents(this, bus)
                .withoutPing()
                .enableWifiScan();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
        networkEvents.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
        networkEvents.unregister();
    }
}
