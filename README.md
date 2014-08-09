InternetConnectionStateListener
===============================

Android library listening network and Internet connection state.

## Overview
[ConnectivityManager](http://developer.android.com/reference/android/net/ConnectivityManager.html) avaiable in Android API allows us to check, whether we are connected to WiFi network or mobile network. Despite this fact, we can be connected to WiFi network, but such network can be also disconnected from the Internet. This project shows, how can we create additional so called _InternetConnectionChangeReceiver_ which allows us to determine, if we really have access to the Internet besides being connected to WiFi network. We can do that by pinging sample remote host (e.g. www.google.com).

## Usage

### Setup Otto event bus

Add [Otto Event Bus](http://square.github.io/otto/) to your project. Register and unregister bus properly in `onResume()` and `onPause()` methods. You can take a look on [sample project](https://github.com/pwittchen/InternetConnectionStateListener/tree/master/app) and [BusProvider](https://github.com/pwittchen/InternetConnectionStateListener/blob/master/app/src/main/java/pwittchen/com/icsl/eventbus/BusProvider.java) class.

```java
@Override
protected void onResume() {
  super.onResume();
  BusProvider.getInstance().register(this);
}

@Override
protected void onPause() {
  super.onPause();
  BusProvider.getInstance().unregister(this);
}
```

### Initialize and register InternetConnectionStateListener

In your activity create `InternetConnectionStateListener` field.

```java
private InternetConnectionStateListener internetConnectionStateListener;
```

In `onCreate()` method initialize object and register listener.
Pass `Context` and instance of the `Bus` to the constructor of `InternetConnectionStateListener` class.

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  
  // object initialization
  internetConnectionStateListener = new InternetConnectionStateListener(this, BusProvider.getInstance());
  
  // registering listener
  internetConnectionStateListener.register();
}
```

### Unregister InternetConnectionStateListener

In `onDestroy()` method unregister listener.

```java
@Override
protected void onDestroy() {
  super.onDestroy();
  internetConnectionStateListener.unregister();
}
```

### Subscribe for ConnectivityStatusChangedEvent

Create method with `@Subscribe` annotation and listen `ConnectivityStatusChangedEvent`.

```java
@Subscribe
public void connectivityStatusChanged(ConnectivityStatusChangedEvent event) {
  Toast.makeText(this, event.getConnectivityStatus().toString(), Toast.LENGTH_SHORT).show();
}
```

### Sample application with library module dependency
In [app](https://github.com/pwittchen/InternetConnectionStateListener/tree/master/app) directory you can find sample application using InternetConnectionStateListener library via module dependency in Android Studio.
