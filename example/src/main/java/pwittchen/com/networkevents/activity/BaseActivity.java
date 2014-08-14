package pwittchen.com.networkevents.activity;

import android.app.Activity;

import pwittchen.com.networkevents.provider.BusProvider;
import pwittchen.com.networkevents.provider.NetworkEventsProvider;

public abstract class BaseActivity extends Activity {
    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
        NetworkEventsProvider.getInstance().register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
        NetworkEventsProvider.getInstance().unregister();
    }
}
