package pwittchen.com.internetconnectionstatelistener.task;

import android.content.Intent;
import android.os.AsyncTask;

import pwittchen.com.internetconnectionstatelistener.BaseApplication;
import pwittchen.com.internetconnectionstatelistener.R;
import pwittchen.com.internetconnectionstatelistener.helper.NetworkHelper;
import pwittchen.com.internetconnectionstatelistener.helper.StringHelper;

public class PingToRemoteHostTask extends AsyncTask<Void, Void, Boolean> {
    @Override
    protected Boolean doInBackground(Void... params) {
        return NetworkHelper.pingRemoteHostSync(StringHelper.getString(R.string.remote_host_for_ping));
    }

    @Override
    protected void onPostExecute(Boolean connectedToInternet) {
        super.onPostExecute(connectedToInternet);
        Intent intent = new Intent(StringHelper.getString(R.string.internet_connection_state_change_intent));
        intent.putExtra(StringHelper.getString(R.string.internet_connection_intent_extra), connectedToInternet);
        BaseApplication.getContext().sendBroadcast(intent);
    }
}

