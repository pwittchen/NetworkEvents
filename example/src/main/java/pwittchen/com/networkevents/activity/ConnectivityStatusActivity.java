package pwittchen.com.networkevents.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.pwitchen.network.events.library.event.ConnectivityStatusChangedEvent;
import com.pwitchen.network.events.library.receiver.ConnectivityStatus;
import com.squareup.otto.Subscribe;

import pwittchen.com.networkevents.R;

public class ConnectivityStatusActivity extends BaseActivity {

    private TextView tvConnectivityStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connectivity_status);
        tvConnectivityStatus = (TextView) findViewById(R.id.tv_connectivity_status);
    }

    @Subscribe
    public void connectivityStatusChanged(ConnectivityStatusChangedEvent event) {
        ConnectivityStatus status = event.getConnectivityStatus();
        tvConnectivityStatus.setText(String.format("connectivity status: %s", status.toString()));
    }
}
