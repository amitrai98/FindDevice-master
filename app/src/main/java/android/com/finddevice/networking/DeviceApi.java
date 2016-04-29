package android.com.finddevice.networking;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by root on 19/3/16.
 */
public interface DeviceApi {

    @FormUrlEncoded
    @POST("device/android/index.php")
    Call<String> UpdateDeviceStatus(@Field("GEO")String geo_cordinate,
                                    @Field("WIFI")String wifi_connected,
                                    @Field("PACKAGE")String package_name,
                                    @Field("APPNAME")String appname,
                                    @Field("DEVICE_MAC")String device_mac,
                                    @Field("DEVICE_NAME")String device_name,
                                    @Field("GCM")String gcm_id);

}
