package com.software.verifyoo.verifyooofflinesdk.Utils;

import android.os.Build;

/**
 * Created by roy on 2/17/2016.
 */
public class UtilsData {
    public static String GetUserKey(String userName) {
        String serial = Build.SERIAL;
        String key = String.format("%s-%s", serial, userName);
        return key;
    }
}
