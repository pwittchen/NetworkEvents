InternetConnectionStateListener
===============================

Sample Android application listening internet connection state.

### Short overview
[ConnectivityManager](http://developer.android.com/reference/android/net/ConnectivityManager.html) avaiable in Android API allows us to check, whether we are connected to WiFi network or mobile network. Despite this fact, we can be connected to WiFi network, but such network can be also disconnected from the Internet. This project shows, how can we create additional so called _InternetConnectionChangeReceiver_ which allows us to determine, if we really have access to the Internet besides being connected to WiFi network.
