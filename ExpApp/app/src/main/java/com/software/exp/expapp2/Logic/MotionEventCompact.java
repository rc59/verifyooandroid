package com.software.exp.expapp2.Logic;

import android.view.MotionEvent;

/**
 * Created by roy on 8/6/2015.
 */
public class MotionEventCompact {

    public double RawX;
    public double RawY;
    public double X;
    public double Y;
    public double Velocity;
    public double VelocityX;
    public double VelocityY;
    public double Pressure;
    public double EventTime;
    public double TouchSurface;
    public double AngleZ;
    public double AngleX;
    public double AngleY;
    public boolean IsHistory;

    public MotionEventCompact() {
        IsHistory = true;
    }

    public MotionEventCompact(MotionEvent event) {
        IsHistory = false;
        Pressure = event.getPressure();
        X = event.getX();
        Y = event.getY();
        RawX = event.getRawX();
        RawY = event.getRawY();

        EventTime = event.getEventTime();
        TouchSurface = event.getSize();

    }
}
