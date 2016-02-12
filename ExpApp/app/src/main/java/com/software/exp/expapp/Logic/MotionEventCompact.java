package com.software.exp.expapp.Logic;

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

   /* public double Orientation;
    public double Action;
    public double ActionIdx;
    public double ActionMasked;
    public double EdgeFlags;
    public double Flags;
    public double HistorySize;
    public double MetaState;
    public double ToolMajor;
    public double TouchMajor;

    public double TouchMinor;
    public double PointerCount;
    public double AxisBreak;
    public double AxisGas;
    public double AxisX;
    public double AxisY;
    public double AxisZ;
    public double AxisPressure;
    public double AxisRudder;
    public double AxisThrottle;

    public double AxisDistance;
    public double AxisTilt;
    public double AxisOrientation;
    public double AxisRtTrigger;
    public double AxisLtTrigger;
    public double AxisHatX;
    public double AxisHatY;*/

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

        /*
        Orientation = event.getOrientation();
        Action = event.getAction();
        ActionIdx = event.getActionIndex();
        ActionMasked = event.getActionMasked();
        EdgeFlags = event.getEdgeFlags();
        Flags = event.getFlags();
        HistorySize = event.getHistorySize();
        MetaState = event.getMetaState();
        ToolMajor = event.getToolMajor();
        TouchMajor = event.getTouchMajor();
        TouchMinor = event.getTouchMinor();
        AxisBreak = event.getAxisValue(event.AXIS_BRAKE);
        AxisGas = event.getAxisValue(event.AXIS_GAS);
        AxisDistance = event.getAxisValue(event.AXIS_DISTANCE);
        AxisX = event.getAxisValue(event.AXIS_X);
        AxisY = event.getAxisValue(event.AXIS_Y);
        AxisZ = event.getAxisValue(event.AXIS_Z);

        AxisPressure = event.getAxisValue(event.AXIS_PRESSURE);
        AxisRudder = event.getAxisValue(event.AXIS_RUDDER);
        AxisThrottle = event.getAxisValue(event.AXIS_THROTTLE);
        AxisTilt = event.getAxisValue(event.AXIS_TILT);
        AxisTilt = event.getAxisValue(event.AXIS_TILT);
        AxisOrientation = event.getAxisValue(event.AXIS_ORIENTATION);
        AxisLtTrigger = event.getAxisValue(event.AXIS_LTRIGGER);
        AxisRtTrigger = event.getAxisValue(event.AXIS_RTRIGGER);
        AxisHatX = event.getAxisValue(event.AXIS_HAT_X);
        AxisHatY = event.getAxisValue(event.AXIS_HAT_Y);
        PointerCount = event.getPointerCount();*/

    }
}
