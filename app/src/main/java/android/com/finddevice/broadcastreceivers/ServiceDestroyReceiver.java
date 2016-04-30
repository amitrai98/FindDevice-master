package android.com.finddevice.broadcastreceivers;

import android.com.finddevice.services.AppStarter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by root on 30/4/16.
 */
public class ServiceDestroyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, AppStarter.class);
        context.startService(serviceIntent);
    }
}
