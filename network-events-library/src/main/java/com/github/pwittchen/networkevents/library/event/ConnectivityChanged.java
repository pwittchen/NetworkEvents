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
package com.github.pwittchen.networkevents.library.event;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.github.pwittchen.networkevents.library.ConnectivityStatus;
import com.github.pwittchen.networkevents.library.MobileNetworkType;
import com.github.pwittchen.networkevents.library.logger.Logger;

/**
 * Event pushed to Otto Event Bus when ConnectivityStatus changes;
 * E.g. when WiFi is turned on or off or when mobile network is turned on or off
 * it also occurs when WiFi is on, but there is no Internet connection or when there is Internet
 * connection
 * or when device goes off-line
 */
public final class ConnectivityChanged {
  private static final String MESSAGE_FORMAT = "ConnectivityChanged: %s";
  private final ConnectivityStatus connectivityStatus;
  private final Context context;

  public ConnectivityChanged(ConnectivityStatus connectivityStatus, Logger logger,
      Context context) {
    this.connectivityStatus = connectivityStatus;
    this.context = context;
    String message = String.format(MESSAGE_FORMAT, connectivityStatus.toString());
    logger.log(message);
  }

  public ConnectivityStatus getConnectivityStatus() {
    return connectivityStatus;
  }

  public MobileNetworkType getMobileNetworkType() {

    if (connectivityStatus != ConnectivityStatus.MOBILE_CONNECTED) {
      return MobileNetworkType.UNKNOWN;
    }

    TelephonyManager telephonyManager =
        (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

    switch (telephonyManager.getNetworkType()) {
      case (TelephonyManager.NETWORK_TYPE_LTE):
        return MobileNetworkType.LTE;
      case (TelephonyManager.NETWORK_TYPE_HSPAP):
        return MobileNetworkType.HSPAP;
      case (TelephonyManager.NETWORK_TYPE_EDGE):
        return MobileNetworkType.EDGE;
      case (TelephonyManager.NETWORK_TYPE_GPRS):
        return MobileNetworkType.GPRS;
      default:
        return MobileNetworkType.UNKNOWN;
    }
  }
}