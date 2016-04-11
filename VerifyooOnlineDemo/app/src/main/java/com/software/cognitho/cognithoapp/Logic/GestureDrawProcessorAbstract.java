package com.software.cognitho.cognithoapp.Logic;

import android.content.Context;
import android.gesture.GestureOverlayView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.VelocityTracker;

import com.software.cognitho.cognithoapp.Objects.MotionEventCompact;
import com.software.cognitho.cognithoapp.Objects.Stroke;

/**
 * Created by roy on 9/15/2015.
 */
public abstract class GestureDrawProcessorAbstract implements GestureOverlayView.OnGestureListener {

    private static VelocityTracker _velocityTracker;
    protected Stroke _tempStroke;
    private Context _applicationContext;
    private SensorManager _sensorManager;
    private Sensor _accelerometer;
    private SensorEventListener _sensorListener;

    private MotionEventCompact mDownEvent;

    private float _accX, _accY, _accZ;

    public Stroke getStroke() {
        return _tempStroke;
    }

    public void clearStroke() {
        _tempStroke = new Stroke();
    }

    public void init(Context applicationContext) {
        _tempStroke = new Stroke();
        _applicationContext = applicationContext;
        _velocityTracker = VelocityTracker.obtain();

        _sensorManager = (SensorManager)_applicationContext.getSystemService(_applicationContext.SENSOR_SERVICE);
        _accelerometer = _sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        _sensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                Sensor mySensor = sensorEvent.sensor;

                if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    _accX = sensorEvent.values[0];
                    _accY = sensorEvent.values[1];
                    _accZ = sensorEvent.values[2];
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {

        try {
            MotionEventCompact temp;

            _velocityTracker.addMovement(event);
            _velocityTracker.computeCurrentVelocity(1000);

            temp = new MotionEventCompact(event);
            temp.AngleY = _accY;
            temp.AngleX = _accX;
            temp.AngleZ = _accZ;

            temp.VelocityX = _velocityTracker.getXVelocity();
            temp.VelocityY = _velocityTracker.getYVelocity();

            _tempStroke.ListEvents.add(temp);

            _sensorManager.registerListener(_sensorListener, _accelerometer, SensorManager.SENSOR_DELAY_GAME);
        } catch (Exception exc) {
            String msg = exc.getMessage();
        }

    }

    public void onGesture(GestureOverlayView overlay, MotionEvent event) {
        MotionEventCompact temp;

        _velocityTracker.addMovement(event);
        _velocityTracker.computeCurrentVelocity(1000);

        if (event.getHistorySize() > 1) {
            for (int idx = 0; idx < event.getHistorySize(); idx++) {
                temp = new MotionEventCompact();

                temp.X = event.getHistoricalX(idx);
                temp.Y = event.getHistoricalY(idx);
                temp.EventTime = event.getHistoricalEventTime(idx);
                temp.Pressure = event.getHistoricalPressure(idx);
                temp.TouchSurface = event.getHistoricalSize(idx);
                temp.AngleY = _accY;
                temp.AngleX = _accX;
                temp.AngleZ = _accZ;

                temp.VelocityX = _velocityTracker.getXVelocity();
                temp.VelocityY = _velocityTracker.getYVelocity();

                temp.PointerCount = event.getPointerCount();

                _tempStroke.ListEvents.add(temp);
            }

            temp = new MotionEventCompact(event);
            temp.AngleY = _accY;
            temp.AngleX = _accX;
            temp.AngleZ = _accZ;

            temp.VelocityX = _velocityTracker.getXVelocity();
            temp.VelocityY = _velocityTracker.getYVelocity();

            _tempStroke.ListEvents.add(temp);
        }
        else {
            temp = new MotionEventCompact(event);
            temp.AngleY = _accY;
            temp.AngleX = _accX;
            temp.AngleZ = _accZ;

            temp.VelocityX = _velocityTracker.getXVelocity();
            temp.VelocityY = _velocityTracker.getYVelocity();

            _tempStroke.ListEvents.add(temp);
        }
    }

    public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {

    }

    protected void unRegisterSensors() {
        _sensorManager.unregisterListener(_sensorListener);
    }
}