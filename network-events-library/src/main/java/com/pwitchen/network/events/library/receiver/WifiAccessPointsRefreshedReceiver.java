package com.pwitchen.network.events.library.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.pwitchen.network.events.library.event.WifiAccessPointsSignalStrengthChangedEvent;
import com.squareup.otto.Bus;

public class WifiAccessPointsRefreshedReceiver extends BroadcastReceiver {
    private Bus eventBus;

    public WifiAccessPointsRefreshedReceiver(Bus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        eventBus.post(new WifiAccessPointsSignalStrengthChangedEvent(context));
    }
}
