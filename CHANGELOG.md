CHANGELOG
=========

v. 2.1.2
--------
*04 Oct 2015*

- bumped target SDK version to 23
- bumped buildToolsVersion to 23.0.1
- removed `CHANGE_NETWORK_STATE` and `INTERNET` permissions from `AndroidManifest.xml`, because they're no longer required

v. 2.1.1
--------
*13 Sep 2015*

- updated `InternetConnectionChangeReceiver` class and its API
- fixed failing unit tests
- all changes were provided in a single commit https://github.com/pwittchen/NetworkEvents/commit/2f6999c5cd45ba220f615580e64bfee9e6cc8089

v. 2.1.0
--------
*13 Sep 2015*

replaced `networkInfo.isConnectedOrConnecting()` with `networkInfo.isConnected()` in `isOnline(context)` method in `OnlineCheckerImpl` class.

v. 2.0.1
--------
*09 Aug 2015*

replaced `networkInfo.isConnectedOrConnecting()` with `networkInfo.isConnected()` in `isOnline(context)` method in `OnlineCheckerImpl` class.

v. 2.0.0
--------
*31 Jul 2015*

* removed `withPingUrl(url)` method
* removed `withPingTimeout()` method
* removed `withoutPing()` method
* removed `withoutWifiAccessPointsScan()` method
* removed Otto dependency (now, it's available only for unit tests)
* removed `example-disabling-ping-and-wifi-scan` app sample
* removed `example-ping-customization` app sample
* removed `NetworkHelper` class and moved its method to specific classes with changed scope
* moved permissions to Manifest of library
* disabled WiFi scan by default
* disabled Internet connection check by default
* added `BusWrapper`, which is abstraction for Event Bus required by `NetworkEvents` object
* added `example-greenrobot-bus` app sample
* added `enableWifiScan()` method
* added `enableInternetCheck()` method
* added `getWifiScanResults()` method in WifiSignalStrengthChanged event
* added `getMobileNetworkType()` method in ConnectivityChanged event
* added JavaDoc at: http://pwittchen.github.io/NetworkEvents/
* updated existing sample applications
* updated documentation in `README.md` and library code

v. 1.0.5
--------
*13 May 2015*

In this version, we can customize `NetworkEvents` object. E.g. we can set our own ping url and ping timeout:

```java
networkEvents = new NetworkEvents(this, bus)
        .withPingUrl("http://www.android.com")
        .withPingTimeout(50 * 1000);
```
We can also disable ping or Wifi Access Points Scan:
```java
networkEvents = new NetworkEvents(this, bus)
        .withoutPing()
        .withoutWifiAccessPointsScan();
```
In the main repository, we can find new examples of applications showing how to use these methods.
In addition, internal elements of code (especially `NetworkEvents` class) were updated and new unit tests were created.

v. 1.0.4
--------
*21 Mar 2015*

* migrated unit tests to AndroidJUnit4 runner
* added Google Truth assertions for unit tests
* added Mockito library for creating mocks in unit tests
* fixed bug with `EOFException` in `HttpURLConnection` in `ping` method inside `NetworkHelper` class
* refactored code to make it more testable and loosely coupled

v. 1.0.3
--------
*07 Mar 2015*

- updated `ping` method in `NetworkHelper` class to more reliable
- added unit tests
- refactored code due to unit tests
- added missing comments with license
- updated Android Build Tools to 1.1.0
- removed unused `getWifiInfo()` method from `NetworkHelper` class
- updated `travis.yml` file for CI
- left public API unchanged

v. 1.0.2
--------
*14 Feb 2015*

- improved ping method in NetworkHelper class
- detection of Internet access in WiFi network works faster and is more reliable
- added example of usage of the library with Dagger

v. 1.0.1
--------
*31 Jan 2015*

* added support for API 9 (Android 2.3 GINGERBREAD) and above
* increased code immutability
* removed dependency to unused appcompat library
* performed small code refactoring and reformatting

v. 1.0.0
--------
*30 Jan 2015*

First version of the library is available in Maven Central Repository.
It supports API 15 (Android 4.0.3 - ICE_CREAM_SANDWICH_MR1) and above.

Library has the following features:
* listening connection state (WiFi, WiFi with Internet access, mobile network, off-line)
* listening change of the WiFi signal strength
* informing about the network events via Otto Event Bus

The following bug was fixed:
* Pushing several events twice to the bus when event occurred once
