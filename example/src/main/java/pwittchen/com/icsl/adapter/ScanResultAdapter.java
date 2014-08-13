package pwittchen.com.icsl.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pwittchen.icsl.library.helper.NetworkHelper;

import java.util.List;

import pwittchen.com.icsl.R;
import pwittchen.com.icsl.room.RoomLocator;

public class ScanResultAdapter extends ArrayAdapter<ScanResult> {

    private Context context;
    private int listItemResourceId;
    private List<ScanResult> objects;
    private RoomLocator roomLocator;

    public ScanResultAdapter(Context context, int listItemResourceId, List<ScanResult> objects) {
        super(context, listItemResourceId, objects);
        this.context = context;
        this.listItemResourceId = listItemResourceId;
        this.objects = objects;
        roomLocator = new RoomLocator(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(listItemResourceId, parent, false);
        ScanResult scanResult = objects.get(position);
        TextView tvSsId = (TextView) rowView.findViewById(R.id.tv_ssid);
        TextView tvBssId = (TextView) rowView.findViewById(R.id.tv_bssid);
        TextView tvRoom = (TextView) rowView.findViewById(R.id.tv_room);
        TextView tvLevel = (TextView) rowView.findViewById(R.id.tv_level);
        TextView tvDistance = (TextView) rowView.findViewById(R.id.tv_distance);
        tvSsId.setText(String.format("SSID: %s", scanResult.SSID));
        tvBssId.setText(String.format("BSSID: %s", scanResult.BSSID));
        tvLevel.setText(String.format("Level: %d", scanResult.level));
        tvDistance.setText(String.format("Distance: %f m", NetworkHelper.calculateDistance(scanResult.level, scanResult.frequency)));
        tvRoom.setText(String.format("Room: %s", roomLocator.getRoomName(scanResult.BSSID)));
        return rowView;
    }
}
