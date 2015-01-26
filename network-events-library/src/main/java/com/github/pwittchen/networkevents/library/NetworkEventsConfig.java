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

public final class NetworkEventsConfig {
    public final static String TAG = "NetworkEvents";
    public final static String HOST = "http://www.google.com/";
    public final static String INTENT = "networkevents.intent.action.INTERNET_CONNECTION_STATE_CHANGED";
    public final static String INTENT_EXTRA = "networkevents.intent.extra.CONNECTED_TO_INTERNET";
}