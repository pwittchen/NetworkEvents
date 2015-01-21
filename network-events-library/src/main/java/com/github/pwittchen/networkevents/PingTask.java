package com.github.pwittchen.networkevents;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

/**
 * Pings remote host in order to check if we have access
 * to the Internet network through WiFi network
 */
public class PingTask extends AsyncTask<Void, Void, Boolean> {
    private Context context;

    public PingTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return NetworkHelper.ping(NetworkEventsConfig.getRemoteHostForPing());
    }

    @Override
    protected void onPostExecute(Boolean connectedToInternet) {
        super.onPostExecute(connectedToInternet);
        Intent intent = new Intent(NetworkEventsConfig.getIntentNameForInternetConnectionChange());
        intent.putExtra(NetworkEventsConfig.getIntentNameExtraForInternetConnectionChange(), connectedToInternet);
        context.sendBroadcast(intent);
    }
}

