package com.pwittchen.icsl.library.service;

import android.app.Service;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.IBinder;

import com.pwittchen.icsl.library.config.ICSLConfig;
import com.pwittchen.icsl.library.helper.NetworkHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WifiAccessPointScanService extends Service {

    private Timer timer = new Timer(true);
    private List<ScanResult> accessPointList = new ArrayList<ScanResult>();
    private final static int delay = 0;

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                accessPointList = NetworkHelper.getAccessPointList(getApplicationContext());
                if (!accessPointList.isEmpty()) {
                    sendWifiScanFinishedBroadCast();
                }
            }
        }, delay, ICSLConfig.getWifiScanUpdateIntervalInMilliseconds());

        return super.onStartCommand(intent, flags, startId);
    }

    private void sendWifiScanFinishedBroadCast() {
        Intent intent = new Intent(ICSLConfig.getIntentNameForWifiAccessPointsScanFinished());
        getApplicationContext().sendBroadcast(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
