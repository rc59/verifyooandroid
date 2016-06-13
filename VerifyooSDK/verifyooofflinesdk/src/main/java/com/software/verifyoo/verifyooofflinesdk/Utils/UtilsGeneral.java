package com.software.verifyoo.verifyooofflinesdk.Utils;

import android.os.Build;

/**
 * Created by roy on 2/25/2016.
 */
public class UtilsGeneral {
    public static double LastTime;

    public static void SimulateException() {
        int x = 1;
        int y = 0;
        x = x / y;
    }

    public static String GetUserKey(String userName) {
        String serial = Build.SERIAL;
        String key = String.format("%s-%s", serial, userName);
        return key;
    }
}