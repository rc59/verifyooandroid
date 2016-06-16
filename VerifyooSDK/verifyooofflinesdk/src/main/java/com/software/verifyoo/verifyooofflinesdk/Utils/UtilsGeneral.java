package com.software.verifyoo.verifyooofflinesdk.Utils;

import android.os.Build;
import android.text.TextUtils;

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

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        if (manufacturer.equalsIgnoreCase("HTC")) {
            // make sure "HTC" is fully capitalized.
            return "HTC " + model;
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }
        return phrase;
    }

}