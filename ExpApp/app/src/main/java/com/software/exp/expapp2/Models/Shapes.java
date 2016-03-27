package com.software.exp.expapp2.Models;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.software.exp.expapp2.BuildConfig;
import com.software.exp.expapp2.Logic.Utils;

import java.util.ArrayList;

/**
 * Created by work on 17/02/2016.
 */
public class Shapes {

    public String Version;
    public String OS;
    public String Name;
    public String DeviceId;
    public String ModelName;
    public String GcmToken;
    public double ScreenWidth;
    public double ScreenHeight;
    public float Ydpi;
    public float Xdpi;

    public String UserCountry;
    public String AppLocale;


    public ArrayList<ExpShape> ExpShapeList;

    public Shapes(TelephonyManager telephonyManager, WindowManager wm,DisplayMetrics metrics,Context context) {
        ExpShapeList = new ArrayList<ExpShape>();
        OS = Build.VERSION.RELEASE;
        GcmToken = Utils.GcmToken;

        DeviceId = telephonyManager.getDeviceId();
        ModelName = Utils.getDeviceName();

        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        ScreenWidth = size.x;
        ScreenHeight = size.y;

        Ydpi = metrics.xdpi;
        Xdpi = metrics.ydpi;

        Version = BuildConfig.VERSION_NAME;

        UserCountry = Utils.getUserCountryFromSim(context);
        AppLocale = context.getResources().getConfiguration().locale.getLanguage();
    }


}
