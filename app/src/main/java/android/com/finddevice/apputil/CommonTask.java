package android.com.finddevice.apputil;

import android.Manifest;
import android.app.ActivityManager;
import android.com.finddevice.modals.PingModal;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.net.URLEncoder;
import java.util.List;

/**
 * Created by root on 20/3/16.
 */
public class CommonTask {


    private static String geo_cordinate;
    private static String wifi_connected;
    private static String package_name;
    private static String appname;
    private static String device_mac;
    private static String device_name;

    private static String TAG = CommonTask.class.getSimpleName();

    public static PingModal getPingModal(Context context) {
        PingModal pingModal = null;

        if(context == null){
            Log.e(TAG, "context null");
            return null;
        }
        String geo = null;

        try {

            geo = getLocation(context);
            if (geo != null) {
                geo_cordinate = URLEncoder.encode(geo.trim(), "UTF-8");
            } else
                geo_cordinate = URLEncoder.encode("no_value", "UTF-8");

        }catch (Exception e){

        }

        String wifi = null;
        try {
            wifi = getWifiName(context);
            if (wifi != null) {
                if (wifi.contains("\""))
                    wifi = wifi.replace("\"", "");
                wifi_connected = URLEncoder.encode(wifi.trim(), "UTF-8");
            } else
                wifi_connected = URLEncoder.encode("no_value", "UTF-8");
        }catch (Exception e){}

        String mac = null;
        try {
            mac = getDeviceMac(context);
            if (mac != null) {
                device_mac = URLEncoder.encode(mac, "UTF-8");
            } else
                device_mac = URLEncoder.encode("no_value", "UTF-8");
        }catch (Exception e){}

        String devicename = null;
        try {
            devicename = getDeviceName();
            if (devicename != null) {
                device_name = URLEncoder.encode(devicename, "UTF-8");
            } else
                device_name = URLEncoder.encode("no_value", "UTF-8");
        }catch (Exception e){}

        String packageName = null;
        try {
            packageName = getPackName(context);
            if (packageName != null) {
                package_name = URLEncoder.encode(packageName, "UTF-8");
                appname = URLEncoder.encode("appname", "UTF-8");
            } else
                package_name = URLEncoder.encode("no_value", "UTF-8");

        }catch (Exception e){}



            pingModal = new PingModal(geo_cordinate,
                    wifi_connected,
                    package_name,
                    device_mac,
                    appname,
                    device_name);

        return pingModal;
    }

    /**
     * returns the name of wifi connected
     * @param context
     * @return
     */
    public static String getWifiName(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (manager.isWifiEnabled()) {
            WifiInfo wifiInfo = manager.getConnectionInfo();
            if (wifiInfo != null) {
                NetworkInfo.DetailedState state = WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState());
                if (state == NetworkInfo.DetailedState.CONNECTED || state == NetworkInfo.DetailedState.OBTAINING_IPADDR) {
                    return wifiInfo.getSSID();
                }
            }
        }
        return null;
    }


    /**
     * returns devices mac address
     * @param context
     * @return
     */
    public static String getDeviceMac(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String macAddress = wInfo.getMacAddress();

        return macAddress;
    }


    public static String getDeviceName() {

        String mString = "";

        mString.concat("VERSION.RELEASE {" + Build.VERSION.RELEASE + "}");
        mString.concat("\nVERSION.INCREMENTAL {" + Build.VERSION.INCREMENTAL + "}");
        mString.concat("\nVERSION.SDK {" + Build.VERSION.SDK + "}");
        mString.concat("\nBOARD {" + Build.BOARD + "}");
        mString.concat("\nBRAND {" + Build.BRAND + "}");
        mString.concat("\nDEVICE {" + Build.DEVICE + "}");
        mString.concat("\nFINGERPRINT {" + Build.FINGERPRINT + "}");
        mString.concat("\nHOST {" + Build.HOST + "}");
        mString.concat("\nID {" + Build.ID + "}");

        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model + mString;
        }

    }


    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }


    public static String getPackName(Context context) {
        String packagename = null;
        try {
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

            List<ActivityManager.RunningAppProcessInfo> tasks = manager.getRunningAppProcesses();

            Log.i("current_app", tasks.get(0).processName);
            packagename = tasks.get(0).processName;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return packagename;
    }




    public static String  getLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocationGPS != null) {
                return "lat_"+lastKnownLocationGPS.getLatitude()+"_long_"+lastKnownLocationGPS.getLongitude();
            } else {
                Location loc =  locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                System.out.println("1::"+loc);
                System.out.println("2::"+loc.getLatitude());
                return "lat_"+loc.getLatitude()+"_long_"+loc.getLongitude();
            }
        } else {
            return null;
        }
    }

    /**
     * if internet is connected
     * @return
     */
    public static boolean  isInternetAvailable(Context context) {
        if(context == null)
            return false;
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] networkInfos = connectivity.getAllNetworkInfo();
            if (networkInfos != null)
                for (NetworkInfo info : networkInfos) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
        }
        return false;
    }
}
