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
package com.github.pwittchen.networkevents;

public class NetworkEventsConfig {
    /**
     * Setting default values of the configuration
     */
    public final static String TAG = "NetworkEvents";
    private static String remoteHostForPing = "http://www.google.com/";
    private static String intentNameForInternetConnectionChange = "network.events.intent.action.INTERNET_CONNECTION_STATE_CHANGED";
    private static String intentNameExtraForInternetConnectionChange = "network.events.intent.extra.CONNECTED_TO_INTERNET";

    public static String getRemoteHostForPing() {
        return remoteHostForPing;
    }

    public static void setRemoteHostForPing(String remoteHostForPing) {
        NetworkEventsConfig.remoteHostForPing = remoteHostForPing;
    }

    public static String getIntentNameForInternetConnectionChange() {
        return intentNameForInternetConnectionChange;
    }

    public static void setIntentNameForInternetConnectionChange(String intentNameForInternetConnectionChange) {
        NetworkEventsConfig.intentNameForInternetConnectionChange = intentNameForInternetConnectionChange;
    }

    public static String getIntentNameExtraForInternetConnectionChange() {
        return intentNameExtraForInternetConnectionChange;
    }

    public static void setIntentNameExtraForInternetConnectionChange(String intentNameExtraForInternetConnectionChange) {
        NetworkEventsConfig.intentNameExtraForInternetConnectionChange = intentNameExtraForInternetConnectionChange;
    }
}