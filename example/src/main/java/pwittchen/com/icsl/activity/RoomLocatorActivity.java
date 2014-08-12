package pwittchen.com.icsl.activity;

import android.app.Activity;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.widget.TextView;

import com.pwittchen.icsl.library.event.WifiScanFinishedEvent;
import com.squareup.otto.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pwittchen.com.icsl.R;
import pwittchen.com.icsl.eventbus.BusProvider;

/**
 * Exemplary activity used for user location
 * inside the building basing on access points MAC addresses
 * It's still under development and may contain errors
 */
public class RoomLocatorActivity extends Activity {

    private TextView tvRoomLocation;
    private TextView tvApDistance;

    private final static Map<String, String> accessPoints = new HashMap<String, String>();

    static {
        accessPoints.put("24:A4:3C:03:B3:42", "F3-203 (Piętro 2, Budynek szkoleniowy)");
        accessPoints.put("24:A4:3C:03:B4:82", "F3-013 (Parter, Budynek sportowy)");
        accessPoints.put("24:A4:3C:03:38:0E", "F3-002 (Parter, Budynek szkoleniowy)");
        accessPoints.put("24:A4:3C:03:38:9C", "F3-103 (Piętro 1, Budynek szkoleniowy)");
        accessPoints.put("24:A4:3C:03:3A:5D", "F3-201 (Piętro 2, Budynek szkoleniowy)");
        accessPoints.put("24:A4:3C:03:3A:50", "F3-001 (Parter, Budynek szkoleniowy)");
        accessPoints.put("24:A4:3C:03:38:ED", "F3 (Parter, Korytarz)");
        accessPoints.put("24:A4:3C:03:3A:5B", "F3 (Parter, Restauracja, Budynek sportowy)");
        accessPoints.put("24:A4:3C:03:38:B2", "F3 (Piętro 1, Restauracja, Budynek sportowy)");
        accessPoints.put("24:A4:3C:03:38:F0", "F3 (Piętro 1, Korytarz, Budynek szkoleniowy)");
        accessPoints.put("24:A4:3C:03:38:C3", "F3 (Piętro 2, Korytarz, Budynek szkoleniowy)");
        accessPoints.put("24:A4:3C:03:38:20", "F3-101 (Piętro 1, Korytarz, Budynek szkoleniowy)");
        accessPoints.put("24:A4:3C:03:B4:E1", "F3 (Budynek sportowy)");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_locator);
        tvRoomLocation = (TextView) findViewById(R.id.tv_room_location);
        tvApDistance = (TextView) findViewById(R.id.tv_ap_distance);
    }

    @Subscribe
    public void wifiScanFinished(WifiScanFinishedEvent event) {
        List<ScanResult> accessPointList = event.getAccessPointList();

        if (accessPointList.isEmpty()) {
            return;
        }

        tvRoomLocation.setText(getNearestRoomName(accessPointList));
    }

    private String getNearestRoomName(List<ScanResult> accessPointList) {
        double minDistance = 100000d; // very big value
        double currentDistance;
        String nearestRoomName = null;
        String currentNearestRoomName;
        String currentNearestBSSID;

        for (ScanResult scanResult : accessPointList) {
            currentDistance = calculateDistance(scanResult.level, scanResult.frequency);
            if (currentDistance < minDistance) {
                currentNearestBSSID = scanResult.BSSID.toUpperCase();
                currentNearestRoomName = accessPoints.get(currentNearestBSSID);
                if (currentNearestRoomName != null) {
                    minDistance = currentDistance;
                    nearestRoomName = currentNearestRoomName;
                    tvApDistance.setText(String.format("Distance: %s m", String.valueOf(minDistance)));
                }
            }
        }

        return (nearestRoomName == null) ? "not recognized" : nearestRoomName;
    }

    /**
     * Calculates distance to the Access Point
     * basing on signal level in dB and frequency in MHz
     * Method based on this information:
     * http://stackoverflow.com/a/18359639/1150795
     * http://en.wikipedia.org/wiki/Free-space_path_loss
     * http://rvmiller.com/2013/05/part-1-wifi-based-trilateration-on-android/
     * @param signalLevelInDb
     * @param freqInMHz
     * @return distance in meters
     */
    public double calculateDistance(double signalLevelInDb, double freqInMHz) {
        double exp = (27.55 - (20 * Math.log10(freqInMHz)) - Math.abs(signalLevelInDb)) / 20.0;
        return Math.pow(10.0, exp);
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
}
