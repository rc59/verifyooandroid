package com.software.exp.expapp1.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.software.exp.expapp1.Logic.Consts;
import com.software.exp.expapp1.Logic.ExpShape;
import com.software.exp.expapp1.Logic.SaveRequest;
import com.software.exp.expapp1.Logic.Shapes;
import com.software.exp.expapp1.Logic.ShapesType;
import com.software.exp.expapp1.Logic.MotionEventCompact;
import com.software.exp.expapp1.Logic.Stroke;
import com.software.exp.expapp1.Logic.Tools;
import com.software.exp.expapp1.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SourceShapeActivity extends Activity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    static final int numberOfShapes = ShapesType.ShapesTypeEnum.values().length - 1;
    static final int numberOfLoops = 6;

    private int shapeCounter = 0;
    private int loopCounter = 0;

    boolean mShowIcon = true;
    boolean mShowIconFirstTime = true;

    private static GesturesProcessor mGesturesProcessor;
    private static GestureOverlayView mOverlay;

    @Bind(R.id.btnRestart)
    Button mBtnRestart;
    @Bind(R.id.btnSave)
    Button mBtnSave;
    @Bind(R.id.lblStatus)
    TextView mLblStatus;

    private static ArrayList<Stroke> mShape = new ArrayList<Stroke>();


    private Stroke mTempStroke;

    private Shapes shapes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_shape);
        ButterKnife.bind(this);
        init();
    }

    private int getIconDisplayTime() {
        if (mShowIconFirstTime) {
            return Consts.TOUCH_ICON_INTERVAL_FIRST_LONG;
        } else {
            return Consts.TOUCH_ICON_INTERVAL;
        }
    }

    private void setColorInput() {
        mOverlay.setEnabled(true);
        mOverlay.setBackgroundColor(Color.rgb(200, 200, 200));

        if (mShowIcon) {
            mShowIcon = false;
            mOverlay.setBackgroundResource(R.drawable.touchicon);

            new CountDownTimer(getIconDisplayTime(), getIconDisplayTime()) {

                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    mOverlay.setBackgroundResource(R.drawable.empty);
                    mOverlay.setBackgroundColor(Color.rgb(200, 200, 200));
                }
            }.start();
            mShowIconFirstTime = false;
        }
    }

    private void setColorNoInput() {
        mOverlay.setBackgroundColor(Color.rgb(120, 120, 120));
        mOverlay.setEnabled(false);
    }

    private void setColorInputing() {
        mOverlay.setBackgroundColor(Color.rgb(190, 190, 190));
    }

    private void init() {

        setLoopTitle();

        mLblStatus.setText(ShapesType.getTitle(0, this));
        mLblStatus.setTextColor(Color.BLUE);

        Intent intentPrev = getIntent();
        String mUserName = intentPrev.getStringExtra(Consts.USERNAME);

        String serviceName = Context.TELEPHONY_SERVICE;
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(serviceName);
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String user = prefs.getString(Consts.USERNAME, "");

        shapes = new Shapes(telephonyManager, wm, metrics);
        shapes.Name = user;


        mOverlay = (GestureOverlayView) findViewById(R.id.gestures_overlay);
        setColorInput();

        mShape.clear();

        mTempStroke = new Stroke();

        mOverlay.setFadeOffset(Consts.FADE_INTERVAL);

        mGesturesProcessor = new GesturesProcessor();
        mOverlay.addOnGestureListener(mGesturesProcessor);
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSave();
            }
        });
        mBtnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRestart();
            }
        });
        mBtnSave.setEnabled(false);
    }

    private void onClickRestart() {

        mBtnSave.setEnabled(false);
        clearOverlay();
        setColorInput();
        mShape.clear();
    }

    private void onClickSave() {

        if (shapeCounter == numberOfShapes) {
            shapeCounter = 0;
            loopCounter++;
            if (numberOfLoops != loopCounter) {
                setLoopTitle();
            }
            //start again the next loop
        } else {
            shapeCounter++;
        }

        if (numberOfLoops == loopCounter) {
            //stop and send the request
            shapeCounter--;
            addNewShape();
            Gson gson = new Gson();
            String jsonShapes = gson.toJson(shapes);
            SaveRequest apiMatch = new SaveRequest(getApplicationContext(), mBtnSave, mLblStatus, mBtnRestart);
            apiMatch.runEx(jsonShapes);
            Intent intent = new Intent(getApplicationContext(), SavingShapeActivity.class);
            startActivity(intent);
            return;
        }

        addNewShape();
        clearOverlay();
        setColorInput();
        mShape.clear();

        mLblStatus.setText(ShapesType.getTitle(shapeCounter, this));
    }

    private void addNewShape() {
        mBtnSave.setEnabled(false);
        ExpShape expShape = new ExpShape();
        expShape.Instruction = ShapesType.getInstructionName(loopCounter - 1);
        expShape.Strokes = mShape;
        shapes.ExpShapeList.add(expShape);
    }

    private void clearOverlay() {
        mOverlay.setFadeOffset(Consts.FADE_INTERVAL_CLEAR);
        mOverlay.clear(true);
        mOverlay.setFadeOffset(Consts.FADE_INTERVAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_source_shape, menu);
        setLoopTitle();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_home) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    public class GesturesProcessor implements GestureOverlayView.OnGestureListener {

        private VelocityTracker mVelocityTracker;
        private SensorManager mSensorManager;
        Sensor accelerometer;
        SensorEventListener sensorListener;

        private float mAccX, mAccY, mAccZ;

        public GesturesProcessor() {
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            mVelocityTracker = VelocityTracker.obtain();
            accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            sensorListener = new SensorEventListener() {
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

        public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
            mSensorManager.registerListener(sensorListener, accelerometer, SensorManager.SENSOR_DELAY_GAME);
            setColorInputing();
        }

        public void onGesture(GestureOverlayView overlay, MotionEvent event) {
            MotionEventCompact temp;

            mVelocityTracker.addMovement(event);
            mVelocityTracker.computeCurrentVelocity(1000);

            if (event.getHistorySize() > 1) {
                for (int idx = 0; idx < event.getHistorySize(); idx++) {
                    temp = new MotionEventCompact();
                    temp.X = event.getHistoricalX(idx);
                    temp.Y = event.getHistoricalY(idx);
                    temp.EventTime = event.getHistoricalEventTime(idx);
                    temp.Pressure = event.getHistoricalPressure(idx);
                    temp.TouchSurface = event.getHistoricalSize(idx);
                    temp.AngleY = mAccY;
                    temp.AngleX = mAccX;
                    temp.AngleZ = mAccZ;

                    temp.VelocityX = mVelocityTracker.getXVelocity();
                    temp.VelocityY = mVelocityTracker.getXVelocity();
                    temp.Velocity = Tools.pitagoras(temp.VelocityX, temp.VelocityY);

                    mTempStroke.ListEvents.add(temp);
                }

                temp = new MotionEventCompact(event);
                temp.AngleY = mAccY;
                temp.AngleX = mAccX;
                temp.AngleZ = mAccZ;

                temp.VelocityX = mVelocityTracker.getXVelocity();
                temp.VelocityY = mVelocityTracker.getXVelocity();
                temp.Velocity = Tools.pitagoras(temp.VelocityX, temp.VelocityY);

                mTempStroke.ListEvents.add(temp);
            } else {
                temp = new MotionEventCompact(event);
                temp.AngleY = mAccY;
                temp.AngleX = mAccX;
                temp.AngleZ = mAccZ;

                temp.VelocityX = mVelocityTracker.getXVelocity();
                temp.VelocityY = mVelocityTracker.getXVelocity();
                temp.Velocity = Tools.pitagoras(temp.VelocityX, temp.VelocityY);

                mTempStroke.ListEvents.add(temp);
            }
        }

        public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {

            setColorInput();
            mSensorManager.unregisterListener(sensorListener);
            Gesture gesture = overlay.getGesture();

            int strokesCount = gesture.getStrokes().size();
            if (strokesCount > 1) {
                gesture.getStrokes().remove(0);
            }

            if (gesture.getLength() > Consts.LENGTH_THRESHOLD) {
                mTempStroke.Length = gesture.getLength();
                mShape.add(mTempStroke);
                mTempStroke = new Stroke();
                mBtnSave.setEnabled(true);
            }
        }

        public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {

        }
    }

    @Override
    public void onBackPressed() {
        // your code.
    }

    private void setLoopTitle() {
        setTitle(loopCounter + 1 + "/" + numberOfLoops);
    }
}