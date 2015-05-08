package com.github.pwittchen.networkevents.library;

import android.content.Context;

/**
 * Wrapper for Ping class is created,
 * because we need separate instance of Ping class for every thread,
 * to execute AsyncTasks properly.
 */
public final class PingWrapper implements Task {

    private Context context;
    private String url;

    public PingWrapper(Context context) {
        this.context = context;
        this.url = NetworkEventsConfig.URL;
    }

    public PingWrapper(Context context, String pingUrl) {
        this.context = context;
        this.url = pingUrl;
    }

    @Override
    public void execute() {
        new Ping(context, url).execute();
    }
}
