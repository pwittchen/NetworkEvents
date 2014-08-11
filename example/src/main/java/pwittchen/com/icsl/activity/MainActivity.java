package pwittchen.com.icsl.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.pwitchen.icsl.library.InternetConnectionStateListener;
import com.pwitchen.icsl.library.event.ConnectivityStatusChangedEvent;
import com.pwitchen.icsl.library.receiver.ConnectivityStatus;
import com.squareup.otto.Subscribe;

import pwittchen.com.icsl.R;
import pwittchen.com.icsl.eventbus.BusProvider;

public class MainActivity extends Activity {

    private InternetConnectionStateListener internetConnectionStateListener;
    private TextView tvConnectivityStatus;
    private TextView tvWifiInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvConnectivityStatus = (TextView) findViewById(R.id.tv_connectivity_status);
        tvWifiInfo = (TextView) findViewById(R.id.tv_wifi_info);

        // creating new instance of InternetConnectionStateListener class
        // passing Context and instance of Otto Event Bus
        internetConnectionStateListener = new InternetConnectionStateListener(this, BusProvider.getInstance());

        // register InternetConnectionStateListener
        internetConnectionStateListener.register();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // registering event bus
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // unregistering event bus
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
        if (status.equals(ConnectivityStatus.WIFI_CONNECTED)) {
            tvWifiInfo.setText(String.format("WiFi Info:\n%s", event.getWifiInfo().toString()));
        } else if (status.equals(ConnectivityStatus.OFFLINE) || status.equals(ConnectivityStatus.MOBILE_CONNECTED)) {
            tvWifiInfo.setText("WiFi Info is not available.");
        }
    }
}
