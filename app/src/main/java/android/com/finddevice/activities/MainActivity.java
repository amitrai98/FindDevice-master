package android.com.finddevice.activities;

import android.com.finddevice.R;
import android.com.finddevice.gcm.QuickstartPreferences;
import android.com.finddevice.gcm.RegistrationIntentService;
import android.com.finddevice.services.AppStarter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    private Button btn_starter = null;
    private String TAG = getClass().getSimpleName();
    public static Context context = null;
//    private ProgressDialog mRegistrationProgressBar;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        registerBroadcastListener();
        init();

        if (checkPlayServices()) {
            // Showing Progress bar
//            mRegistrationProgressBar = ProgressDialog.show(MainActivity.this, null, "Please wait..");
//            mRegistrationProgressBar.setCancelable(false);

            Log.i(TAG, "Calling registration service");
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(MainActivity.this, RegistrationIntentService.class);
            startService(intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init(){
        context = MainActivity.this;
        Intent serviceIntent = new Intent(context, AppStarter.class);
        startService(serviceIntent);
        btn_starter = (Button) findViewById(R.id.btn_starter);
        btn_starter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, AppStarter.class));

//                ApiManager apiManager = new ApiManager();
//                apiManager.pingLocation(new PingListener() {
//                    @Override
//                    public void onPingSuccess(String success_message) {
//                        Log.e(TAG, "" + success_message);
//                    }
//
//                    @Override
//                    public void onPingFailure(String error_message) {
//                        Log.e(TAG,""+error_message);
//                    }
//                }, CommonTask.getPingModal(MainActivity.this));
            }
        });


        finish();
    }


    private void registerBroadcastListener() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//                mRegistrationProgressBar.dismiss();
                finish();
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


}
