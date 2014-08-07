package pwittchen.com.internetconnectionstatelistener.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import pwittchen.com.internetconnectionstatelistener.config.Config;
import pwittchen.com.internetconnectionstatelistener.helper.NetworkHelper;

public class PingToRemoteHostTask extends AsyncTask<Void, Void, Boolean> {

    private Context context;

    public PingToRemoteHostTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return NetworkHelper.pingRemoteHostSync(Config.getRemoteHostForPing());
    }

    @Override
    protected void onPostExecute(Boolean connectedToInternet) {
        super.onPostExecute(connectedToInternet);
        Intent intent = new Intent(Config.getIntentName());
        intent.putExtra(Config.getIntentNameExtra(), connectedToInternet);
        context.sendBroadcast(intent);
    }
}

