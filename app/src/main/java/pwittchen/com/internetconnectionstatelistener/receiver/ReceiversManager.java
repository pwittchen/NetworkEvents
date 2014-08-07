package pwittchen.com.internetconnectionstatelistener.receiver;

import android.content.Context;
import android.content.IntentFilter;

import pwittchen.com.internetconnectionstatelistener.config.ICSLConfig;

public class ReceiversManager {
    private Context context;
    private NetworkChangeReceiver networkChangeReceiver;
    private InternetConnectionChangeReceiver internetConnectionChangeReceiver;

    public ReceiversManager(Context context) {
        this.context = context;
    }

    public void registerReceivers() {
        registerNetworkChangeReceiver();
        registerInternetConnectionChangeReceiver();
    }

    public void unregisterReceivers() {
        context.unregisterReceiver(networkChangeReceiver);
        context.unregisterReceiver(internetConnectionChangeReceiver);
    }

    private void registerNetworkChangeReceiver() {
        networkChangeReceiver = new NetworkChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        context.registerReceiver(networkChangeReceiver, filter);
    }

    private void registerInternetConnectionChangeReceiver() {
        internetConnectionChangeReceiver = new InternetConnectionChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ICSLConfig.getIntentName());
        context.registerReceiver(internetConnectionChangeReceiver, filter);
    }
}
