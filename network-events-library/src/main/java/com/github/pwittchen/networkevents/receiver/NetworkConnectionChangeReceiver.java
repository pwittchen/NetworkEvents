package com.github.pwittchen.networkevents.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.github.pwittchen.networkevents.ConnectivityStatus;
import com.github.pwittchen.networkevents.event.ConnectivityChanged;
import com.github.pwittchen.networkevents.NetworkHelper;
import com.github.pwittchen.networkevents.PingTask;
import com.squareup.otto.Bus;

public class NetworkConnectionChangeReceiver extends BroadcastReceiver {
    private static boolean firstConnect = true;
    private static boolean firstDisconnect = true;
    private Bus eventBus;

    public NetworkConnectionChangeReceiver(Bus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        onReceivePatched(context, new Runnable() {
            @Override
            public void run() {
                ConnectivityStatus connectivityStatus = NetworkHelper.getConnectivityStatus(context);
                eventBus.post(new ConnectivityChanged(connectivityStatus));
                if (connectivityStatus == ConnectivityStatus.WIFI_CONNECTED) {
                    new PingTask(context).execute();
                }
            }
        });
    }

    /**
     * Sometimes Android may receive an information about changed connection state multiple times.
     * This is device specific behaviour that's why we're adding here workaround with firstConnect variable
     * to send this event only once.Cases for connecting to network and going off-line have to be treated separately
     * in order to work correctly. That's why we added additional firstDisconnect variable
     *
     * Known issue: for some reason going off-line event is still called 2 times on Nexus 5,
     * but connecting to Wifi or mobile network events works fine and are called once.
     */
    private void onReceivePatched(Context context, Runnable runnable) {
        synchronized (this) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && firstConnect) { // connecting to Wifi or mobile network
                runnable.run();
                firstConnect = false;
            } else {
                firstConnect = true;
            }

            if(activeNetworkInfo == null && firstDisconnect) { // going off-line
                runnable.run();
                firstDisconnect = false;
            } else {
                firstDisconnect = true;
            }
        }
    }
}