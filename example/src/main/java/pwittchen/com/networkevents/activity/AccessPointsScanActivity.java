package pwittchen.com.networkevents.activity;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.ListView;

import com.pwittchen.network.events.library.event.WifiAccessPointsSignalStrengthChangedEvent;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import pwittchen.com.networkevents.R;
import pwittchen.com.networkevents.adapter.ScanResultAdapter;

public class AccessPointsScanActivity extends BaseActivity {
    private ListView lvAccessPointScanResults;
    private List<ScanResult> accessPoints = new ArrayList<ScanResult>();
    private ScanResultAdapter scanResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_points_scan);
        lvAccessPointScanResults = (ListView) findViewById(R.id.lv_access_point_scan_results);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setScanResultAdapter();
    }

    @Subscribe
    public void wifiAccessPointsRefreshed(WifiAccessPointsSignalStrengthChangedEvent event) {
        setScanResultAdapter();
    }

    private void setScanResultAdapter() {
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        accessPoints = wifiManager.getScanResults();
        scanResultAdapter = new ScanResultAdapter(this, R.layout.list_row, accessPoints);
        lvAccessPointScanResults.setAdapter(scanResultAdapter);
        scanResultAdapter.notifyDataSetChanged();
    }
}
