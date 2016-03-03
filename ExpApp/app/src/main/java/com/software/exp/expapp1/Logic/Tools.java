package com.software.exp.expapp1.Logic;

import android.os.Build;
import android.text.TextUtils;

import com.software.exp.expapp1.Models.ExpShape;
import com.software.exp.expapp1.Models.Stroke;

/**
 * Created by roy on 8/6/2015.
 */
public class Tools {
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

    public static void prepareShapeForSerialize(ExpShape shape) {
        Stroke stroke, prevStroke;
        MotionEventCompact eventCompactStart;
        MotionEventCompact eventCompactEnd;
        MotionEventCompact eventCompactTemp;

        double pressureMax, pressureMin, pressureAvg;
        double touchSurfaceMax, touchSurfaceMin, touchSurfaceAvg;
        double maxX, minX, maxY, minY;

        for (int idx = 0; idx < shape.Strokes.size(); idx++) {
            stroke = shape.Strokes.get(idx);
//            stroke.DownTime = stroke.ListEvents.get(0).EventTime;
//            stroke.UpTime = stroke.ListEvents.get(stroke.ListEvents.size() - 1).EventTime;

            if (idx > 0) {
                prevStroke = shape.Strokes.get(idx - 1);
//                stroke.PauseBeforeStroke = stroke.DownTime - shape.Strokes.get(idx - 1).UpTime;
//                stroke.RelativePosX = stroke.ListEvents.get(0).X - prevStroke.ListEvents.get(prevStroke.ListEvents.size() - 1).X;
//                stroke.RelativePosY = stroke.ListEvents.get(0).Y - prevStroke.ListEvents.get(prevStroke.ListEvents.size() - 1).Y;
            }

            eventCompactStart = stroke.ListEvents.get(0);
            eventCompactEnd = stroke.ListEvents.get(stroke.ListEvents.size() - 1);

            //stroke.TimeInterval = eventCompactEnd.EventTime - eventCompactStart.EventTime;

//            pressureMax = -1;
//            pressureMin = 1000000;
//            pressureAvg = 0;
//
//            touchSurfaceMax = -1;
//            touchSurfaceMin = 1000000;
//            touchSurfaceAvg = 0;
//
//            maxX = -1;
//            minX = -1;
//            maxY = -1;
//            minY = -1;
//
//            stroke.NumEvents = stroke.ListEvents.size();
//            for (int idxEvent = 0; idxEvent < stroke.NumEvents; idxEvent++) {
//                eventCompactTemp = stroke.ListEvents.get(idxEvent);
//
//                if (eventCompactTemp.Pressure > pressureMax) {
//                    pressureMax = eventCompactTemp.Pressure;
//                }
//
//                if (eventCompactTemp.Pressure < pressureMin) {
//                    pressureMin = eventCompactTemp.Pressure;
//                }
//
//                pressureAvg += eventCompactTemp.Pressure;
//
//                if (eventCompactTemp.TouchSurface > touchSurfaceMax) {
//                    touchSurfaceMax = eventCompactTemp.TouchSurface;
//                }
//
//                if (eventCompactTemp.TouchSurface < touchSurfaceMin) {
//                    touchSurfaceMin = eventCompactTemp.TouchSurface;
//                }
//
//                touchSurfaceAvg += eventCompactTemp.TouchSurface;
//
//                if (maxX == -1) {
//                    maxX = eventCompactTemp.RawX;
//                    minX = eventCompactTemp.RawX;
//                    maxY = eventCompactTemp.RawY;
//                    minX = eventCompactTemp.RawY;
//                }
//                else {
//                    if (eventCompactTemp.RawX > maxX) {
//                        maxX = eventCompactTemp.RawX;
//                    }
//                    if (eventCompactTemp.RawX < minX) {
//                        minX = eventCompactTemp.RawX;
//                    }
//                    if (eventCompactTemp.RawY > maxY) {
//                        maxY = eventCompactTemp.RawY;
//                    }
//                    if (eventCompactTemp.RawY < minY) {
//                        minY = eventCompactTemp.RawY;
//                    }
//                }
//            }

//            stroke.Height = maxY - minY;
//            stroke.Width = maxX - minX;
//            stroke.Area = stroke.Height * stroke.Width;
//
//            pressureAvg = pressureAvg/stroke.NumEvents;
//
//            stroke.PressureMax = pressureMax;
//            stroke.PressureMin = pressureMin;
//            stroke.PressureAvg = pressureAvg;

//            touchSurfaceAvg = touchSurfaceAvg/stroke.NumEvents;
//
//            stroke.TouchSurfaceMax = touchSurfaceMax;
//            stroke.TouchSurfaceMin = touchSurfaceMin;
//            stroke.TouchSurfaceAvg = touchSurfaceAvg;
        }
    }

    public static double pitagoras(double velocityX, double velocityY) {
        double velocity = velocityX * velocityX + velocityY * velocityY;
        velocity = Math.sqrt(velocity);

        return velocity;
    }
}
