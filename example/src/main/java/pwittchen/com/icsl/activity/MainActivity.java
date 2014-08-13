package pwittchen.com.icsl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pwittchen.com.icsl.R;

public class MainActivity extends BaseActivity {

    private Button bConnectivityStatus;
    private Button bAccessPointScan;
    private Button bRoomLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bConnectivityStatus = (Button) findViewById(R.id.b_connectivity_status);
        bAccessPointScan = (Button) findViewById(R.id.b_access_point_scan);
        bRoomLocation = (Button) findViewById(R.id.b_room_location);
        setListeners();
    }

    private void setListeners() {
        bConnectivityStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ConnectivityStatusActivity.class));
            }
        });

        bAccessPointScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AccessPointsScanActivity.class));
            }
        });

        bRoomLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RoomLocationActivity.class));
            }
        });
    }

}
