package com.software.verifyoo.verifyooofflinesdk.Utils;

import com.software.verifyoo.verifyooofflinesdk.Objects.MotionEventCompact;

/**
 * Created by roy on 1/5/2016.
 */
public class UtilsCalc {

    public static double GetMaxValue(double value1, double value2) {
        if (value1 > value2) {
            return value1;
        }

        return value2;
    }

    public static double GetMinValue(double value1, double value2) {
        if (value1 < value2) {
            return value1;
        }

        return value2;
    }

    public static double CalcPitagoras(double value1, double value2) {
        double value = value1 * value1 + value2 * value2;
        value = Math.sqrt(value);

        return value;
    }

    public static boolean CheckIfOppositeSymbol(double value1, double value2) {
        if ((value1 > 0 && value2 < 0) || (value1 < 0 && value2 > 0)) {
            return true;

        }
        else {
            return false;
        }
    }

    public static boolean CheckIfBetween(double value, double min, double max) {
        if (value > min && value < max) {
            return true;
        }
        else {
            return false;
        }
    }

    public static double GetPercentageDiff(double value1, double value2) {

        if(value1 == 0 || value2 == 0)
        {
            return 1;
        }

        value1 = Math.abs(value1);
        value2 = Math.abs(value2);

        double result = 0;

        if(value1 > value2)
        {
            result = value2 / value1;
        }
        else
        {
            result = value1 / value2;
        }

        return result;
    }

    public static MotionEventCompact GetMaxXpixel(MotionEventCompact pointA, MotionEventCompact pointB){
        if(pointA.X > pointB.X)
            return pointA;
        return pointB;
    }

    public static MotionEventCompact GetMinXpixel(MotionEventCompact pointA, MotionEventCompact pointB){
        if(pointA.X > pointB.X)
            return pointB;
        return pointA;
    }

    public static MotionEventCompact GetMaxYpixel(MotionEventCompact pointA, MotionEventCompact pointB){
        if(pointA.Y > pointB.Y)
            return pointA;
        return pointB;
    }

    public static MotionEventCompact GetMinYpixel(MotionEventCompact pointA, MotionEventCompact pointB){
        if(pointA.Y > pointB.Y)
            return pointB;
        return pointA;
    }
}
