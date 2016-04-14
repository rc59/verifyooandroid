package com.software.verifyoo.verifyooofflinesdk.Utils;

/**
 * Created by roy on 1/5/2016.
 */
public class UtilsCalc {
    public static double CalcPitagoras(double value1, double value2) {
        double value = value1 * value1 + value2 * value2;
        value = Math.sqrt(value);

        return value;
    }
}
