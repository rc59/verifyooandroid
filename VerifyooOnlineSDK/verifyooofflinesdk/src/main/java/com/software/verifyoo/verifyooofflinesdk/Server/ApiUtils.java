package com.software.verifyoo.verifyooofflinesdk.Server;

/**
 * Created by roy on 4/14/2016.
 */
public class ApiUtils {
    public static boolean isProd = false;

    public static String getBaseIP() {
        String ip = "http://192.168.1.151/";

        if(isProd) {
            ip = "http://52.34.215.246/";
        }

        return ip;
    }

    public static String getBaseApiUrl() {
        //String name = "cognitho/api/";

        String name = "cognitho/api/";

        if(isProd) {
            name = "verifyoo/api/";
        }

        return name;
    }
}
