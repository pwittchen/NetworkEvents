package com.pwittchen.network.events.library.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.pwittchen.network.events.library.event.WifiSignalStrengthChanged;
import com.squareup.otto.Bus;

public class WifiSignalStrengthChangeReceiver extends BroadcastReceiver {
    private Bus eventBus;

    public WifiSignalStrengthChangeReceiver(Bus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        eventBus.post(new WifiSignalStrengthChanged(context));
    }
}
