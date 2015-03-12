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

import com.github.pwittchen.networkevents.library.event.WifiSignalStrengthChanged;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.otto.ThreadEnforcer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class WifiSignalStrengthChangeReceiverTest {

    private WifiSignalStrengthChangeReceiver receiver;
    private Bus bus;

    @Before
    public void setUp() throws Exception {
        this.bus = new Bus(ThreadEnforcer.ANY);
        this.receiver = new WifiSignalStrengthChangeReceiver(bus);
    }

    @Test
    public void testShouldReceiveAnEventWhenWifiSignalStrengthChanged() throws InterruptedException {
        // given
        final List<WifiSignalStrengthChanged> connectivityChangeEvents = new ArrayList<>();
        Object eventCatcher = getWifiSignalStrengthChangedEventCatcher(connectivityChangeEvents);
        bus.register(eventCatcher);

        // when
        receiver.onPostReceive();
        Thread.sleep(2000); // wait a while for async operation

        // then
        assertThat(connectivityChangeEvents).isNotEmpty();
        bus.unregister(eventCatcher);
    }

    private static Object getWifiSignalStrengthChangedEventCatcher(final List<WifiSignalStrengthChanged> connectivityChangeEvents) {
        return new Object() {
            @SuppressWarnings("unused")
            @Subscribe
            public void onWifiSignalStrengthChanged(WifiSignalStrengthChanged event) {
                connectivityChangeEvents.add(event);
            }
        };
    }
}