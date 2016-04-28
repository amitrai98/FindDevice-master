package android.com.finddevice.gcm;

import android.app.IntentService;
import android.com.finddevice.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Created by ankitkumar on 28/10/15.
 */
public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            // [START register_for_gcm]
            // Initially this call goes out to the network to retrieve the token, subsequent calls
            // are local.
            // [START get_token]
            Log.i(TAG, "Inside RegistrationIntentService");
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            // [END get_token]
            Log.i(TAG, "GCM Registration Token: " + token);

            // TODO: Implement this method to send any registration to your app's servers.
            if (token != null && token.length() > 0) {
    //            sendRegistrationToServer(token);

            // Subscribe to topic channels
            subscribeTopics(token);
            }
            System.out.println("token is " + token);
            // You should store a boolean that indicates whether the generated token has been
            // sent to your server. If the boolean is false, send the token to your server,
            // otherwise your server should have already received the token.
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true).apply();
            // [END register_for_gcm]
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
        }

        // Notify UI that registration has completed, so the progress indicator can be hidden.

    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    // [START subscribe_topics]
    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }

    //TODO
    //Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
    //LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(registrationComplete);


    /**
     * Add Device token Api
     */
    /*private void sendRegistrationToServer(final String token) {
     //   final AppPreferences appPrefs = AppPreferences.getInstance(this);
        //    AppUtility.showAlertDialog(this, getString(R.string.app_name), getString(R.string.connection_error));
        if (CommonTask.isInternetAvailable(this) == false) return;

        JSONObject paramsObject = new JSONObject();
        try {
            paramsObject.put("DeviceId", token);
            paramsObject.put("DeviceType", "Android");

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        TwingleApis.addDeviceTokenApi(paramsObject, false, this, new BaseListener.OnWebServiceCompleteListener<JSONObject>() {
            @Override
            public void onWebServiceComplete(JSONObject resultObject) {
                try {
                    if (resultObject.getBoolean("_isSuccessful")) {
                        appPrefs.putString(PrefConstants.DEVICE_TOKEN, token);
                        Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(registrationComplete);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }*/

}