package com.software.exp.expapp2.Logic;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;

import java.util.Locale;

/**
 * Created by roy on 8/6/2015.
 */
public class Utils {
    public static String Username = "";
    public static String jsonShape = "";
    public static String GcmToken = "";

    public static String getUrl() {
        //return "http://192.168.0.111:3001/";
        return "http://52.26.178.48/";
    }

    public static  String getTextUrl(){
        return "http://0.0.0.0";
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

    public static double pitagoras(double velocityX, double velocityY) {
        double velocity = velocityX * velocityX + velocityY * velocityY;
        velocity = Math.sqrt(velocity);

        return velocity;
    }

    public static  void changeAppLangauge(Context context, String langCode) {

        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

    }

    public static boolean isRTL(Locale locale)
    {
        String lang = locale.getLanguage();
        if( "iw".equals(lang) || "ar".equals(lang)
                || "fa".equals(lang) || "ur".equals(lang) )
        {
            return true;
        } else {
            return false;
        }
    }
}
