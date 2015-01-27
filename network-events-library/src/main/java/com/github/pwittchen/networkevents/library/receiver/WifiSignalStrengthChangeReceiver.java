package com.github.pwittchen.networkevents.library.receiver;

import android.content.Context;
import android.content.Intent;

import com.github.pwittchen.networkevents.library.event.WifiSignalStrengthChanged;
import com.squareup.otto.Bus;

public final class WifiSignalStrengthChangeReceiver extends BaseBroadcastReceiver {

    public WifiSignalStrengthChangeReceiver(Bus bus) {
        super(bus);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        bus.post(new WifiSignalStrengthChanged(context));
    }
}
