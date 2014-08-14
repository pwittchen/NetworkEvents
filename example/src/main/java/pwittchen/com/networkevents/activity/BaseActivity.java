package pwittchen.com.networkevents.activity;

import android.app.Activity;
import android.os.Bundle;

import pwittchen.com.networkevents.provider.BusProvider;
import pwittchen.com.networkevents.provider.NetworkEventsProvider;

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // register InternetConnectionStateListener
        NetworkEventsProvider.getInstance().register();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register event bus
        BusProvider.getInstance().register(this);
        // resume internetConnectionStateListener
        NetworkEventsProvider.getInstance().resume();
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
        NetworkEventsProvider.getInstance().unregister();
    }
}
