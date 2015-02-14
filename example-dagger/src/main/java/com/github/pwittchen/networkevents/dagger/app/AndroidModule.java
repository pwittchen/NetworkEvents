package com.github.pwittchen.networkevents.dagger.app;

import android.app.Application;
import android.content.Context;

import com.github.pwittchen.networkevents.library.NetworkEvents;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(injects = MainActivity.class, library = true)
public class AndroidModule {
    private final Application application;
    private final Bus bus;

    public AndroidModule(BaseApplication application) {
        this.application = application;
        this.bus = new Bus();
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    public Bus provideBus() {
        return bus;
    }

    @Provides
    @Singleton
    NetworkEvents provideNetworkEvents() {
        return new NetworkEvents(application, bus);
    }
}
