package android.com.finddevice.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

/**
 * Created by amitrai on 21/3/16.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            final String action = intent.getAction();
            if (action.equals(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)) {
                if (intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false)){
                    Intent serviceIntent = new Intent(context, AppStarter.class);
                    context.startService(serviceIntent);
                } else {
                    // wifi connection was lost
                }
            }

            Intent serviceIntent = new Intent(context, AppStarter.class);
            context.startService(serviceIntent);
        }



    }

}
