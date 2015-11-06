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
package com.github.pwittchen.networkevents.library.receiver;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.github.pwittchen.networkevents.library.BusWrapper;
import com.github.pwittchen.networkevents.library.ConnectivityStatus;
import com.github.pwittchen.networkevents.library.internet.OnlineChecker;
import com.github.pwittchen.networkevents.library.logger.Logger;

public final class NetworkConnectionChangeReceiver extends BaseBroadcastReceiver {
  private final OnlineChecker onlineChecker;
  private boolean internetCheckEnabled = false;

  public NetworkConnectionChangeReceiver(BusWrapper busWrapper, Logger logger, Context context,
      OnlineChecker onlineChecker) {
    super(busWrapper, logger, context);
    this.onlineChecker = onlineChecker;
  }

  public void enableInternetCheck() {
    this.internetCheckEnabled = true;
  }

  @Override public void onReceive(final Context context, Intent intent) {
    onPostReceive(getConnectivityStatus(context));
  }

  public void onPostReceive(final ConnectivityStatus connectivityStatus) {
    if (statusNotChanged(connectivityStatus)) {
      return;
    }

    boolean isConnectedToWifi = connectivityStatus == ConnectivityStatus.WIFI_CONNECTED;

    if (internetCheckEnabled && isConnectedToWifi) {
      onlineChecker.check();
    } else {
      postConnectivityChanged(connectivityStatus);
    }
  }

  private ConnectivityStatus getConnectivityStatus(Context context) {
    ConnectivityManager connectivityManager =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

    if (networkInfo != null) {
      if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
        return ConnectivityStatus.WIFI_CONNECTED;
      } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
        return ConnectivityStatus.MOBILE_CONNECTED;
      }
    }

    return ConnectivityStatus.OFFLINE;
  }
}