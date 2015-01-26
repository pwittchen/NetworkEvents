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
package com.github.pwittchen.networkevents.library.event;

import android.util.Log;

import com.github.pwittchen.networkevents.library.NetworkEventsConfig;
import com.github.pwittchen.networkevents.library.ConnectivityStatus;

/**
 * Event pushed to Otto Event Bus when ConnectivityStatus changes;
 * E.g. when WiFi is turned on or off or when mobile network is turned on or off
 * it also occurs when WiFi is on, but there is no Internet connection or when there is Internet connection
 * or when device goes off-line
 */
public final class ConnectivityChanged {
    private final ConnectivityStatus connectivityStatus;

    public ConnectivityChanged(ConnectivityStatus connectivityStatus) {
        this.connectivityStatus = connectivityStatus;
        String message = String.format("ConnectivityChanged: %s", connectivityStatus.toString());
        Log.d(NetworkEventsConfig.TAG, message);
    }

    public ConnectivityStatus getConnectivityStatus() {
        return connectivityStatus;
    }

}