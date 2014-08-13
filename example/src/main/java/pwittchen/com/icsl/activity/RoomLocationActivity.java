package pwittchen.com.icsl.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.pwittchen.icsl.library.event.WifiAccessPointsRefreshedEvent;
import com.squareup.otto.Subscribe;

import pwittchen.com.icsl.R;
import pwittchen.com.icsl.room.RoomLocator;

public class RoomLocationActivity extends BaseActivity {

    private RoomLocator roomLocator;
    private TextView tvRoomLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_locator);
        roomLocator = new RoomLocator(this);
        tvRoomLocation = (TextView) findViewById(R.id.tv_room_location);
        tvRoomLocation.setText(roomLocator.getNearestRoom());
    }

    @Subscribe
    public void wifiAccessPointsRefreshed(WifiAccessPointsRefreshedEvent event) {
        tvRoomLocation.setText(roomLocator.getNearestRoom());
    }
}
