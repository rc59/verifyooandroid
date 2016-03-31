package com.software.verifyoo.verifyooofflinesdk.Objects.ParamProperties;

/**
 * Created by roy on 2/15/2016.
 */
public class AccelerationInterval {
    protected int mState;
    protected double mTimeInterval;

    public AccelerationInterval(int state, double timeInterval) {
        mState = state;
        mTimeInterval = timeInterval;
    }

    public void AddInterval(double interval) {
        mTimeInterval += interval;
    }
}
