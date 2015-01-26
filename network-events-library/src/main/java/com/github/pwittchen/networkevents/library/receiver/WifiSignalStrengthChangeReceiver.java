package com.github.pwittchen.networkevents.library.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.github.pwittchen.networkevents.library.event.WifiSignalStrengthChanged;
import com.squareup.otto.Bus;

public final class WifiSignalStrengthChangeReceiver extends BroadcastReceiver {
    private final Bus bus;

    public WifiSignalStrengthChangeReceiver(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        bus.post(new WifiSignalStrengthChanged(context));
    }
}
