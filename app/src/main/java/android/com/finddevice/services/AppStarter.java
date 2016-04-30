package android.com.finddevice.services;

import android.app.Service;
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
    public void onStart(Intent intent, int startid)
    {
        initPing();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initPing();
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        Intent in = new Intent();
        in.setAction("YouWillNeverKillMe");
        sendBroadcast(in);
    }

    private void initPing(){

        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate
                (new Runnable() {
                    public void run() {

                        Log.e("schedule task started", "" + System.currentTimeMillis());
                    if(CommonTask.isWifiConnected(AppStarter.this)){

                        Log.e("schedule task started", "api hit achived" );
                        ApiManager apiManager = new ApiManager();
                        if(!CommonTask.isInternetAvailable(AppStarter.this))
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
                        }, CommonTask.getPingModal(AppStarter.this), AppStarter.this);
                    }

                    }
                }, 0, 5, TimeUnit.MINUTES);





    }






}
