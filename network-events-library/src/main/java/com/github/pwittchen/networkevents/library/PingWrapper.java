package com.github.pwittchen.networkevents.library;

import android.content.Context;

/**
 * Wrapper for Ping class is created,
 * because we need separate instance of Ping class for every thread,
 * to execute AsyncTasks properly.
 */
public final class PingWrapper implements Task {

    private Context context;

    public PingWrapper(Context context) {
        this.context = context;
    }

    @Override
    public void execute() {
        new Ping(context).execute();
    }
}
