package com.github.pwittchen.networkevents.library.receiver;

import android.content.Context;
import android.content.Intent;

import com.github.pwittchen.networkevents.library.NetworkHelper;
import com.github.pwittchen.networkevents.library.event.WifiSignalStrengthChanged;
import com.squareup.otto.Bus;

public final class WifiSignalStrengthChangeReceiver extends BaseBroadcastReceiver {

    public WifiSignalStrengthChangeReceiver(Bus bus) {
        super(bus);
    }

    /**
     * We need to start WiFi scan after receiving an Intent
     * in order to get update with fresh data as soon as possible
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkHelper.startWifiScan(context);
        bus.post(new WifiSignalStrengthChanged());
    }
}
