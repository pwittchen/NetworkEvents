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

import android.test.AndroidTestCase;

import com.github.pwittchen.networkevents.library.ConnectivityStatus;
import com.github.pwittchen.networkevents.library.NetworkState;
import com.github.pwittchen.networkevents.library.TestUtils;
import com.github.pwittchen.networkevents.library.event.ConnectivityChanged;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.util.ArrayList;
import java.util.List;

public class InternetConnectionChangeReceiverTest extends AndroidTestCase {

    private InternetConnectionChangeReceiver receiver;
    private Bus bus;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.bus = new Bus(ThreadEnforcer.ANY);
        this.receiver = new InternetConnectionChangeReceiver(bus);
    }

    public void testShouldReceiveAnEventWhenDeviceIsConnectedToWifiWithInternetAccess() throws InterruptedException {
        // given
        boolean connectedToInternet = true;
        final List<ConnectivityChanged> connectivityChangeEvents = new ArrayList<>();
        NetworkState.status = ConnectivityStatus.UNKNOWN;
        Object eventCatcher = TestUtils.getConnectivityEventCatcher(connectivityChangeEvents);
        bus.register(eventCatcher);

        // when
        receiver.onPostReceive(connectedToInternet);
        Thread.sleep(2000); // wait a while for async operation

        // then
        assertFalse(connectivityChangeEvents.isEmpty());
        ConnectivityStatus currentConnectivityStatus = connectivityChangeEvents.get(0).getConnectivityStatus();
        assertTrue(currentConnectivityStatus == ConnectivityStatus.WIFI_CONNECTED_HAS_INTERNET);
        bus.unregister(eventCatcher);
    }

    public void testShouldReceiveAnEventWhenDeviceIsConnectedToWifiWithNoInternetAccess() throws InterruptedException {
        // given
        boolean connectedToInternet = false;
        final List<ConnectivityChanged> connectivityChangeEvents = new ArrayList<>();
        NetworkState.status = ConnectivityStatus.UNKNOWN;
        Object eventCatcher = TestUtils.getConnectivityEventCatcher(connectivityChangeEvents);
        bus.register(eventCatcher);

        // when
        receiver.onPostReceive(connectedToInternet);
        Thread.sleep(2000); // wait a while for async operation

        // then
        assertFalse(connectivityChangeEvents.isEmpty());
        ConnectivityStatus currentConnectivityStatus = connectivityChangeEvents.get(0).getConnectivityStatus();
        assertTrue(currentConnectivityStatus == ConnectivityStatus.WIFI_CONNECTED_HAS_NO_INTERNET);
        bus.unregister(eventCatcher);
    }
}