package com.software.verifyoo.verifyooofflinesdk.Objects;

import android.view.MotionEvent;

/**
 * Created by roy on 12/28/2015.
 */
public class MotionEventCompact {
    public int IndexInStroke;
    public double RawX;
    public double RawY;
    public double X;
    public double Y;

    public double VelocityX;
    public double VelocityY;
    public double Pressure;
    public double EventTime;
    public double RelativeEventTime;
    public double TouchSurface;
    public double AngleZ;
    public double AngleX;
    public double AngleY;

    public double PointerCount;
    public boolean IsPause;

    public MotionEventCompact() {
        IsPause = false;
    }

    public MotionEventCompact(MotionEvent event) {
        IsPause = false;
        Pressure = event.getPressure();
        X = event.getX();
        Y = event.getY();
        RawX = event.getRawX();
        RawY = event.getRawY();

        EventTime = event.getEventTime();
        TouchSurface = event.getSize();
    }
}
