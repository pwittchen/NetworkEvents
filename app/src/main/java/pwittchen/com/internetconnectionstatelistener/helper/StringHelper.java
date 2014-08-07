package pwittchen.com.internetconnectionstatelistener.helper;

import android.content.Context;

public class StringHelper {
    public static String getString(Context context, int resourceId) {
        return context.getResources().getString(resourceId);
    }
}
