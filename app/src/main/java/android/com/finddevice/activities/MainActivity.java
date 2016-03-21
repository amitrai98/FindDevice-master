package android.com.finddevice.activities;

import android.com.finddevice.R;
import android.com.finddevice.services.AppStarter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn_starter = null;
    private String TAG = getClass().getSimpleName();
    public static Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
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
    }
}
