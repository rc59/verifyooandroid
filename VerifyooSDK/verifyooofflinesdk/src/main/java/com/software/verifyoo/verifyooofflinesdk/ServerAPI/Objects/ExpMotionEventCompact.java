package com.software.verifyoo.verifyooofflinesdk.ServerAPI.Objects;

import com.software.verifyoo.verifyooofflinesdk.Utils.UtilsCalc;

import VerifyooLogic.UserProfile.MotionEventCompact;

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
    public double AngleZ;
    public double AngleX;
    public double AngleY;

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
        AngleZ = motionEventCompact.AngleZ;
        AngleX = motionEventCompact.AngleX;
        AngleY = motionEventCompact.AngleY;
    }
}
