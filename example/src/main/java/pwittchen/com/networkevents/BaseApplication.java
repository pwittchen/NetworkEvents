package pwittchen.com.networkevents;

import android.app.Application;

public class BaseApplication extends Application {

    private static BaseApplication instance;

    public BaseApplication() {
        super();
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
