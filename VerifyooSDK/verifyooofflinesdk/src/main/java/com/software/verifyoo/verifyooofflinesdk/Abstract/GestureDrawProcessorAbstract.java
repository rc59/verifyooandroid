package com.software.verifyoo.verifyooofflinesdk.Abstract;

import android.content.Context;
import android.gesture.GestureOverlayView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.VelocityTracker;

import com.software.verifyoo.verifyooofflinesdk.Utils.UtilsConvert;

import java.util.ArrayList;

import VerifyooLogic.UserProfile.Stroke;
import VerifyooLogic.UserProfile.MotionEventCompact;

/**
 * Created by roy on 12/28/2015.
 */
public abstract class GestureDrawProcessorAbstract implements GestureOverlayView.OnGestureListener {

    private static VelocityTracker mVelocityTracker;
    protected Stroke mTempStroke;
    private Context mApplicationContext;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private SensorEventListener mSensorListener;

    private float mAccX, mAccY, mAccZ;

    public Stroke getStroke() {
        return mTempStroke;
    }

    public void clearStroke() {
        mTempStroke = new Stroke();
    }

    public void init(Context applicationContext) {
        mTempStroke = new Stroke();
        mApplicationContext = applicationContext;
        mVelocityTracker = VelocityTracker.obtain();

        mSensorManager = (SensorManager) mApplicationContext.getSystemService(mApplicationContext.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                Sensor mySensor = sensorEvent.sensor;

                if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    mAccX = sensorEvent.values[0];
                    mAccY = sensorEvent.values[1];
                    mAccZ = sensorEvent.values[2];
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    public void InitPrevStroke(Stroke currentStroke, ArrayList<Stroke> listStrokes, double length) {
        currentStroke.Length = length;
        if (listStrokes.size() > 0) {
            Stroke prevStroke = listStrokes.get(listStrokes.size() - 1);
            MotionEventCompact prevStrokeLastEvent = prevStroke.ListEvents.get(prevStroke.ListEvents.size() - 1);

            currentStroke.PreviousStrokeLastEvent = prevStrokeLastEvent;
            currentStroke.PreviousEndTime = prevStrokeLastEvent.EventTime;
            currentStroke.PreviousEndX = prevStrokeLastEvent.Xpixel;
            currentStroke.PreviousEndY = prevStrokeLastEvent.Ypixel;
        }
    }

    public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {

        try {
            MotionEventCompact temp;

            mVelocityTracker.addMovement(event);
            mVelocityTracker.computeCurrentVelocity(1000);

            temp = UtilsConvert.ConvertMotionEvent(event);
            temp.AngleY = mAccY;
            temp.AngleX = mAccX;
            temp.AngleZ = mAccZ;

            temp.VelocityX = mVelocityTracker.getXVelocity();
            temp.VelocityY = mVelocityTracker.getYVelocity();

            //mTempStroke.ListEvents.add(temp);

            mSensorManager.registerListener(mSensorListener, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        } catch (Exception exc) {
            String msg = exc.getMessage();
        }

    }

    public void onGesture(GestureOverlayView overlay, MotionEvent event) {
        MotionEventCompact temp;

        mVelocityTracker.addMovement(event);
        mVelocityTracker.computeCurrentVelocity(1000);

        if (event.getHistorySize() > 1) {
            for (int idx = 0; idx < event.getHistorySize(); idx++) {
                temp = new MotionEventCompact();

                temp.Xpixel = event.getHistoricalX(idx);
                temp.Ypixel = event.getHistoricalY(idx);
                temp.EventTime = event.getHistoricalEventTime(idx);
                temp.Pressure = event.getHistoricalPressure(idx);
                temp.TouchSurface = event.getHistoricalSize(idx);
                temp.AngleY = mAccY;
                temp.AngleX = mAccX;
                temp.AngleZ = mAccZ;

                temp.VelocityX = mVelocityTracker.getXVelocity();
                temp.VelocityY = mVelocityTracker.getYVelocity();

                temp.PointerCount = event.getPointerCount();

                mTempStroke.ListEvents.add(temp);
            }

            temp = UtilsConvert.ConvertMotionEvent(event);
            temp.AngleY = mAccY;
            temp.AngleX = mAccX;
            temp.AngleZ = mAccZ;

            temp.VelocityX = mVelocityTracker.getXVelocity();
            temp.VelocityY = mVelocityTracker.getYVelocity();

            mTempStroke.ListEvents.add(temp);
        }
        else {
            temp = UtilsConvert.ConvertMotionEvent(event);
            temp.AngleY = mAccY;
            temp.AngleX = mAccX;
            temp.AngleZ = mAccZ;

            temp.VelocityX = mVelocityTracker.getXVelocity();
            temp.VelocityY = mVelocityTracker.getYVelocity();

            mTempStroke.ListEvents.add(temp);
        }
    }

    public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {

    }

    protected void unRegisterSensors() {
        mSensorManager.unregisterListener(mSensorListener);
    }
}
