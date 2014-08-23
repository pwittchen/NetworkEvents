package pwittchen.com.networkevents.provider;


import com.pwittchen.network.events.library.NetworkEvents;

import pwittchen.com.networkevents.BaseApplication;

public final class NetworkEventsProvider {
    // passing Context (here BaseApplication's Context) and instance of Otto Event Bus
    private static final NetworkEvents NETWORK_EVENTS = new NetworkEvents(BaseApplication.getInstance(), BusProvider.getInstance());

    public static NetworkEvents getInstance() {
        return NETWORK_EVENTS;
    }

    private NetworkEventsProvider() {
        // No instances.
    }
}
