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
package com.github.pwittchen.networkevents.library.internet;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import com.github.pwittchen.networkevents.library.receiver.InternetConnectionChangeReceiver;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public final class OnlineCheckerImpl implements OnlineChecker {
  private static final String DEFAULT_PING_HOST = "www.google.com";
  private static final int DEFAULT_PING_PORT = 80;
  private static final int DEFAULT_PING_TIMEOUT_IN_MS = 2000;

  private final Context context;
  private String pingHost;
  private int pingPort;
  private int pingTimeout;

  public OnlineCheckerImpl(Context context) {
    this.context = context;
    this.pingHost = DEFAULT_PING_HOST;
    this.pingPort = DEFAULT_PING_PORT;
    this.pingTimeout = DEFAULT_PING_TIMEOUT_IN_MS;
  }

  @Override public void check() {
    new AsyncTask<Void, Void, Void>() {
      @Override protected Void doInBackground(Void... params) {
        boolean isOnline = false;
        try {
          Socket socket = new Socket();
          socket.connect(new InetSocketAddress(pingHost, pingPort), pingTimeout);
          isOnline = socket.isConnected();
        } catch (IOException e) {
          isOnline = false;
        } finally {
          sendBroadcast(isOnline);
        }
        return null;
      }
    }.execute();
  }

  @Override public void setPingParameters(String host, int port, int timeoutInMs) {
    this.pingHost = host;
    this.pingPort = port;
    this.pingTimeout = timeoutInMs;
  }

  private void sendBroadcast(boolean isOnline) {
    Intent intent = new Intent(InternetConnectionChangeReceiver.INTENT);
    intent.putExtra(InternetConnectionChangeReceiver.INTENT_EXTRA, isOnline);
    context.sendBroadcast(intent);
  }
}
