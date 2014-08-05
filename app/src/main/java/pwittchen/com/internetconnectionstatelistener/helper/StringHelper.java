package pwittchen.com.internetconnectionstatelistener.helper;

import pwittchen.com.internetconnectionstatelistener.BaseApplication;

public class StringHelper {
    public static String getString(int resourceId) {
        return BaseApplication.getContext().getResources().getString(resourceId);
    }
}
