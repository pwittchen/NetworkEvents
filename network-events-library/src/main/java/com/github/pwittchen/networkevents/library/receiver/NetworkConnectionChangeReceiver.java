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
import com.github.pwittchen.networkevents.library.NetworkHelper;
import com.github.pwittchen.networkevents.library.Ping;
import com.github.pwittchen.networkevents.library.Task;
import com.squareup.otto.Bus;

public final class NetworkConnectionChangeReceiver extends BaseBroadcastReceiver {

    private Task taskExecutedAfterConnectingToWiFi;

    public NetworkConnectionChangeReceiver(Bus bus) {
        super(bus);
    }

    public NetworkConnectionChangeReceiver(Bus bus, Task taskExecutedAfterConnectingToWiFi) {
        this(bus);
        this.taskExecutedAfterConnectingToWiFi = taskExecutedAfterConnectingToWiFi;
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        onPostReceive(context, NetworkHelper.getConnectivityStatus(context));
    }

    public void onPostReceive(final Context context, final ConnectivityStatus connectivityStatus) {
        if (statusNotChanged(connectivityStatus)) {
            return;
        }

        postConnectivityChanged(connectivityStatus, new Runnable() {
            @Override
            public void run() {
                createThreadSafeTaskInstance(context);

                if (connectivityStatus == ConnectivityStatus.WIFI_CONNECTED) {
                    taskExecutedAfterConnectingToWiFi.execute();
                }

                destroyTaskInstanceInCurrentThread();
            }
        });
    }

    private void createThreadSafeTaskInstance(Context context) {
        if (taskExecutedAfterConnectingToWiFi != null) {
            // task is already set (via constructor, probably for unit tests)
            return;
        }
        // we need to create separate instance for each thread
        taskExecutedAfterConnectingToWiFi = new Ping(context);
        // Ping class is responsible for checking Internet connection
    }

    private void destroyTaskInstanceInCurrentThread() {
        taskExecutedAfterConnectingToWiFi = null;
        // now, new instances can be created in next threads
    }
}