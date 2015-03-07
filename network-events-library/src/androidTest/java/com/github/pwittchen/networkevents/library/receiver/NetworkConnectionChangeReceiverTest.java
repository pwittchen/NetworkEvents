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
import com.github.pwittchen.networkevents.library.Task;
import com.github.pwittchen.networkevents.library.TestUtils;
import com.github.pwittchen.networkevents.library.event.ConnectivityChanged;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.util.ArrayList;
import java.util.List;

public class NetworkConnectionChangeReceiverTest extends AndroidTestCase {

    private NetworkConnectionChangeReceiver receiver;
    private Bus bus;
    private List<ConnectivityChanged> connectivityChangeEvents;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.bus = new Bus(ThreadEnforcer.ANY);
        this.receiver = new NetworkConnectionChangeReceiver(bus);
        this.receiver.setTask(getTaskImplementation());
        this.connectivityChangeEvents = new ArrayList<>();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        connectivityChangeEvents.clear();
    }

    public void testReceiverShouldReceiveAnEventOnConnectivityChange() throws Exception {
        // given
        connectivityChangeEvents.clear();
        NetworkState.status = ConnectivityStatus.UNKNOWN;
        Object eventCatcher = TestUtils.getConnectivityEventCatcher(connectivityChangeEvents);
        bus.register(eventCatcher);
        ConnectivityStatus connectivityStatus = ConnectivityStatus.OFFLINE;

        // when
        onPostReceiveAndSleep(connectivityStatus);

        // then
        assertFalse(connectivityChangeEvents.isEmpty());
        bus.unregister(eventCatcher);
    }

    public void testReceiverShouldReceiveAnEventWhenGoingOffline() throws Exception {
        // given
        connectivityChangeEvents.clear();
        Object eventCatcher = TestUtils.getConnectivityEventCatcher(connectivityChangeEvents);
        setUnknownNetworkStatusAndRegisterBus(eventCatcher);
        ConnectivityStatus expectedConnectivityStatus = ConnectivityStatus.OFFLINE;

        // when
        onPostReceiveAndSleep(expectedConnectivityStatus);

        // then
        assertExpectedStatusEqualsCurrentAndUnregisterBus(expectedConnectivityStatus, eventCatcher);
    }

    public void testReceiverShouldReceiveAnEventWhenGoingOnlineViaWifi() throws Exception {
        // given
        connectivityChangeEvents.clear();
        Object eventCatcher = TestUtils.getConnectivityEventCatcher(connectivityChangeEvents);
        setUnknownNetworkStatusAndRegisterBus(eventCatcher);
        ConnectivityStatus expectedConnectivityStatus = ConnectivityStatus.WIFI_CONNECTED;

        // when
        onPostReceiveAndSleep(expectedConnectivityStatus);

        // then
        assertExpectedStatusEqualsCurrentAndUnregisterBus(expectedConnectivityStatus, eventCatcher);
    }

    public void testReceiverShouldReceiveAnEventWhenGoingOnlineViaMobile() throws Exception {
        // given
        connectivityChangeEvents.clear();
        Object eventCatcher = TestUtils.getConnectivityEventCatcher(connectivityChangeEvents);
        setUnknownNetworkStatusAndRegisterBus(eventCatcher);
        ConnectivityStatus expectedConnectivityStatus = ConnectivityStatus.MOBILE_CONNECTED;

        // when
        onPostReceiveAndSleep(expectedConnectivityStatus);

        // then
        assertExpectedStatusEqualsCurrentAndUnregisterBus(expectedConnectivityStatus, eventCatcher);
    }

    private void setUnknownNetworkStatusAndRegisterBus(Object eventCatcher) {
        NetworkState.status = ConnectivityStatus.UNKNOWN;
        bus.register(eventCatcher);
    }

    private void onPostReceiveAndSleep(ConnectivityStatus connectivityStatus) throws InterruptedException {
        receiver.onPostReceive(getContext(), connectivityStatus);
        Thread.sleep(2000); // wait a while for async operation
    }

    private void assertExpectedStatusEqualsCurrentAndUnregisterBus(ConnectivityStatus expectedConnectivityStatus, Object eventCatcher) {
        ConnectivityStatus currentConnectivityStatus = connectivityChangeEvents.get(0).getConnectivityStatus();
        assertTrue(expectedConnectivityStatus == currentConnectivityStatus);
        bus.unregister(eventCatcher);
    }

    private Task getTaskImplementation() {
        return new Task() {
            @Override
            public void execute() {
                // do nothing in this test
            }
        };
    }
}