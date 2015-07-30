package com.github.pwittchen.networkevents.library;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.github.pwittchen.networkevents.library.bus.BusWrapper;
import com.github.pwittchen.networkevents.library.utils.OttoBusWrapper;
import com.squareup.otto.Bus;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class NetworkEventsTest {

    @Test(expected = IllegalArgumentException.class)
    public void testContextShouldNotBeNull() {
        // given
        Context nullContext = null;
        BusWrapper busWrapper = new OttoBusWrapper(new Bus());

        // when
        new NetworkEvents(nullContext, busWrapper);

        // then throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBusShouldNotBeNull() {
        // given
        Context context = InstrumentationRegistry.getContext();
        BusWrapper nullBusWrapper = null;

        // when
        new NetworkEvents(context, nullBusWrapper);

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
        OttoBusWrapper busWrapper = new OttoBusWrapper(new Bus());

        // when
        new NetworkEvents(InstrumentationRegistry.getContext(), busWrapper)
                .withPingUrl(invalidUrl);

        // then throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPingUrlShouldNotBeNull() {
        // given
        String nullUrl = null;
        OttoBusWrapper busWrapper = new OttoBusWrapper(new Bus());

        // when
        new NetworkEvents(InstrumentationRegistry.getContext(), busWrapper)
                .withPingUrl(nullUrl);

        // then throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPingTimeoutShouldNotBeLowerEqualToZero() {
        // given
        int zeroTimeout = 0;
        OttoBusWrapper busWrapper = new OttoBusWrapper(new Bus());

        // when
        new NetworkEvents(InstrumentationRegistry.getContext(), busWrapper)
                .withPingTimeout(zeroTimeout);

        // then throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPingTimeoutShouldNotBeLowerThanZero() {
        // given
        int negativeTimeout = -5;
        OttoBusWrapper busWrapper = new OttoBusWrapper(new Bus());

        // when
        new NetworkEvents(InstrumentationRegistry.getContext(), busWrapper)
                .withPingTimeout(negativeTimeout);

        // then throw an exception
    }

    @Test(expected = IllegalStateException.class)
    public void testShouldNotSetPingUrlWhenContextAndBusAreSetAndPingIsDisabled() {
        // given
        String givenUrl = "http://www.android.com";
        OttoBusWrapper busWrapper = new OttoBusWrapper(new Bus());

        // when
        new NetworkEvents(InstrumentationRegistry.getContext(), busWrapper)
                .withoutPing()
                .withPingUrl(givenUrl);

        // then throw an exception
    }

    @Test(expected = IllegalStateException.class)
    public void testShouldNotSetPingUrlWhenPingIsDisabled() {
        // given
        String givenUrl = "http://www.android.com";
        OttoBusWrapper busWrapper = new OttoBusWrapper(new Bus());

        // when
        new NetworkEvents(InstrumentationRegistry.getContext(), busWrapper)
                .withoutPing()
                .withPingUrl(givenUrl);

        // then throw an exception
    }

    @Test(expected = IllegalStateException.class)
    public void testShouldNotSetPingTimeoutWhenContextAndBusAreSetAndPingIsDisabled() {
        // given
        int givenTimeout = 40 * 1000;
        OttoBusWrapper busWrapper = new OttoBusWrapper(new Bus());

        // when
        new NetworkEvents(InstrumentationRegistry.getContext(), busWrapper)
                .withoutPing()
                .withPingTimeout(givenTimeout);

        // then throw an exception
    }

    @Test(expected = IllegalStateException.class)
    public void testShouldNotSetPingTimeoutWhenPingIsDisabled() {
        // given
        int givenTimeout = 40 * 1000;
        OttoBusWrapper busWrapper = new OttoBusWrapper(new Bus());

        // when
        new NetworkEvents(InstrumentationRegistry.getContext(), busWrapper)
                .withoutPing()
                .withPingTimeout(givenTimeout);

        // then throw an exception
    }
}
