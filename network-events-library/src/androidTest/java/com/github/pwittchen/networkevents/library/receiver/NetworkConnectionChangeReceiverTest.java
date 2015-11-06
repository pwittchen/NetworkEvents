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
import android.support.test.runner.AndroidJUnit4;

import com.github.pwittchen.networkevents.library.BusWrapper;
import com.github.pwittchen.networkevents.library.ConnectivityStatus;
import com.github.pwittchen.networkevents.library.NetworkState;
import com.github.pwittchen.networkevents.library.event.ConnectivityChanged;
import com.github.pwittchen.networkevents.library.internet.OnlineChecker;
import com.github.pwittchen.networkevents.library.logger.Logger;
import com.github.pwittchen.networkevents.library.utils.OttoBusWrapper;
import com.github.pwittchen.networkevents.library.utils.TestUtils;
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

@RunWith(AndroidJUnit4.class) public class NetworkConnectionChangeReceiverTest {

  private NetworkConnectionChangeReceiver receiver;
  private BusWrapper busWrapper;
  private List<ConnectivityChanged> connectivityChangeEvents;

  @Before public void setUp() throws Exception {
    this.busWrapper = new OttoBusWrapper(new Bus(ThreadEnforcer.ANY));
    Logger logger = Mockito.mock(Logger.class);
    Context context = Mockito.mock(Context.class);
    OnlineChecker onlineChecker = Mockito.mock(OnlineChecker.class);
    this.receiver = new NetworkConnectionChangeReceiver(busWrapper, logger, context, onlineChecker);
    this.connectivityChangeEvents = new ArrayList<>();
  }

  @After public void tearDown() throws Exception {
    connectivityChangeEvents.clear();
  }

  @Test public void testReceiverShouldReceiveAnEventOnConnectivityChange() throws Exception {
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

  @Test public void testReceiverShouldReceiveAnEventWhenGoingOffline() throws Exception {
    // given
    connectivityChangeEvents.clear();
    Object eventCatcher = TestUtils.getConnectivityEventCatcher(connectivityChangeEvents);
    setUnknownNetworkStatusAndRegisterBus(eventCatcher);
    ConnectivityStatus expectedConnectivityStatus = ConnectivityStatus.OFFLINE;

    // when
    onPostReceiveAndSleep(expectedConnectivityStatus);

    // then
    assertExpectedStatusEqualsCurrent(expectedConnectivityStatus, eventCatcher);
  }

  @Test public void testReceiverShouldReceiveAnEventWhenGoingOnlineViaWifi() throws Exception {
    // given
    connectivityChangeEvents.clear();
    Object eventCatcher = TestUtils.getConnectivityEventCatcher(connectivityChangeEvents);
    setUnknownNetworkStatusAndRegisterBus(eventCatcher);
    ConnectivityStatus expectedConnectivityStatus = ConnectivityStatus.WIFI_CONNECTED;

    // when
    onPostReceiveAndSleep(expectedConnectivityStatus);

    // then
    assertExpectedStatusEqualsCurrent(expectedConnectivityStatus, eventCatcher);
  }

  @Test public void testReceiverShouldReceiveAnEventWhenGoingOnlineViaMobile() throws Exception {
    // given
    connectivityChangeEvents.clear();
    Object eventCatcher = TestUtils.getConnectivityEventCatcher(connectivityChangeEvents);
    setUnknownNetworkStatusAndRegisterBus(eventCatcher);
    ConnectivityStatus expectedConnectivityStatus = ConnectivityStatus.MOBILE_CONNECTED;

    // when
    onPostReceiveAndSleep(expectedConnectivityStatus);

    // then
    assertExpectedStatusEqualsCurrent(expectedConnectivityStatus, eventCatcher);
  }

  private void onPostReceiveAndSleep(ConnectivityStatus status) throws InterruptedException {
    receiver.onPostReceive(status);
    Thread.sleep(2000); // wait a while for async operation
  }

  private void setUnknownNetworkStatusAndRegisterBus(Object eventCatcher) {
    NetworkState.status = ConnectivityStatus.UNKNOWN;
    busWrapper.register(eventCatcher);
  }

  private void assertExpectedStatusEqualsCurrent(ConnectivityStatus status, Object eventCatcher) {
    ConnectivityStatus currentStatus = connectivityChangeEvents.get(0).getConnectivityStatus();
    assertThat(status).isEqualTo(currentStatus);
    busWrapper.unregister(eventCatcher);
  }
}