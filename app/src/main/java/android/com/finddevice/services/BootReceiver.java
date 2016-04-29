package android.com.finddevice.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by amitrai on 21/3/16.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMan.getActiveNetworkInfo();
        if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI){
            Log.e("WifiReceiver", "Have Wifi Connection");
            if (intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false)){
                Intent serviceIntent = new Intent(context, AppStarter.class);
                context.startService(serviceIntent);
            } else {
                // wifi connection was lost
            }
        }
        else
            Log.e("WifiReceiver", "Don't have Wifi Connection");


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
