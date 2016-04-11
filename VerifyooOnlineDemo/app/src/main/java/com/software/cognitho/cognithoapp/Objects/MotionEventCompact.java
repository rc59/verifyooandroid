package com.software.cognitho.cognithoapp.Objects;

import android.view.MotionEvent;

/**
 * Created by roy on 8/25/2015.
 */
public class MotionEventCompact {
    public double RawX;
    public double RawY;
    public double X;
    public double Y;
    public double VelocityX;
    public double VelocityY;
    public double Pressure;
    public double EventTime;
    public double TouchSurface;
    public double AngleZ;
    public double AngleX;
    public double AngleY;
    public double PointerCount;

    public MotionEventCompact() {

    }

    public MotionEventCompact(MotionEvent event) {
        PointerCount = event.getPointerCount();

        Pressure = event.getPressure();
        X = event.getX();
        Y = event.getY();
        RawX = event.getRawX();
        RawY = event.getRawY();

        EventTime = event.getEventTime();
        TouchSurface = event.getSize();
    }
}
