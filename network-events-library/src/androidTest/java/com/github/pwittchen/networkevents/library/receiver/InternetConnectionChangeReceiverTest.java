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
import com.github.pwittchen.networkevents.library.bus.BusWrapper;
import com.github.pwittchen.networkevents.library.bus.OttoBusWrapper;
import com.github.pwittchen.networkevents.library.logger.Logger;
import com.github.pwittchen.networkevents.library.NetworkState;
import com.github.pwittchen.networkevents.library.TestUtils;
import com.github.pwittchen.networkevents.library.event.ConnectivityChanged;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class InternetConnectionChangeReceiverTest {

    private InternetConnectionChangeReceiver receiver;
    private BusWrapper busWrapper;
    private List<ConnectivityChanged> connectivityChangeEvents;

    @Before
    public void setUp() throws Exception {
        this.busWrapper = new OttoBusWrapper(new Bus(ThreadEnforcer.ANY));
        this.receiver = new InternetConnectionChangeReceiver(busWrapper, Mockito.mock(Logger.class));
        this.connectivityChangeEvents = new ArrayList<>();
    }

    @After
    public void tearDown() throws Exception {
        connectivityChangeEvents.clear();
    }

    @Test
    public void testShouldReceiveAnEventWhenDeviceIsConnectedToWifiWithInternetAccess() throws InterruptedException {
        // given
        connectivityChangeEvents.clear();
        boolean connectedToInternet = true;
        NetworkState.status = ConnectivityStatus.UNKNOWN;
        Object eventCatcher = TestUtils.getConnectivityEventCatcher(connectivityChangeEvents);
        busWrapper.register(eventCatcher);

        // when
        onPostReceiveAndSleep(connectedToInternet);

        // then
        ConnectivityStatus expectedConnectivityStatus = ConnectivityStatus.WIFI_CONNECTED_HAS_INTERNET;
        assertExpectedStatusEqualsCurrentAndUnregisterBus(expectedConnectivityStatus, eventCatcher);
    }

    @Test
    public void testShouldReceiveAnEventWhenDeviceIsConnectedToWifiWithNoInternetAccess() throws InterruptedException {
        // given
        connectivityChangeEvents.clear();
        boolean connectedToInternet = false;
        NetworkState.status = ConnectivityStatus.UNKNOWN;
        Object eventCatcher = TestUtils.getConnectivityEventCatcher(connectivityChangeEvents);
        busWrapper.register(eventCatcher);

        // when
        onPostReceiveAndSleep(connectedToInternet);

        // then
        ConnectivityStatus expectedConnectivityStatus = ConnectivityStatus.WIFI_CONNECTED_HAS_NO_INTERNET;
        assertExpectedStatusEqualsCurrentAndUnregisterBus(expectedConnectivityStatus, eventCatcher);
    }

    private void onPostReceiveAndSleep(boolean connectedToInternet) throws InterruptedException {
        receiver.onPostReceive(connectedToInternet);
        Thread.sleep(2000); // wait a while for async operation
    }

    private void assertExpectedStatusEqualsCurrentAndUnregisterBus(ConnectivityStatus expectedConnectivityStatus, Object eventCatcher) {
        ConnectivityStatus currentConnectivityStatus = connectivityChangeEvents.get(0).getConnectivityStatus();
        assertThat(expectedConnectivityStatus).isEqualTo(currentConnectivityStatus);
        busWrapper.unregister(eventCatcher);
    }
}