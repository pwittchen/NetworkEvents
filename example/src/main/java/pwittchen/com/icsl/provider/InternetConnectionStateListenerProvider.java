package pwittchen.com.icsl.provider;

import com.pwittchen.icsl.library.InternetConnectionStateListener;

import pwittchen.com.icsl.BaseApplication;

public final class InternetConnectionStateListenerProvider {
    // passing Context (here BaseApplication's Context) and instance of Otto Event Bus
    private static final InternetConnectionStateListener ICSL = new InternetConnectionStateListener(BaseApplication.getInstance(), BusProvider.getInstance());

    public static InternetConnectionStateListener getInstance() {
        return ICSL;
    }

    private InternetConnectionStateListenerProvider() {
        // No instances.
    }
}
