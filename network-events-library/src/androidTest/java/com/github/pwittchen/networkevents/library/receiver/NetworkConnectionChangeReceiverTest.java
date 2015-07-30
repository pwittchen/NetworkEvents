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

import android.support.test.runner.AndroidJUnit4;

import com.github.pwittchen.networkevents.library.ConnectivityStatus;
import com.github.pwittchen.networkevents.library.NetworkState;
import com.github.pwittchen.networkevents.library.Task;
import com.github.pwittchen.networkevents.library.TestUtils;
import com.github.pwittchen.networkevents.library.bus.BusWrapper;
import com.github.pwittchen.networkevents.library.bus.OttoBusWrapper;
import com.github.pwittchen.networkevents.library.event.ConnectivityChanged;
import com.github.pwittchen.networkevents.library.logger.Logger;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class NetworkConnectionChangeReceiverTest {

    private NetworkConnectionChangeReceiver receiver;
    private BusWrapper busWrapper;
    private List<ConnectivityChanged> connectivityChangeEvents;

    @Before
    public void setUp() throws Exception {
        this.busWrapper = new OttoBusWrapper(new Bus(ThreadEnforcer.ANY));
        this.receiver = new NetworkConnectionChangeReceiver(busWrapper, Mockito.mock(Logger.class), Mockito.mock(Task.class));
        this.connectivityChangeEvents = new ArrayList<>();
    }

    @After
    public void tearDown() throws Exception {
        connectivityChangeEvents.clear();
    }

    @Test
    public void testReceiverShouldReceiveAnEventOnConnectivityChange() throws Exception {
        // given
        connectivityChangeEvents.clear();
        NetworkState.status = ConnectivityStatus.UNKNOWN;
        Object eventCatcher = TestUtils.getConnectivityEventCatcher(connectivityChangeEvents);
        busWrapper.register(eventCatcher);
        ConnectivityStatus connectivityStatus = ConnectivityStatus.OFFLINE;

        // when
        onPostReceiveAndSleep(connectivityStatus);

        // then
        assertThat(connectivityChangeEvents).isNotEmpty();
        busWrapper.unregister(eventCatcher);
    }

    @Test
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

    @Test
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

    @Test
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

    private void onPostReceiveAndSleep(ConnectivityStatus connectivityStatus) throws InterruptedException {
        receiver.onPostReceive(connectivityStatus);
        Thread.sleep(2000); // wait a while for async operation
    }

    private void setUnknownNetworkStatusAndRegisterBus(Object eventCatcher) {
        NetworkState.status = ConnectivityStatus.UNKNOWN;
        busWrapper.register(eventCatcher);
    }

    private void assertExpectedStatusEqualsCurrentAndUnregisterBus(ConnectivityStatus expectedConnectivityStatus, Object eventCatcher) {
        ConnectivityStatus currentConnectivityStatus = connectivityChangeEvents.get(0).getConnectivityStatus();
        assertThat(expectedConnectivityStatus).isEqualTo(currentConnectivityStatus);
        busWrapper.unregister(eventCatcher);
    }
}