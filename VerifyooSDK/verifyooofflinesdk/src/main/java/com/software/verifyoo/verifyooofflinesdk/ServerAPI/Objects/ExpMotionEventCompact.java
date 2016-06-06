package com.software.verifyoo.verifyooofflinesdk.ServerAPI.Objects;

import Data.UserProfile.Raw.MotionEventCompact;

/**
 * Created by roy on 2/24/2016.
 */
public class ExpMotionEventCompact {
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

    public double AngleX;
    public double AngleY;
    public double AngleZ;

    public double GyroX;
    public double GyroY;
    public double GyroZ;

    public ExpMotionEventCompact(MotionEventCompact motionEventCompact) {
        X = motionEventCompact.Xpixel;
        Y = motionEventCompact.Ypixel;
        VelocityX = motionEventCompact.VelocityX;
        VelocityY = motionEventCompact.VelocityY;
        Velocity = CalcPitagoras(VelocityX, Velocity);
        Pressure = motionEventCompact.Pressure;
        EventTime = motionEventCompact.EventTime;
        TouchSurface = motionEventCompact.TouchSurface;

        AngleX = motionEventCompact.AccelerometerX;
        AngleY = motionEventCompact.AccelerometerY;
        AngleZ = motionEventCompact.AccelerometerZ;

        GyroX = motionEventCompact.GyroX;
        GyroY = motionEventCompact.GyroY;
        GyroZ = motionEventCompact.GyroZ;
    }

    private double CalcPitagoras(double value1, double value2) {
        double value = value1 * value1 + value2 * value2;
        value = Math.sqrt(value);

        return value;
    }
}
