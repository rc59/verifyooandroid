package com.software.exp.expapp.Logic;

import android.graphics.Point;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

import java.util.ArrayList;

public class ExpShape {
    public String Instruction;
    public String OS;
    public String Name;
    public String DeviceId;
    public String ModelName;
    public String GcmToken;
    public double ScreenWidth;
    public double ScreenHeight;

    public ArrayList<Stroke> Strokes;

    public ExpShape(TelephonyManager telephonyManager, WindowManager wm, String instruction) {
        OS = Build.VERSION.RELEASE;
        GcmToken = Tools.GcmToken;

        Instruction = instruction;

        DeviceId = telephonyManager.getDeviceId();
        ModelName = Tools.getDeviceName();

        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        ScreenWidth = size.x;
        ScreenHeight = size.y;
    }
}