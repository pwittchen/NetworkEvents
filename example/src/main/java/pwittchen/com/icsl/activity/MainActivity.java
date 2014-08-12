package pwittchen.com.icsl.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.pwittchen.icsl.library.InternetConnectionStateListener;
import com.pwittchen.icsl.library.event.ConnectivityStatusChangedEvent;
import com.pwittchen.icsl.library.event.WifiScanFinishedEvent;
import com.pwittchen.icsl.library.helper.NetworkHelper;
import com.pwittchen.icsl.library.receiver.ConnectivityStatus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import pwittchen.com.icsl.R;
import pwittchen.com.icsl.adapter.ScanResultAdapter;
import pwittchen.com.icsl.eventbus.BusProvider;

public class MainActivity extends Activity {

    private InternetConnectionStateListener internetConnectionStateListener;
    private TextView tvConnectivityStatus;
    private TextView tvWifiInfo;
    private ListView lvAccessPointScanResults;
    private List<ScanResult> accessPoints = new ArrayList<ScanResult>();
    private ScanResultAdapter scanResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        setScanResultAdapter();

        // passing Context and instance of Otto Event Bus
        internetConnectionStateListener = new InternetConnectionStateListener(this, BusProvider.getInstance(), true, 1000);

        // register InternetConnectionStateListener
        internetConnectionStateListener.register();
    }

    private void initializeViews() {
        tvConnectivityStatus = (TextView) findViewById(R.id.tv_connectivity_status);
        tvWifiInfo = (TextView) findViewById(R.id.tv_wifi_info);
        lvAccessPointScanResults = (ListView) findViewById(R.id.lv_access_point_scan_results);
    }

    /**
     * This method is used only to display list of available access points.
     * It's an additional feature and we don't have to use it.
     */
    private void setScanResultAdapter() {
        scanResultAdapter = new ScanResultAdapter(this, R.layout.list_row, accessPoints);
        lvAccessPointScanResults.setAdapter(scanResultAdapter);
        scanResultAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register event bus
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // unregister event bus
        BusProvider.getInstance().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // unregister InternetConnectionStateListener
        internetConnectionStateListener.unregister();
    }

    @Subscribe
    public void connectivityStatusChanged(ConnectivityStatusChangedEvent event) {
        // subscribing for ConnectivityStatusChangedEvent
        // when Connectivity status changes, we can perform any action we want to
        // in this case, we are simply displaying info with connectivity status in TextView
        ConnectivityStatus status = event.getConnectivityStatus();
        tvConnectivityStatus.setText(String.format("connectivity status: %s", status.toString()));

        // display WiFi info in TextView, when device connects to the WiFi access point
        if (status == ConnectivityStatus.WIFI_CONNECTED) {
            tvWifiInfo.setText(String.format("WiFi Info:\n%s", event.getWifiInfo().toString()));
        } else if (status == ConnectivityStatus.OFFLINE || status == ConnectivityStatus.MOBILE_CONNECTED) {
            tvWifiInfo.setText("WiFi Info is not available.");
        }
    }

    @Subscribe
    public void wifiScanFinished(WifiScanFinishedEvent event) {
        // subscribing for WifiScanFinishedEvent
        // every given interval background service invokes WifiScanFinishedEvent
        // from which list of available access points can be retrieved
        accessPoints = event.getAccessPointList();
        setScanResultAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            // set WiFi info
            tvWifiInfo.setText(NetworkHelper.getWiFiInfo(getApplicationContext()).toString());
            // set access points and refresh adapter
            accessPoints = NetworkHelper.getAccessPointList(getApplicationContext());
            // set scanResultAdapter again in order to display access point on the list
            setScanResultAdapter();
            return true;
        } else if(id == R.id.action_room_locator) {
            Intent intent = new Intent(this, RoomLocatorActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
