package android.com.finddevice.activities;

import android.app.Application;
import android.content.Context;

/**
 * Created by root on 20/3/16.
 */
public class AppInitials extends Application{

    public static Context context = null;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext(){
        return context;
    }
}
