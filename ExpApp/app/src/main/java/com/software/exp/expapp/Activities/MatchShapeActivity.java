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

import com.software.exp.expapp.Logic.Consts;
import com.software.exp.expapp.Logic.ExpShape;
import com.software.exp.expapp.Logic.MotionEventCompact;
import com.software.exp.expapp.Logic.Stroke;
import com.software.exp.expapp.Logic.Tools;
import com.software.exp.expapp.MainActivity;
import com.software.exp.expapp.R;

import java.util.ArrayList;

import flexjson.JSONSerializer;

public class MatchShapeActivity extends Activity {

    boolean mShowIcon = true;
    boolean mShowIconFirstTime = true;

    private String mInstruction;

    private Button mBtnRestart;
    private Button mBtnMatch;

    private static GesturesProcessor mGesturesProcessor;
    private static GestureOverlayView mOverlay;

    private static ArrayList<Stroke> mShape = new ArrayList<Stroke>();
    private Stroke mTempStroke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_shape);
        init();
    }

    private int getIconDisplayTime() {
        if (mShowIconFirstTime) {
            return Consts.TOUCH_ICON_INTERVAL;
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
        mOverlay = (GestureOverlayView) findViewById(R.id.gestures_overlay);
        setColorInput();

        mShape.clear();
        mBtnMatch = (Button) findViewById(R.id.btnMatch);
        mBtnRestart = (Button) findViewById(R.id.btnRestart);


        mBtnMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickMatch();
            }
        });

        mBtnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRestart();
            }
        });


        mTempStroke = new Stroke();

        mOverlay.setFadeOffset(Consts.FADE_INTERVAL);

        mGesturesProcessor = new GesturesProcessor();
        mOverlay.addOnGestureListener(mGesturesProcessor);

        mInstruction = getIntent().getStringExtra(Consts.INSTRUCTION);

        TextView label = (TextView) findViewById(R.id.lblStatus);
        label.setText(String.format("%s - %s", mInstruction, getString(R.string.repeatOriginal)));
        label.setTextColor(Color.BLUE);

        setTitle(mInstruction);
        mBtnMatch.setEnabled(false);
    }

    private void onClickRestart() {
        clearOverlay();
        mTempStroke = new Stroke();
        mShape.clear();
        mBtnMatch.setEnabled(false);
        mShowIcon = true;
        setColorInput();
    }

    private void onClickMatch() {
        setColorNoInput();
        Intent intent = new Intent(getApplication(), MatchingActivity.class);

        JSONSerializer serializer = new JSONSerializer();

        String serviceName = Context.TELEPHONY_SERVICE;
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(serviceName);
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        String numGamesStr = getIntent().getStringExtra(Consts.NUM_GAMES);
        String username = getIntent().getStringExtra(Consts.USERNAME);

        int numGames = (Integer.valueOf(numGamesStr));
        numGames++;

        ExpShape shape = new ExpShape(telephonyManager, wm, mInstruction);
        shape.Name = username;
        shape.Strokes = mShape;

        Tools.prepareShapeForSerialize(shape);

        String jsonShape = serializer.deepSerialize(shape);

        Tools.jsonShape = jsonShape;

        startActivity(intent);
    }

    private void clearOverlay() {
        mOverlay.setFadeOffset(Consts.FADE_INTERVAL_CLEAR);
        mOverlay.clear(true);
        mOverlay.setFadeOffset(Consts.FADE_INTERVAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_match_shape, menu);
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
            setColorInputing();
            mSensorManager.registerListener(sensorListener, accelerometer, SensorManager.SENSOR_DELAY_GAME);
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

                mShape.add(mTempStroke);
                mTempStroke = new Stroke();
                mBtnMatch.setEnabled(true);
            }
        }

        public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {

        }
    }
}
