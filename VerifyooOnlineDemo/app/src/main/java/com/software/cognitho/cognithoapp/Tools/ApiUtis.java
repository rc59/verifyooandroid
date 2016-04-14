package com.software.cognitho.cognithoapp.Tools;

/**
 * Created by roy on 8/25/2015.
 */
public class ApiUtis {
    public static boolean isProd = true;

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