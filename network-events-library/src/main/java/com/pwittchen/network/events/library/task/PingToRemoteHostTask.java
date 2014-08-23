package com.pwittchen.network.events.library.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.pwittchen.network.events.library.config.NetworkEventsConfig;
import com.pwittchen.network.events.library.helper.NetworkHelper;

public class PingToRemoteHostTask extends AsyncTask<Void, Void, Boolean> {

    private Context context;

    public PingToRemoteHostTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return NetworkHelper.pingRemoteHostSync(NetworkEventsConfig.getRemoteHostForPing());
    }

    @Override
    protected void onPostExecute(Boolean connectedToInternet) {
        super.onPostExecute(connectedToInternet);
        Intent intent = new Intent(NetworkEventsConfig.getIntentNameForInternetConnectionChange());
        intent.putExtra(NetworkEventsConfig.getIntentNameExtraForInternetConnectionChange(), connectedToInternet);
        context.sendBroadcast(intent);
    }
}

