NetworkEvents
===============================

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-NetworkEvents-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1392)

Android library listening network events.

## Overview

It is able to detect [`ConnectivityStatus`](https://github.com/pwittchen/NetworkEvents/blob/master/network-events-library/src/main/java/com/github/pwittchen/network/events/library/receiver/ConnectivityStatus.java) when it changes:
- `WIFI_CONNECTED("connected to WiFi")`
- `WIFI_CONNECTED_HAS_INTERNET("connected to WiFi (Internet available)")`
- `WIFI_CONNECTED_HAS_NO_INTERNET("connected to WiFi (Internet not available)")`
- `MOBILE_CONNECTED("connected to mobile network")`
- `OFFLINE("offline")`

In addition it is able to detect situation when strength of the Wifi signal was changed with `WifiSignalStrengthChanged` event.

## Usage

Add permissions to `AndroidManifest.xml` file.

```xml
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
<uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.INTERNET" />
```

In your activity add `Bus` field from [Otto Event Bus](http://square.github.io/otto/) library and `NetworkEvents` field.

```java
private Bus bus;
private NetworkEvents networkEvents;
```

Initialize objects in `onCreate(Bundle savedInstanceState)` method.

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    bus = new Bus();
    networkEvents = new NetworkEvents(this, bus);
}
```

Register `Bus` and `NetworkEvents` in `onResume()` method and unregister them in `onPause()` method.

```java
@Override
protected void onResume() {
    super.onResume();
    bus.register(this);
    networkEvents.register();
}

@Override
protected void onPause() {
    super.onPause();
    bus.unregister(this);
    networkEvents.unregister();
}
```

Subscribe for the events

```java
@Subscribe
public void onConnectivityChanged(ConnectivityChanged event) {
    // get connectivity status from event.getConnectivityStatus()
    // and do whatever you want
}

@Subscribe
public void onWifiSignalStrengthChanged(WifiSignalStrengthChanged event) {
    // do whatever you want - e.g. read fresh list of access points
}
```

## Example

Look at [MainActivity](https://github.com/pwittchen/NetworkEvents/blob/master/example/src/main/java/com/github/pwittchen/network/events/app/MainActivity.java) in [exemplary application](https://github.com/pwittchen/NetworkEvents/tree/master/example) to see how this library works.

## Known issues

**issue #1**

In [NetworkConnectionChangeReceiver](https://github.com/pwittchen/NetworkEvents/blob/master/network-events-library/src/main/java/com/github/pwittchen/network/events/library/receiver/NetworkConnectionChangeReceiver.java) event informing about **going off-line** is **pushed twice to the event bus**. This behaviour may vary on different devices. It was observed on Nexus 5 and may be device specific problem.

**issue #2**

In [WifiSignalStrengthChanged](https://github.com/pwittchen/NetworkEvents/blob/master/network-events-library/src/main/java/com/github/pwittchen/network/events/library/event/WifiSignalStrengthChanged.java) we have to start request for Wifi scanning in order to receive an event as soon as possible when user is moving with the mobile device. In theory, it may cause greater battery drain, but in practical applications I didn't observed noticeable problems with that. It may be investigated and improved in the future.

License
-------

    Copyright 2015 Piotr Wittchen

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
