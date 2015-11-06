package com.github.pwittchen.networkevents.library.utils;

import com.github.pwittchen.networkevents.library.BusWrapper;
import com.squareup.otto.Bus;

public final class OttoBusWrapper implements BusWrapper {
  private Bus bus;

  public OttoBusWrapper(Bus bus) {
    this.bus = bus;
  }

  @Override public void register(Object object) {
    bus.register(object);
  }

  @Override public void unregister(Object object) {
    bus.unregister(object);
  }

  @Override public void post(Object event) {
    bus.post(event);
  }
}
