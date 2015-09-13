/*
 * Copyright (C) 2015 Piotr Wittchen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.pwittchen.networkevents.library.receiver;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.github.pwittchen.networkevents.library.BusWrapper;
import com.github.pwittchen.networkevents.library.ConnectivityStatus;
import com.github.pwittchen.networkevents.library.logger.Logger;

public final class InternetConnectionChangeReceiver extends BaseBroadcastReceiver {

    public final static String INTENT = "networkevents.intent.action.INTERNET_CONNECTION_STATE_CHANGED";
    public final static String INTENT_EXTRA = "networkevents.intent.extra.CONNECTED_TO_INTERNET";

    public InternetConnectionChangeReceiver(BusWrapper busWrapper, Logger logger, Context context) {
        super(busWrapper, logger, context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(INTENT)) {
            boolean connectedToInternet = intent.getBooleanExtra(INTENT_EXTRA, false);
            onPostReceive(connectedToInternet, context);
        }
    }

    public void onPostReceive(boolean connectedToInternet, Context context) {
        ConnectivityStatus connectivityStatus
                = (connectedToInternet)
                ? ConnectivityStatus.WIFI_CONNECTED_HAS_INTERNET
                : ConnectivityStatus.WIFI_CONNECTED_HAS_NO_INTERNET;

        if (statusNotChanged(connectivityStatus)) {
            return;
        }

        // we are checking if device is connected to WiFi again,
        // because connectivityStatus may change in a short period of time
        // after receiving it

        if (context != null && !isConnectedToWifi(context)) {
            return;
        }

        postConnectivityChanged(connectivityStatus);
    }

    private boolean isConnectedToWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            return networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
        }

        return false;
    }
}
