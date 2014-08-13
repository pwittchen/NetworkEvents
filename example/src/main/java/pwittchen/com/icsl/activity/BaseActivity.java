package pwittchen.com.icsl.activity;

import android.app.Activity;
import android.os.Bundle;

import com.pwittchen.icsl.library.InternetConnectionStateListener;
import com.pwittchen.icsl.library.helper.NetworkHelper;

import pwittchen.com.icsl.eventbus.BusProvider;

public abstract class BaseActivity extends Activity {

    private InternetConnectionStateListener internetConnectionStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // passing Context and instance of Otto Event Bus
        internetConnectionStateListener = new InternetConnectionStateListener(this, BusProvider.getInstance());
        // register InternetConnectionStateListener
        internetConnectionStateListener.register();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register event bus
        BusProvider.getInstance().register(this);
        // start WiFi scan in order to refresh access point list
        // if this won't be called WifiAccessPointsRefreshedEvent may never occur
        NetworkHelper.startWifiScan(this);
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
}
