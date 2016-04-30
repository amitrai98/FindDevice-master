package android.com.finddevice.util;

import android.com.finddevice.apputil.AppConstants;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

import junit.framework.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 30/4/16.
 */
public class AppBin {

    private static MediaPlayer mp = null;

    /**
     * shutdowns the device
     * @param context
     */
    public static void shutDown(Context context){
        try{
//            Intent i = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
//            i.putExtra("android.intent.extra.KEY_CONFIRM", true);
//            context.startActivity(i);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void playFile(Context context, String command){
        try{
            // Release any resources from previous MediaPlayer
            if (mp != null) {
                mp.release();
            }
            // Create a new MediaPlayer to play this sound
            String[] audio_files = getAllFilesInAssetByExtension(context,"", "mp3");
            AssetFileDescriptor descriptor = null;
            if(audio_files.length > 0){
                mp = new MediaPlayer();
                if(command.equals(AppConstants.COMMAND_SHUTDOWN)){
                    descriptor = context.getAssets().openFd("report_or_switcoff.mp3");
                }else if(command.equals(AppConstants.COMMAND_WARNINIG)){
                    descriptor = context.getAssets().openFd("report_on_skype.mp3");
                }else if(command.equals(AppConstants.COMMAND_LOCK)){
                    descriptor = context.getAssets().openFd("report_or_lock.mp3");
                }else {
                    descriptor = context.getAssets().openFd(audio_files[0]);
                }

                long start = descriptor.getStartOffset();
                long end = descriptor.getLength();

                mp.setDataSource(descriptor.getFileDescriptor(), start, end);
                mp.prepare();

                mp.setVolume(1.0f, 1.0f);
                mp.start();
                AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                int origionalVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
                mp.start();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * getting all the files from assets folder
     * @param context
     * @param path
     * @param extension
     * @return
     */
    public static String[] getAllFilesInAssetByExtension(Context context, String path, String extension){
        Assert.assertNotNull(context);

        try {
            String[] files = context.getAssets().list(path);

//            if(StringHelper.isNullOrEmpty(extension)){
//                return files;
//            }

            List<String> filesWithExtension = new ArrayList<String>();

            for(String file : files){
                if(file.endsWith(extension)){
                    filesWithExtension.add(file);
                }
            }

            return filesWithExtension.toArray(new String[filesWithExtension.size()]);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }


    /**
     * locaks the device.
     */
    private void lockDevice(){

    }

}
