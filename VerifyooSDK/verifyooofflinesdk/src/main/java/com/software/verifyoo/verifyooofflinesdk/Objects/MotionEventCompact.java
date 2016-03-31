package com.software.verifyoo.verifyooofflinesdk.Objects;

import android.view.MotionEvent;

/**
 * Created by roy on 12/28/2015.
 */
public class MotionEventCompact {
    public int IndexInStroke;
    public double Xmm;
    public double Ymm;
    public double RawXpixel;
    public double RawYpixel;
    public double Xpixel;
    public double Ypixel;

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
        Xpixel = event.getX();
        Ypixel = event.getY();
        RawXpixel = event.getRawX();
        RawYpixel = event.getRawY();

        EventTime = event.getEventTime();
        TouchSurface = event.getSize();
    }
}
