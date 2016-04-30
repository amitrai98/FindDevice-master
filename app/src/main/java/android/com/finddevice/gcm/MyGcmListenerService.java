package android.com.finddevice.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.com.finddevice.R;
import android.com.finddevice.activities.MediaActivity;
import android.com.finddevice.apputil.AppConstants;
import android.com.finddevice.util.AppBin;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.Locale;

/**
 * Created by ankitkumar on 28/10/15.
 */
public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";
    private static TextToSpeech tts;
    private boolean SPEECHSTATUS = false;

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message").trim();
        if(message != null){
            if(message.equals(AppConstants.COMMAND_SHUTDOWN)){
//                AppBin.shutDown(this);
                AppBin.playFile(this, message);
            }else if(message.equals(AppConstants.COMMAND_RESTART)){
                AppBin.playFile(this, message);
            }else {
                AppBin.playFile(this, message);
//                speakOut(message);
            }
        }


        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);


            // Notification for eventys except Chat Related stuff
//            System.out.println("Notification is .... " + message);
//            sendNotification(message);


    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */

    private void sendNotification(String message) {
        PendingIntent pendingIntent = null;

        Intent intent = new Intent(this, MediaActivity.class);
        intent.putExtra("message", message);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap largeIconBM = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(largeIconBM)
                .setContentTitle(getString(R.string.app_name))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentText(message)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = (int) System.currentTimeMillis();

        notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());

            /*send broadcast to Potluck screen*/
            Intent in = new Intent("PotLuckResponse");
            in.putExtra("message", message);
            this.sendBroadcast(in);


    }

    /**
     * speaks the message received from gcm
     */
    private void speakOut(final String message){
        if(message != null){
            new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {

                        int result = tts.setLanguage(Locale.US);

                        if (result == TextToSpeech.LANG_MISSING_DATA
                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS", "This Language is not supported");
                        } else {
                           try {
                                if(tts != null){
                                    tts.stop();
                                    tts.shutdown();
                                    tts = null;
                                }
                               tts = new TextToSpeech(MyGcmListenerService.this, this);
                               tts.speak(message,TextToSpeech.QUEUE_FLUSH, null);
                           }catch (Exception e){
                               e.printStackTrace();
                           }
                        }

                    } else {
                        Log.e("TTS", "Initilization Failed!");
                    }
                }
            };

        }
    }
}