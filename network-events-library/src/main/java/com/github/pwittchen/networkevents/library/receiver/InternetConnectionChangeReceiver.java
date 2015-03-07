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

import com.github.pwittchen.networkevents.library.ConnectivityStatus;
import com.github.pwittchen.networkevents.library.NetworkEventsConfig;
import com.squareup.otto.Bus;

public final class InternetConnectionChangeReceiver extends BaseBroadcastReceiver {

    public InternetConnectionChangeReceiver(Bus bus) {
        super(bus);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(NetworkEventsConfig.INTENT)) {
            boolean connectedToInternet = intent.getBooleanExtra(NetworkEventsConfig.INTENT_EXTRA, false);
            onPostReceive(connectedToInternet);
        }
    }

    public void onPostReceive(boolean connectedToInternet) {
        ConnectivityStatus connectivityStatus
                = (connectedToInternet)
                ? ConnectivityStatus.WIFI_CONNECTED_HAS_INTERNET
                : ConnectivityStatus.WIFI_CONNECTED_HAS_NO_INTERNET;

        if (statusNotChanged(connectivityStatus)) {
            return;
        }

        postConnectivityChanged(connectivityStatus);
    }
}
