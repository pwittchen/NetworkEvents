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
import android.content.Intent;
import android.os.AsyncTask;

/**
 * Asynchronous Task which pings remote host in a separate thread
 * in order to check Internet connection
 */
public final class Ping extends AsyncTask<Void, Void, Boolean> {
    private final Context context;
    private String url;
    private int timeout;

    public Ping(Context context, String url, int timeout) {
        this.context = context;
        this.url = url;
        this.timeout = timeout;
    }

    @Override
    public Boolean doInBackground(Void... params) {
        return NetworkHelper.ping(url, timeout);
    }

    @Override
    public void onPostExecute(Boolean connectedToInternet) {
        super.onPostExecute(connectedToInternet);
        Intent intent = new Intent(NetworkEventsConfig.INTENT);
        intent.putExtra(NetworkEventsConfig.INTENT_EXTRA, connectedToInternet);
        context.sendBroadcast(intent);
    }
}