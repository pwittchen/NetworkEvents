/*
 * Copyright (C) 2016 Piotr Wittchen
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class NetworkHelper {

  private NetworkHelper() {
  }

  /**
   * Helper method, which checks if device is connected to WiFi or mobile network.
   *
   * @param context Activity or application context
   * @return boolean true if is connected to mobile or WiFi network.
   */
  public static boolean isConnectedToWiFiOrMobileNetwork(Context context) {
    final String service = Context.CONNECTIVITY_SERVICE;
    final ConnectivityManager manager = (ConnectivityManager) context.getSystemService(service);
    final NetworkInfo networkInfo = manager.getActiveNetworkInfo();

    if (networkInfo == null) {
      return false;
    }

    final boolean isWifiNetwork = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    final boolean isMobileNetwork = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;

    if (isWifiNetwork || isMobileNetwork) {
      return true;
    }

    return false;
  }
}
