package android.com.finddevice.listeners;

/**
 * Created by root on 19/3/16.
 */
public interface PingListener {
    public void onPingSuccess(String success_message);
    public void onPingFailure(String error_message);
}
