package pwittchen.com.internetconnectionstatelistener.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import pwittchen.com.internetconnectionstatelistener.config.ICSLConfig;
import pwittchen.com.internetconnectionstatelistener.helper.NetworkHelper;

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

