NetworkEvents
===============================

Android library listening network events.

## Usage

In your activity add Bus field from Otto Event Bus library and NetworkEvents field.

```java
private Bus bus;
private NetworkEvents networkEvents;
```

Register Bus and NetworkEvents in `onResume()` method and unregister them in `onPause()` method.

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
    // get connectivity status from event.getConnectivityStatus() and do whatever you want
}

@Subscribe
public void onWifiSignalStrengthChanged(WifiSignalStrengthChanged event) {
    // do whatever you want - e.g. read fresh list of access points
}
```

## Example

Look at [MainActivity](https://github.com/pwittchen/NetworkEvents/blob/master/example/src/main/java/pwittchen/com/networkevents/MainActivity.java) in [example app](https://github.com/pwittchen/NetworkEvents/tree/master/example) to see how it works.
