package pwittchen.com.icsl.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.pwittchen.icsl.library.event.WifiAccessPointsRefreshedEvent;
import com.squareup.otto.Subscribe;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import pwittchen.com.icsl.R;
import pwittchen.com.icsl.eventbus.BusProvider;
import pwittchen.com.icsl.room.RoomLocator;

/**
 * Exemplary activity used for user location
 * inside the building basing on MAC addresses of the access points
 * It's still under development and may contain errors
 */
public class RoomLocatorActivity extends Activity {

    private RoomLocator roomLocator;
    private TextView tvLastUpdate;
    private TextView tvRoomLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_locator);
        roomLocator = new RoomLocator(this);
        tvLastUpdate = (TextView) findViewById(R.id.tv_last_update);
        tvRoomLocation = (TextView) findViewById(R.id.tv_room_location);
        tvRoomLocation.setText(roomLocator.getNearestRoom());
        setLastUpdate();
    }

    @Subscribe
    public void wifiAccessPointsRefreshed(WifiAccessPointsRefreshedEvent event) {
        tvRoomLocation.setText(roomLocator.getNearestRoom());
        setLastUpdate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register event bus
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // unregister event bus
        BusProvider.getInstance().unregister(this);
    }

    private void setLastUpdate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd H:m:s");
        tvLastUpdate.setText(String.format("last update: %s", dateTimeFormatter.print(new DateTime())));
    }
}
