package pwittchen.com.internetconnectionstatelistener;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
    private static BaseApplication instance;

    public BaseApplication() {
        super();
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
