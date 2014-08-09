package pwittchen.com.icsl.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.pwitchen.icsl.library.InternetConnectionStateListener;
import com.pwitchen.icsl.library.event.ConnectivityStatusChangedEvent;
import com.squareup.otto.Subscribe;

import pwittchen.com.icsl.R;
import pwittchen.com.icsl.eventbus.BusProvider;

public class MainActivity extends Activity {

    private InternetConnectionStateListener internetConnectionStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // creating new instance of InternetConnectionStateListener class
        // passing Context and instance of Otto Event Bus
        internetConnectionStateListener = new InternetConnectionStateListener(this, BusProvider.getInstance());

        // register InternetConnectionStateListener
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

        // unregister InternetConnectionStateListener
        internetConnectionStateListener.unregister();
    }

    @Subscribe
    public void connectivityStatusChanged(ConnectivityStatusChangedEvent event) {
        // subscribing for an ConnectivityStatusChangedEvent
        // when Connectivity status changes, we can perform any action we want to
        // in this case, we are simply displaying toast with connectivity status
        Toast.makeText(this, event.getConnectivityStatus().toString(), Toast.LENGTH_SHORT).show();
    }
}
