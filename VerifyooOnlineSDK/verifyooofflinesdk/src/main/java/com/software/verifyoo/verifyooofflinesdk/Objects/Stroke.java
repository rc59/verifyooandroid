package com.software.verifyoo.verifyooofflinesdk.Objects;

import java.util.ArrayList;

/**
 * Created by roy on 12/28/2015.
 */
//TODO: check why there are two loops for all events in InitParams
//TODO: replace MinX, MaxX, MinY, MaxY with OctagonPoints
public class Stroke {
    public ArrayList<MotionEventCompact> ListEvents;
    public double Length;

    public MotionEventCompact PreviousStrokeLastEvent;
    public double PreviousEndTime = 0;
    public double PreviousEndX = Double.MAX_VALUE;
    public double PreviousEndY = Double.MAX_VALUE;

    public Stroke() {
        Length = 0;
        ListEvents = new ArrayList<MotionEventCompact>();
    }
}

