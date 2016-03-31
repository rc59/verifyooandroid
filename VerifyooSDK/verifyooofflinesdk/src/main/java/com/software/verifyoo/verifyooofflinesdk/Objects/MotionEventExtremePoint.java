package com.software.verifyoo.verifyooofflinesdk.Objects;

/**
 * Created by roy on 2/21/2016.
 */
public class MotionEventExtremePoint extends MotionEventCompact {
    public double AdjustedX;
    public double AdjustedY;

    public double Angle;

    public int[] ShapeType;
    public double ExtremePointAngle;

    public MotionEventExtremePoint() {

    }

    public MotionEventExtremePoint(MotionEventCompact originalEvent) {
        Xmm = originalEvent.Xmm;
        Ymm = originalEvent.Ymm;
        EventTime = originalEvent.EventTime;


    }
}
