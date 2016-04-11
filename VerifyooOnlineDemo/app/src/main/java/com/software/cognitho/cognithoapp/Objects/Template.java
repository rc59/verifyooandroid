package com.software.cognitho.cognithoapp.Objects;

import android.content.Context;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

import com.software.cognitho.cognithoapp.General.AppData;
import com.software.cognitho.cognithoapp.Tools.DeviceMgr;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Template {
    public String OS;
    public String Name;
    public String DeviceId;
    public String ModelName;
    public String CountryCode;
    public String SubId;
    public String DeviceSerial;
    public double ScreenWidth;
    public double ScreenHeight;
    public double Latitude = 0;
    public double Longitude = 0;
    public double Xdpi = 0;
    public double Ydpi = 0;
    public String GcmToken;

    public ArrayList<GestureObj> Gestures;

    public Template() {

    }

    public Template(Context context, TelephonyManager telephonyManager, WindowManager wm, LocationManager locationManager) {
        Gestures = new ArrayList<GestureObj>();

        Name = AppData.userId;
        OS = Build.VERSION.RELEASE;

        SubId = telephonyManager.getSubscriberId();
        DeviceSerial = Build.SERIAL;
        DeviceId = telephonyManager.getDeviceId();
        ModelName = DeviceMgr.getDeviceName();

        getLocationInfo(context, locationManager);

        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        ScreenWidth = size.x;
        ScreenHeight = size.y;
    }

    public void getLocationInfo(Context context, LocationManager locationManager) {
        try {
            LocationManager lm = locationManager;
            List<String> providers = lm.getProviders(true);

            Location l = null;

            for (int i=providers.size()-1; i>=0; i--) {
                l = lm.getLastKnownLocation(providers.get(i));
                if (l != null) break;
            }

            double[] gps = new double[2];

            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(context, Locale.getDefault());

            Latitude = l.getLatitude();
            Longitude = l.getLongitude();

            addresses = geocoder.getFromLocation(Latitude, Longitude, 1);
            CountryCode = addresses.get(0).getCountryCode();
        }
        catch (Exception exc) {
            String s = exc.getMessage();
        }
    }
}
