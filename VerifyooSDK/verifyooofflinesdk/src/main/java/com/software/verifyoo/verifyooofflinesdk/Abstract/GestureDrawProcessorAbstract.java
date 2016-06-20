package com.software.verifyoo.verifyooofflinesdk.Abstract;

import android.content.Context;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.VelocityTracker;

import com.software.verifyoo.verifyooofflinesdk.Utils.UtilsConvert;
import com.software.verifyoo.verifyooofflinesdk.Utils.UtilsGeneral;

import java.util.ArrayList;
import java.util.Date;

import Data.UserProfile.Raw.MotionEventCompact;
import Data.UserProfile.Raw.Stroke;

/**
 * Created by roy on 12/28/2015.
 */
public abstract class GestureDrawProcessorAbstract implements GestureOverlayView.OnGestureListener {

    private static VelocityTracker mVelocityTracker;
    protected Stroke mTempStroke;
    private Context mApplicationContext;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mGyro;
    private SensorEventListener mSensorListenerAcc;
    private SensorEventListener mSensorListenerGyro;

    private float mAccX, mAccY, mAccZ;
    private float mGyroX, mGyroY, mGyroZ;

    protected GestureOverlayView mOverlay;
    protected Bitmap mBitMap;
    protected int mWidth;
    protected int mHeight;

    public Stroke getStroke() {
        return mTempStroke;
    }

    public void clearStroke() {
        mTempStroke = new Stroke();
    }

    public void init(Context applicationContext, GestureOverlayView overlay) {
        mOverlay = overlay;
        mTempStroke = new Stroke();
        mApplicationContext = applicationContext;
        mVelocityTracker = VelocityTracker.obtain();

        mSensorManager = (SensorManager) mApplicationContext.getSystemService(mApplicationContext.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        mSensorListenerAcc = new SensorEventListener() {
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

        mSensorListenerGyro = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                Sensor mySensor = sensorEvent.sensor;

                if (mySensor.getType() == Sensor.TYPE_GYROSCOPE) {
//                    mGyroX = sensorEvent.values[0];
//                    mGyroY = sensorEvent.values[1];
//                    mGyroZ = sensorEvent.values[2];
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        mSensorManager.registerListener(mSensorListenerAcc, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(mSensorListenerGyro, mGyro, SensorManager.SENSOR_DELAY_GAME);
    }

    public void InitPrevStroke(Stroke currentStroke, ArrayList<Stroke> listStrokes, double length) {
//        currentStroke.Length = length;
//        if (listStrokes.size() > 0) {
//            Stroke prevStroke = listStrokes.get(listStrokes.size() - 1);
//            MotionEventCompact prevStrokeLastEvent = prevStroke.ListEvents.get(prevStroke.ListEvents.size() - 1);
//
////            currentStroke.PreviousStrokeLastEvent = prevStrokeLastEvent;
////            currentStroke.PreviousEndTime = prevStrokeLastEvent.EventTime;
////            currentStroke.PreviousEndX = prevStrokeLastEvent.Xpixel;
////            currentStroke.PreviousEndY = prevStrokeLastEvent.Ypixel;
//        }
    }

    public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {

        try {
            if (UtilsGeneral.StartTime == 0) {
                UtilsGeneral.StartTime = new Date().getTime();
            }
            MotionEventCompact temp;

            mVelocityTracker.addMovement(event);
            mVelocityTracker.computeCurrentVelocity(1000);

            temp = UtilsConvert.ConvertMotionEvent(event);

            temp.AccelerometerX = mAccX;
            temp.AccelerometerY = mAccY;
            temp.AccelerometerZ = mAccZ;

            temp.GyroX = mGyroX;
            temp.GyroY = mGyroY;
            temp.GyroZ = mGyroZ;

            temp.VelocityX = mVelocityTracker.getXVelocity();
            temp.VelocityY = mVelocityTracker.getYVelocity();

            mTempStroke.ListEvents.add(temp);
        } catch (Exception exc) {
            String msg = exc.getMessage();
        }

    }

    public void onGesture(GestureOverlayView overlay, MotionEvent event) {
        try {

            MotionEventCompact temp;

            mVelocityTracker.addMovement(event);
            mVelocityTracker.computeCurrentVelocity(1000);

            if (event.getHistorySize() >= 1) {
                for (int idx = 0; idx < event.getHistorySize(); idx++) {
                    temp = new MotionEventCompact();

                    temp.Xpixel = event.getHistoricalX(idx);
                    temp.Ypixel = event.getHistoricalY(idx);
                    temp.EventTime = event.getHistoricalEventTime(idx);
                    temp.Pressure = event.getHistoricalPressure(idx);
                    temp.TouchSurface = event.getHistoricalSize(idx);

                    temp.AccelerometerX = mAccX;
                    temp.AccelerometerY = mAccY;
                    temp.AccelerometerZ = mAccZ;

                    temp.GyroX = mGyroX;
                    temp.GyroY = mGyroY;
                    temp.GyroZ = mGyroZ;

                    temp.VelocityX = mVelocityTracker.getXVelocity();
                    temp.VelocityY = mVelocityTracker.getYVelocity();

                    //temp.PointerCount = event.getPointerCount();

                    mTempStroke.ListEvents.add(temp);
                }

                temp = UtilsConvert.ConvertMotionEvent(event);

                temp.AccelerometerX = mAccX;
                temp.AccelerometerY = mAccY;
                temp.AccelerometerZ = mAccZ;

                temp.GyroX = mGyroX;
                temp.GyroY = mGyroY;
                temp.GyroZ = mGyroZ;

                temp.VelocityX = mVelocityTracker.getXVelocity();
                temp.VelocityY = mVelocityTracker.getYVelocity();

//            if (event.getPointerCount() > 1) {
//                temp.Xpixel2 = event.getX(1);
//                temp.Ypixel2 = event.getY(1);
//
//                if (event.getPointerCount() > 2) {
//                    temp.Xpixel3 = event.getX(2);
//                    temp.Ypixel3 = event.getY(2);
//                }
//            }

                mTempStroke.ListEvents.add(temp);
            } else {
                temp = UtilsConvert.ConvertMotionEvent(event);

                temp.AccelerometerX = mAccX;
                temp.AccelerometerY = mAccY;
                temp.AccelerometerZ = mAccZ;

                temp.GyroX = mGyroX;
                temp.GyroY = mGyroY;
                temp.GyroZ = mGyroZ;

                temp.VelocityX = mVelocityTracker.getXVelocity();
                temp.VelocityY = mVelocityTracker.getYVelocity();

//            if (event.getPointerCount() > 1) {
//                temp.Xpixel2 = event.getX(1);
//                temp.Ypixel2 = event.getY(1);
//
//                if (event.getPointerCount() > 2) {
//                    temp.Xpixel3 = event.getX(2);
//                    temp.Ypixel3 = event.getY(2);
//                }
//            }

                mTempStroke.ListEvents.add(temp);
            }
            return;
        }
        catch (Exception exc) {
            String msg = exc.getMessage();
        }
    }

    public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {

    }

    protected void unRegisterSensors() {
        mSensorManager.unregisterListener(mSensorListenerGyro);
        mSensorManager.unregisterListener(mSensorListenerAcc);
    }
}
