package com.pwittchen.icsl.library.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.pwittchen.icsl.library.config.ICSLConfig;
import com.pwittchen.icsl.library.event.WifiScanFinishedEvent;
import com.pwittchen.icsl.library.helper.NetworkHelper;
import com.squareup.otto.Bus;

public class WifiScanReceiver extends BroadcastReceiver {
    private Bus eventBus;

    public WifiScanReceiver(Bus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ICSLConfig.getIntentNameForWifiAccessPointsScanFinished())) {
            eventBus.post(new WifiScanFinishedEvent(NetworkHelper.getAccessPointList(context)));
        }
    }
}
