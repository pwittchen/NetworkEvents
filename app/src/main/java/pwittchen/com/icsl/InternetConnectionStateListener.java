package pwittchen.com.icsl;

import android.content.Context;
import android.content.IntentFilter;

import com.squareup.otto.Bus;

import pwittchen.com.icsl.config.ICSLConfig;
import pwittchen.com.icsl.receiver.InternetConnectionChangeReceiver;
import pwittchen.com.icsl.receiver.NetworkChangeReceiver;

public class InternetConnectionStateListener {
    private Context context;
    private Bus eventBus;
    private NetworkChangeReceiver networkChangeReceiver;
    private InternetConnectionChangeReceiver internetConnectionChangeReceiver;

    public InternetConnectionStateListener(Context context, Bus eventBus) {
        this.context = context;
        this.eventBus = eventBus;
    }

    public void register() {
        registerNetworkChangeReceiver();
        registerInternetConnectionChangeReceiver();
    }

    public void unregister() {
        context.unregisterReceiver(networkChangeReceiver);
        context.unregisterReceiver(internetConnectionChangeReceiver);
    }

    private void registerNetworkChangeReceiver() {
        networkChangeReceiver = new NetworkChangeReceiver(eventBus);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        context.registerReceiver(networkChangeReceiver, filter);
    }

    private void registerInternetConnectionChangeReceiver() {
        internetConnectionChangeReceiver = new InternetConnectionChangeReceiver(eventBus);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ICSLConfig.getIntentName());
        context.registerReceiver(internetConnectionChangeReceiver, filter);
    }
}
