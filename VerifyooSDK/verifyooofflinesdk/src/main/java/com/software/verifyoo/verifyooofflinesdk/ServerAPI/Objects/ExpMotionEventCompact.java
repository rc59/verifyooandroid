package com.software.verifyoo.verifyooofflinesdk.ServerAPI.Objects;

import VerifyooLogic.UserProfile.MotionEventCompact;
import VerifyooLogic.Utils.UtilsCalc;

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
        RawX = motionEventCompact.RawXpixel;
        RawY = motionEventCompact.RawYpixel;
        X = motionEventCompact.Xpixel;
        Y = motionEventCompact.Ypixel;
        VelocityX = motionEventCompact.VelocityX;
        VelocityY = motionEventCompact.VelocityY;
        Velocity = UtilsCalc.CalcPitagoras(VelocityX, Velocity);
        Pressure = motionEventCompact.Pressure;
        EventTime = motionEventCompact.EventTime;
        TouchSurface = motionEventCompact.TouchSurface;

        AngleX = motionEventCompact.AngleX;
        AngleY = motionEventCompact.AngleY;
        AngleZ = motionEventCompact.AngleZ;

        GyroX = motionEventCompact.AngleX;
        GyroY = motionEventCompact.AngleY;
        GyroZ = motionEventCompact.AngleZ;
    }
}
