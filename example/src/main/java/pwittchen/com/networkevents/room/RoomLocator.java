package pwittchen.com.networkevents.room;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import com.pwitchen.network.events.library.helper.NetworkHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomLocator {

    private final static String NOT_RECOGNIZED = "not recognized";
    private Context context;
    private final static Map<String, String> accessPointsRoomList = new HashMap<String, String>();

    static {
        accessPointsRoomList.put("24:A4:3C:04:B3:42", "F3-203 (Piętro 2, Budynek szkoleniowy)");
        accessPointsRoomList.put("24:A4:3C:04:B4:82", "F3-013 (Parter, Budynek sportowy)");
        accessPointsRoomList.put("24:A4:3C:04:38:0E", "F3-002 (Parter, Budynek szkoleniowy)");
        accessPointsRoomList.put("24:A4:3C:04:38:9C", "F3-103 (Piętro 1, Budynek szkoleniowy)");
        accessPointsRoomList.put("24:A4:3C:04:3A:5D", "F3-201 (Piętro 2, Budynek szkoleniowy)");
        accessPointsRoomList.put("24:A4:3C:04:3A:50", "F3-001 (Parter, Budynek szkoleniowy)");
        accessPointsRoomList.put("24:A4:3C:04:38:ED", "F3 (Parter, Korytarz)");
        accessPointsRoomList.put("24:A4:3C:04:3A:5B", "F3 (Parter, Restauracja, Budynek sportowy)");
        accessPointsRoomList.put("24:A4:3C:04:38:B2", "F3 (Piętro 1, Restauracja, Budynek sportowy)");
        accessPointsRoomList.put("24:A4:3C:04:38:F0", "F3 (Piętro 1, Korytarz, Budynek szkoleniowy)");
        accessPointsRoomList.put("24:A4:3C:04:38:C3", "F3 (Piętro 2, Korytarz, Budynek szkoleniowy)");
        accessPointsRoomList.put("24:A4:3C:04:38:20", "F3-101 (Piętro 1, Korytarz, Budynek szkoleniowy)");
        accessPointsRoomList.put("24:A4:3C:04:B4:E1", "F3 (Budynek sportowy)");
    }

    public RoomLocator(Context context) {
        this.context = context;
    }

    public String getNearestRoom() {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> accessPointsScanResult = wifiManager.getScanResults();

        String nearestRoomName = null;
        if (NetworkHelper.isWifiEnabled(context)) {
            ScanResult bestSignal = null;
            String currentNearestRoomName;
            String currentNearestBSSID;

            for (ScanResult scanResult : accessPointsScanResult) {
                if (bestSignal == null || WifiManager.compareSignalLevel(bestSignal.level, scanResult.level) < 0) {
                    currentNearestBSSID = scanResult.BSSID.toUpperCase();
                    currentNearestRoomName = accessPointsRoomList.get(currentNearestBSSID);
                    if (currentNearestRoomName != null) {
                        bestSignal = scanResult;
                        nearestRoomName = currentNearestRoomName;
                    }
                }
            }
        }
        return (nearestRoomName == null) ? NOT_RECOGNIZED : nearestRoomName;
    }

    public String getRoomName(String bSsId) {
        String roomName = accessPointsRoomList.get(bSsId.toUpperCase());
        return (roomName == null) ? NOT_RECOGNIZED : roomName;
    }
}
