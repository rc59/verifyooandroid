package com.software.exp.expapp.Logic;

import java.util.ArrayList;

public class Stroke {
    public ArrayList<MotionEventCompact> ListEvents;
    public double Length;
    public double TimeInterval;
    public double PauseBeforeStroke;
    public double NumEvents;
    public double DownTime;
    public double UpTime;

    public double PressureMax;
    public double PressureMin;
    public double PressureAvg;

    public double TouchSurfaceMax;
    public double TouchSurfaceMin;
    public double TouchSurfaceAvg;

    public double Width;
    public double Height;
    public double Area;

    public double RelativePosX;
    public double RelativePosY;

    public Stroke() {
        Length = 0;
        ListEvents = new ArrayList<MotionEventCompact>();
    }
}
