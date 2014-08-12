package pwittchen.com.icsl.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pwittchen.com.icsl.R;

public class ScanResultAdapter extends ArrayAdapter<ScanResult> {

    private Context context;
    private int listItemResourceId;
    private List<ScanResult> objects;

    public ScanResultAdapter(Context context, int listItemResourceId, List<ScanResult> objects) {
        super(context, listItemResourceId, objects);
        this.context = context;
        this.listItemResourceId = listItemResourceId;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(listItemResourceId, parent, false);
        TextView tvItem = (TextView) rowView.findViewById(R.id.tv_item_name);
        tvItem.setText(objects.get(position).toString());
        return rowView;
    }
}
