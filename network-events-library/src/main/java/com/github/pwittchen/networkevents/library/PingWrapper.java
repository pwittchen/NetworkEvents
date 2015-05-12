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
    private int timeout;
    private boolean pingEnabled;

    public PingWrapper(Context context) {
        this.context = context;
        this.url = NetworkEventsConfig.URL;
        this.timeout = NetworkEventsConfig.TIMEOUT;
        this.pingEnabled = true;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void disablePing() {
        this.pingEnabled = false;
    }

    public boolean isPingEnabled() {
        return pingEnabled;
    }

    @Override
    public void execute() {
        if(pingEnabled) {
            new Ping(context, url, timeout).execute();
        }
    }
}
