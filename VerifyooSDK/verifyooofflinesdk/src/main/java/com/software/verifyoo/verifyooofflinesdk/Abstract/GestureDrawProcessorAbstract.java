package com.software.verifyoo.verifyooofflinesdk.Abstract;

import android.content.Context;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.VelocityTracker;

import com.software.verifyoo.verifyooofflinesdk.Utils.Consts;
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

    protected long mTotalGestureTime;
    Runnable mRunnable;
    private Handler handler = new Handler();

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
        mOverlay.setFadeOffset(Consts.FADE_INTERVAL);
        mSensorManager = (SensorManager) mApplicationContext.getSystemService(mApplicationContext.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        mOverlay.setGestureStrokeWidth(8);

        mSensorListenerAcc = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                try {
                    Sensor mySensor = sensorEvent.sensor;

                    if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                        mAccX = sensorEvent.values[0];
                        mAccY = sensorEvent.values[1];
                        mAccZ = sensorEvent.values[2];
                    }
                } catch (Exception exc) {

                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        mSensorListenerGyro = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                try {
                    Sensor mySensor = sensorEvent.sensor;

                    if (mySensor.getType() == Sensor.TYPE_GYROSCOPE) {
                        mGyroX = sensorEvent.values[0];
                        mGyroY = sensorEvent.values[1];
                        mGyroZ = sensorEvent.values[2];
                    }
                } catch (Exception exc) {

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
            UtilsGeneral.IsGesturing = true;

            handler.removeCallbacks(mRunnable);
            handler.postDelayed(mRunnable, mTotalGestureTime);

            if (UtilsGeneral.AuthStartTime == 0) {
                UtilsGeneral.AuthStartTime = new Date().getTime();
            }
            MotionEventCompact temp;

            mVelocityTracker.addMovement(event);
            mVelocityTracker.computeCurrentVelocity(1000);

            temp = UtilsConvert.ConvertMotionEvent(event);

            temp.SetAccelerometerX(mAccX);
            temp.SetAccelerometerY(mAccY);
            temp.SetAccelerometerZ(mAccZ);

            temp.SetGyroX(mGyroX);
            temp.SetGyroY(mGyroY);
            temp.SetGyroZ(mGyroZ);

            temp.SetVelocityX(mVelocityTracker.getXVelocity());
            temp.SetVelocityY(mVelocityTracker.getYVelocity());

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

                    temp.SetAccelerometerX(mAccX);
                    temp.SetAccelerometerY(mAccY);
                    temp.SetAccelerometerZ(mAccZ);

                    temp.SetGyroX(mGyroX);
                    temp.SetGyroY(mGyroY);
                    temp.SetGyroZ(mGyroZ);

                    temp.SetVelocityX(mVelocityTracker.getXVelocity());
                    temp.SetVelocityY(mVelocityTracker.getYVelocity());

                    //temp.PointerCount = event.getPointerCount();

                    mTempStroke.ListEvents.add(temp);
                }

                temp = UtilsConvert.ConvertMotionEvent(event);

                temp.SetAccelerometerX(mAccX);
                temp.SetAccelerometerY(mAccY);
                temp.SetAccelerometerZ(mAccZ);

                temp.SetGyroX(mGyroX);
                temp.SetGyroY(mGyroY);
                temp.SetGyroZ(mGyroZ);

                temp.SetVelocityX(mVelocityTracker.getXVelocity());
                temp.SetVelocityY(mVelocityTracker.getYVelocity());

                mTempStroke.ListEvents.add(temp);
            } else {
                temp = UtilsConvert.ConvertMotionEvent(event);

                temp.SetAccelerometerX(mAccX);
                temp.SetAccelerometerY(mAccY);
                temp.SetAccelerometerZ(mAccZ);

                temp.SetGyroX(mGyroX);
                temp.SetGyroY(mGyroY);
                temp.SetGyroZ(mGyroZ);

                temp.SetVelocityX(mVelocityTracker.getXVelocity());
                temp.SetVelocityY(mVelocityTracker.getYVelocity());

                mTempStroke.ListEvents.add(temp);
            }
            return;
        }
        catch (Exception exc) {
            String msg = exc.getMessage();
        }
    }

    protected float getGestureWidth(float pressure) {
        float width = 0;

        float minPressure = (float) 0.2;
        float maxPressure = (float) 0.7;

        if(pressure >= maxPressure) {
            width = 15;
        }
        if(pressure <= minPressure) {
            width = 3;
        }
        if(pressure > minPressure && pressure < maxPressure) {
            float diffPressure = pressure - minPressure;
            float interval = maxPressure - minPressure;
            float percentage = diffPressure / interval;

            width = (12 * percentage + 3);
        }

        return width;
    }


    public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {

    }

    protected void unRegisterSensors() {
        try {
            mSensorManager.unregisterListener(mSensorListenerGyro);
            mSensorManager.unregisterListener(mSensorListenerAcc);
        } catch (Exception exc) {

        }
    }

    public void setGestureTime(long gestureTime) {
        mTotalGestureTime = gestureTime;
    }

    public void setRunnable(Runnable runnable) {
        mRunnable = runnable;
    }
}
