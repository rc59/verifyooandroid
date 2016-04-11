package com.software.cognitho.cognithoapp.Tools;

import android.os.Build;

/**
 * Created by roy on 8/25/2015.
 */
public class DeviceMgr {
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return Utils.capitalize(model);
        }
        if (manufacturer.equalsIgnoreCase("HTC")) {
            // make sure "HTC" is fully capitalized.
            return "HTC " + model;
        }
        return Utils.capitalize(manufacturer) + " " + model;
    }
}
