package com.github.pwittchen.networkevents.dagger.app;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

public class BaseApplication extends Application {
    private ObjectGraph graph;

    @Override
    public void onCreate() {
        super.onCreate();
        graph = ObjectGraph.create(getModules().toArray());
    }

    protected List<Object> getModules() {
        return Arrays.asList(
                (Object) new AndroidModule(this)
        );
    }

    public void inject(Object object) {
        graph.inject(object);
    }
}
