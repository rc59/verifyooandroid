package com.software.dalal.signaturecapture.Models;

import android.view.MotionEvent;

/**
 * Created by roy on 12/12/2016.
 */
public class ExpMotionEventCompact {
    public double X;
    public double Y;
    public double Pressure;
    public double EventTime;
    public double TouchSurface;

    public ExpMotionEventCompact(MotionEvent event) {
        X = event.getX();
        Y = event.getY();
        EventTime = event.getEventTime();
        Pressure = event.getPressure();
        TouchSurface = event.getSize();
    }
}
