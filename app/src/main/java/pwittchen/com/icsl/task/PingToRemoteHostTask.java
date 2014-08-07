package pwittchen.com.icsl.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import pwittchen.com.icsl.config.ICSLConfig;
import pwittchen.com.icsl.helper.NetworkHelper;

public class PingToRemoteHostTask extends AsyncTask<Void, Void, Boolean> {

    private Context context;

    public PingToRemoteHostTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return NetworkHelper.pingRemoteHostSync(ICSLConfig.getRemoteHostForPing());
    }

    @Override
    protected void onPostExecute(Boolean connectedToInternet) {
        super.onPostExecute(connectedToInternet);
        Intent intent = new Intent(ICSLConfig.getIntentName());
        intent.putExtra(ICSLConfig.getIntentNameExtra(), connectedToInternet);
        context.sendBroadcast(intent);
    }
}

