package com.github.pwittchen.networkevents.library;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.github.pwittchen.networkevents.library.utils.OttoBusWrapper;
import com.squareup.otto.Bus;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class) public class NetworkEventsTest {

  @Test(expected = IllegalArgumentException.class) public void testContextShouldNotBeNull() {
    // given
    Context nullContext = null;
    BusWrapper busWrapper = new OttoBusWrapper(new Bus());

    // when
    new NetworkEvents(nullContext, busWrapper);

    // then throw an exception
  }

  @Test(expected = IllegalArgumentException.class) public void testBusShouldNotBeNull() {
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
}
