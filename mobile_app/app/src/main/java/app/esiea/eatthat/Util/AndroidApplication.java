package app.esiea.eatthat.Util;

import android.app.Application;
import android.content.Context;

public class AndroidApplication extends Application {

    private static AndroidApplication instance;

    public AndroidApplication()
    {
        instance = this;
    }
    public static Context getContext()
    {
        return instance;
    }
}
