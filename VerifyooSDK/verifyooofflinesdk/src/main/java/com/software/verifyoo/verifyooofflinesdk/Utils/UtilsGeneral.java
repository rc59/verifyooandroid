package com.software.verifyoo.verifyooofflinesdk.Utils;

import android.os.Build;
import android.text.TextUtils;

import java.util.Random;

import Data.MetaData.NormalizedGestureContainer;
import Data.UserProfile.Extended.TemplateExtended;
import Data.UserProfile.Raw.Template;

/**
 * Created by roy on 2/25/2016.
 */
public class UtilsGeneral {
    public static NormalizedGestureContainer NormalizedGestureContainerObj;
    public static TemplateExtended StoredTemplateExtended;
    public static Template StoredTemplate;

    public static boolean IsGesturing;

    public static String ResultAnalysis;
    public static double AuthStartTime;
    public static double AuthEndTime;

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

    public static int[] generateInstructionsList(int size) {
        int[] instructionIndexes = new int[size];

        for(int idx = 0; idx < size; idx++) {
            instructionIndexes[idx] = idx;
        }

        int tempRandom1 = 0;
        int tempRandom2 = 0;
        boolean isRandomGenerated;
        int tempIndex;

        Random rand = new Random();
        for(int idx = 0; idx < size; idx++) {
            isRandomGenerated = false;
            while(!isRandomGenerated) {
                tempRandom1 = rand.nextInt(size);
                tempRandom2 = rand.nextInt(size);

                if (tempRandom1 != tempRandom2) {
                    isRandomGenerated = true;
                }
            }

            tempIndex = instructionIndexes[tempRandom1];
            instructionIndexes[tempRandom1] = instructionIndexes[tempRandom2];
            instructionIndexes[tempRandom2] = tempIndex;
        }

        return instructionIndexes;
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

    public static double Round(double value, int decimals) {
        for(int idx = 0; idx < decimals; idx++) {
            value = value * 10;
        }

        int tempValue = (int) value;
        value = tempValue;

        for(int idx = 0; idx < decimals; idx++) {
            value = value / 10;
        }
        return value;
    }
}