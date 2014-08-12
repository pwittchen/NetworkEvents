package com.pwittchen.icsl.library.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.pwittchen.icsl.library.config.ICSLConfig;
import com.pwittchen.icsl.library.helper.NetworkHelper;

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

