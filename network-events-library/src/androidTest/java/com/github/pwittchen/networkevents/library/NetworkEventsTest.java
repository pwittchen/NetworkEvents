package com.github.pwittchen.networkevents.library;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.squareup.otto.Bus;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class NetworkEventsTest {

    @Test(expected = IllegalArgumentException.class)
    public void testContextShouldNotBeNull() {
        // given
        Context nullContext = null;
        Bus bus = new Bus();

        // when
        new NetworkEvents(nullContext, bus);

        // then throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBusShouldNotBeNull() {
        // given
        Context context = InstrumentationRegistry.getContext();
        Bus nullBus = null;

        // when
        new NetworkEvents(context, nullBus);

        // then throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldNotRegisterWhenContextAndBusAreNull() {
        // given
        NetworkEvents networkEvents = new NetworkEvents(null, null);

        // when
        networkEvents.register();

        // then throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldNotSetInvalidUrl() {
        // given
        String invalidUrl = "htptp://ghi.jkl.mn";

        // when
        new NetworkEvents(InstrumentationRegistry.getContext(), new Bus())
                .withPingUrl(invalidUrl);

        // then throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPingUrlShouldNotBeNull() {
        // given
        String nullUrl = null;

        // when
        new NetworkEvents(InstrumentationRegistry.getContext(), new Bus())
                .withPingUrl(nullUrl);

        // then throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPingTimeoutShouldNotBeLowerEqualToZero() {
        // given
        int zeroTimeout = 0;

        // when
        new NetworkEvents(InstrumentationRegistry.getContext(), new Bus())
                .withPingTimeout(zeroTimeout);

        // then throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPingTimeoutShouldNotBeLowerThanZero() {
        // given
        int negativeTimeout = -5;

        // when
        new NetworkEvents(InstrumentationRegistry.getContext(), new Bus())
                .withPingTimeout(negativeTimeout);

        // then throw an exception
    }

    @Test(expected = IllegalStateException.class)
    public void testShouldNotSetPingUrlWhenContextAndBusAreSetAndPingIsDisabled() {
        // given
        String givenUrl = "http://www.android.com";

        // when
        new NetworkEvents(InstrumentationRegistry.getContext(), new Bus())
                .withoutPing()
                .withPingUrl(givenUrl);

        // then throw an exception
    }

    @Test(expected = IllegalStateException.class)
    public void testShouldNotSetPingUrlWhenPingIsDisabled() {
        // given
        String givenUrl = "http://www.android.com";

        // when
        new NetworkEvents(InstrumentationRegistry.getContext(), new Bus())
                .withoutPing()
                .withPingUrl(givenUrl);

        // then throw an exception
    }

    @Test(expected = IllegalStateException.class)
    public void testShouldNotSetPingTimeoutWhenContextAndBusAreSetAndPingIsDisabled() {
        // given
        int givenTimeout = 40 * 1000;

        // when
        new NetworkEvents(InstrumentationRegistry.getContext(), new Bus())
                .withoutPing()
                .withPingTimeout(givenTimeout);

        // then throw an exception
    }

    @Test(expected = IllegalStateException.class)
    public void testShouldNotSetPingTimeoutWhenPingIsDisabled() {
        // given
        int givenTimeout = 40 * 1000;

        // when
        new NetworkEvents(InstrumentationRegistry.getContext(), new Bus())
                .withoutPing()
                .withPingTimeout(givenTimeout);

        // then throw an exception
    }
}
