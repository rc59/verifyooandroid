package com.software.verifyoo.verifyooofflinesdk.Models;

import Data.UserProfile.Raw.MotionEventCompact;

/**
 * Created by roy on 8/15/2016.
 */
public class ModelEvent {
    public double EventTime;
    public double Xpixel;
    public double Ypixel;
    public double Pressure;
    public double TouchSurface;

    public ModelEvent() {

    }

    public ModelEvent(MotionEventCompact motionEventCompact) {
        EventTime = motionEventCompact.EventTime;
        Xpixel = motionEventCompact.Xpixel;
        Ypixel = motionEventCompact.Ypixel;
        Pressure = motionEventCompact.Pressure;
        TouchSurface = motionEventCompact.TouchSurface;
    }

    public MotionEventCompact ToEvent() {
        MotionEventCompact tempEvent = new MotionEventCompact();

        tempEvent.EventTime = EventTime;
        tempEvent.Xpixel = Xpixel;
        tempEvent.Ypixel = Ypixel;
        tempEvent.Pressure = Pressure;
        tempEvent.TouchSurface = TouchSurface;

        return tempEvent;
    }
}
