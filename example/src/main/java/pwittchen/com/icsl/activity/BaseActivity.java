package pwittchen.com.icsl.activity;

import android.app.Activity;
import android.os.Bundle;

import pwittchen.com.icsl.provider.BusProvider;
import pwittchen.com.icsl.provider.InternetConnectionStateListenerProvider;

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // register InternetConnectionStateListener
        InternetConnectionStateListenerProvider.getInstance().register();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register event bus
        BusProvider.getInstance().register(this);
        // resume internetConnectionStateListener
        InternetConnectionStateListenerProvider.getInstance().resume();
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
        InternetConnectionStateListenerProvider.getInstance().unregister();
    }
}
