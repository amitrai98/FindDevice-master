package android.com.finddevice.networking;

import android.com.finddevice.apputil.AppConstants;
import android.com.finddevice.listeners.PingListener;
import android.com.finddevice.modals.PingModal;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by amitrai on 17/3/16.
 */
public class ApiManager {

    private final String TAG = getClass().getSimpleName();

    /**
     * calls the api to send data
     * @param listener
     * @param pingModal
     */
    public void pingLocation(final PingListener listener, PingModal pingModal) {

        if(listener != null){
            Retrofit ping_location = new Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            DeviceApi deviceApi = ping_location.create(DeviceApi.class);

            try{
                Call<String> deviceStatus = deviceApi.UpdateDeviceStatus(pingModal.getGeo_cordinate(),
                        pingModal.getWifi_connected(),pingModal.getPackage_name(),
                        pingModal.getAppname(),pingModal.getDevice_mac(),pingModal.getDevice_name());

                deviceStatus.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Response<String> response, Retrofit retrofit) {
                        String pingresponse = response.body();
                        if(pingresponse != null){
                            listener.onPingSuccess(pingresponse);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        if(t!= null && t.getMessage() != null){
                            listener.onPingFailure(t.getMessage());
                        }
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }

}
