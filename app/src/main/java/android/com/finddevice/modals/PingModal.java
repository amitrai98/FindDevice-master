package android.com.finddevice.modals;

/**
 * Created by root on 19/3/16.
 */
public class PingModal {

    String geo_cordinate;
    String wifi_connected;
    String package_name;
    String appname;
    String device_mac;
    String device_name;

    public String getGeo_cordinate() {
        return geo_cordinate;
    }

    public void setGeo_cordinate(String geo_cordinate) {
        this.geo_cordinate = geo_cordinate;
    }

    public String getWifi_connected() {
        return wifi_connected;
    }

    public void setWifi_connected(String wifi_connected) {
        this.wifi_connected = wifi_connected;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getDevice_mac() {
        return device_mac;
    }

    public void setDevice_mac(String device_mac) {
        this.device_mac = device_mac;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public PingModal(String geo_cordinate,
                     String wifi_connected,
                     String package_name,
                     String appname,
                     String device_mac,
                     String device_name){

        this.geo_cordinate = geo_cordinate;
        this.wifi_connected = wifi_connected;
        this.package_name = package_name;
        this.appname = appname;
        this.device_mac = device_mac;
        this.device_name = device_name;


    }
}
