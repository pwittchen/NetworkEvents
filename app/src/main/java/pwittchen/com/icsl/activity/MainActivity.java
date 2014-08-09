package pwittchen.com.icsl.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import pwittchen.com.icsl.R;
import pwittchen.com.icsl.event.ConnectivityStatusChangedEvent;
import pwittchen.com.icsl.eventbus.BusProvider;
import pwittchen.com.icsl.InternetConnectionStateListener;

public class MainActivity extends Activity {

    private InternetConnectionStateListener internetConnectionStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        internetConnectionStateListener = new InternetConnectionStateListener(this, BusProvider.getInstance());
        internetConnectionStateListener.register();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        internetConnectionStateListener.unregister();
    }

    @Subscribe
    public void connectivityStatusChanged(ConnectivityStatusChangedEvent event) {
        Toast.makeText(this, event.getConnectivityStatus().toString(), Toast.LENGTH_SHORT).show();
    }
}
