package android.com.finddevice.services;

import android.app.Service;
import android.com.finddevice.activities.MainActivity;
import android.com.finddevice.apputil.CommonTask;
import android.com.finddevice.listeners.PingListener;
import android.com.finddevice.networking.ApiManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by amitrai on 17/3/16.
 */
public class AppStarter extends Service{

    public static Context context = null;


    private String TAG = getClass().getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initPing();
        return super.onStartCommand(intent, flags, startId);

    }


    private void initPing(){
        if(MainActivity.context != null)
            context = MainActivity.context;

        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate
                (new Runnable() {
                    public void run() {
                        Log.e("schedule task started", ""+System.currentTimeMillis());
                        if(context == null)
                            context = getApplicationContext();
                        ApiManager apiManager = new ApiManager();
                        if(!CommonTask.isInternetAvailable(context))
                            return;
                apiManager.pingLocation(new PingListener() {
                    @Override
                    public void onPingSuccess(String success_message) {
                        Log.e(TAG, "" + success_message);
                    }

                    @Override
                    public void onPingFailure(String error_message) {
                        Log.e(TAG,""+error_message);
                    }
                }, CommonTask.getPingModal(context));
                    }
                }, 0, 1, TimeUnit.MINUTES);
    }




}
