package com.software.exp.expapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.software.exp.expapp.Logic.ApiSaveAndMatch;
import com.software.exp.expapp.Logic.Consts;
import com.software.exp.expapp.Logic.ExpShape;
import com.software.exp.expapp.Logic.MotionEventCompact;
import com.software.exp.expapp.Logic.Stroke;
import com.software.exp.expapp.Logic.Tools;
import com.software.exp.expapp.MainActivity;
import com.software.exp.expapp.R;

import java.util.ArrayList;

import flexjson.JSONSerializer;


public class SourceShapeActivity extends Activity {

    boolean mShowIcon = true;
    boolean mShowIconFirstTime = true;

    private String mInstruction;

    private static TextView mLblStatus;

    private static GesturesProcessor mGesturesProcessor;
    private static GestureOverlayView mOverlay;

    private Button mBtnRestart;
    private Button mBtnSave;

    private static ArrayList<Stroke> mShape = new ArrayList<Stroke>();
    private static ArrayList<Stroke> mShapeVerify = new ArrayList<Stroke>();

    private boolean mIsGestureAccepted = false;

    private Stroke mTempStroke;

    private String mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_shape);
        init();
    }

    private int getIconDisplayTime() {
        if (mShowIconFirstTime) {
            return Consts.TOUCH_ICON_INTERVAL_FIRST_LONG;
        }
        else {
            return Consts.TOUCH_ICON_INTERVAL;
        }
    }

    private void setColorInput(){
        mOverlay.setEnabled(true);
        mOverlay.setBackgroundColor(Color.rgb(200, 200, 200));

        if(mShowIcon) {
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

    private void setColorNoInput(){
        mOverlay.setBackgroundColor(Color.rgb(120, 120, 120));
        mOverlay.setEnabled(false);
    }

    private void setColorInputing(){
        mOverlay.setBackgroundColor(Color.rgb(190, 190, 190));
    }

    private void init() {
        mUserName = getIntent().getStringExtra(Consts.USERNAME);

        mOverlay = (GestureOverlayView) findViewById(R.id.gestures_overlay);
        setColorInput();

        mLblStatus = (TextView) findViewById(R.id.lblStatus);
        mLblStatus.setTextColor(Color.BLUE);

        mShape.clear();

        mBtnSave = (Button) findViewById(R.id.btnSave);
        mBtnRestart = (Button) findViewById(R.id.btnRestart);
        mTempStroke = new Stroke();

        //mBtnSave.setBackgroundColor(Color.LTGRAY);
        //mBtnRestart.setBackgroundColor(Color.LTGRAY);

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

        mInstruction = getIntent().getStringExtra(Consts.INSTRUCTION);

        setTitle(mInstruction);
        mLblStatus.setText(mInstruction);
        mBtnSave.setEnabled(false);
    }

    private void onClickRestart() {
        mShowIcon = true;
        mShapeVerify = new ArrayList<Stroke>();
        mLblStatus.setText(mInstruction);
        mLblStatus.setTextColor(Color.BLUE);
        mIsGestureAccepted = false;
        clearOverlay();
        mTempStroke = new Stroke();
        mShape.clear();
        mShapeVerify.clear();
        mBtnSave.setText(R.string.btnNext);
        mBtnSave.setEnabled(false);
        //mBtnRestart.setBackgroundColor(Color.LTGRAY);
        //mBtnSave.setBackgroundColor(Color.LTGRAY);

        setColorInput();
    }

    private void onClickSave() {
        if (mIsGestureAccepted) {
            Intent intentPrev = getIntent();
            String id = intentPrev.getStringExtra(Consts.USERNAME);

            Intent intent = new Intent(getApplication(), SavingShapeActivity.class);

            String serviceName = Context.TELEPHONY_SERVICE;
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(serviceName);
            WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

            ExpShape shape = new ExpShape(telephonyManager, wm, mInstruction);
            shape.Name = id;
            shape.Strokes = mShape;

            JSONSerializer serializer = new JSONSerializer();
            String jsonShape = serializer.deepSerialize(shape);

            Tools.jsonShape = jsonShape;
            Tools.Username = id;
            startActivity(intent);
        } else {
            mShowIcon = true;
            mLblStatus.setText(getString(R.string.drawAgain));
            mIsGestureAccepted = true;
            clearOverlay();
            mBtnSave.setText(R.string.btnSave);
            mBtnSave.setEnabled(false);
            setColorInput();
            //mBtnSave.setBackgroundColor(Color.LTGRAY);
        }
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        if (id == R.id.action_exit) {
            int p = android.os.Process.myPid();
            android.os.Process.killProcess(p);
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
            mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
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
            }
            else {
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
            Gesture gesture  = overlay.getGesture();

            int strokesCount = gesture.getStrokes().size();
            if (strokesCount > 1) {
                gesture.getStrokes().remove(0);
            }

            if (gesture.getLength() > Consts.LENGTH_THRESHOLD) {
                mTempStroke.Length = gesture.getLength();

                if (mIsGestureAccepted) {
                    mBtnSave.setEnabled(false);

                    mShapeVerify.add(mTempStroke);
                    mTempStroke = new Stroke();
                    checkIfShapesMatch();
                }
                else {
                    mShape.add(mTempStroke);
                    mTempStroke = new Stroke();
                    mBtnSave.setEnabled(true);
                    //mBtnSave.setBackgroundColor(Color.GREEN);
                }
            }
        }

        public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {

        }
    }

    private void checkIfShapesMatch() {
        if(mShape.size() == mShapeVerify.size()) {
            mBtnRestart.setEnabled(false);
            mLblStatus.setTextColor(Color.BLUE);
            mLblStatus.setText(getString(R.string.matchingShapes));
            setColorNoInput();

            for (int idx = 0; idx < mShapeVerify.size(); idx++) {
                mShape.add(mShapeVerify.get(idx));
            }

            JSONSerializer serializer = new JSONSerializer();

            String serviceName = Context.TELEPHONY_SERVICE;
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(serviceName);
            WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

            Intent intentPrev = getIntent();
            String id = intentPrev.getStringExtra(Consts.USERNAME);

            ExpShape shape = new ExpShape(telephonyManager, wm, mInstruction);
            shape.Name = id;
            shape.Strokes = mShape;

            Tools.prepareShapeForSerialize(shape);

            String jsonShape = serializer.deepSerialize(shape);
            ApiSaveAndMatch apiMatch = new ApiSaveAndMatch(getApplicationContext(), "selfMatch", mBtnSave, mLblStatus, mBtnRestart);
            apiMatch.runEx(jsonShape);
        }
    }
}