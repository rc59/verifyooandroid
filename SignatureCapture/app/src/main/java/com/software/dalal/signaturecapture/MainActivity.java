package com.software.dalal.signaturecapture;

import android.content.pm.ActivityInfo;
import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.software.dalal.signaturecapture.ApiMgr.ApiMgr;
import com.software.dalal.signaturecapture.Models.ExpMotionEventCompact;
import com.software.dalal.signaturecapture.Models.ExpShape;
import com.software.dalal.signaturecapture.Models.ExpStroke;

public class MainActivity extends AppCompatActivity {

    private TextView mTxtStatus;
    private Button mBtnAuth;
    private Button mBtnReg;
    private Button mBtnClear;

    protected ExpStroke mTempStroke;
    protected ExpShape mTempGesture;

    protected static GestureDrawProcessor mGesturesProcessor;
    protected GestureOverlayView mOverlay;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        clearOverlay();
        mTxtStatus.setVisibility(View.INVISIBLE);
    }

    private void init() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mTempGesture = new ExpShape();

        int colorBlue = Color.parseColor(Consts.VERIFYOO_BLUE);
        int colorGray = Color.parseColor(Consts.VERIFYOO_GRAY);

        mGesturesProcessor = new GestureDrawProcessor();

        mOverlay = (GestureOverlayView) findViewById(R.id.gesturesOverlay);
        mOverlay.addOnGestureListener(mGesturesProcessor);
        mOverlay.setGestureStrokeWidth(8);
        mOverlay.setFadeOffset(Consts.FADE_INTERVAL);

        mTxtStatus = (TextView) findViewById(R.id.txtSaving);

        mBtnAuth = (Button) findViewById(R.id.btnAuth);
        mBtnReg = (Button) findViewById(R.id.btnReg);
        mBtnClear = (Button) findViewById(R.id.btnClear);

        mBtnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onClickAuth();
                } catch (Exception exc) {
                    handleGeneralError(exc);
                }
            }
        });

        mBtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onClickReg();
                } catch (Exception exc) {
                    handleGeneralError(exc);
                }
            }
        });

        mBtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onClickClear();
                }
                catch (Exception exc) {
                    handleGeneralError(exc);
                }
            }
        });
    }

    private void onClickReg() {
        mTxtStatus.setVisibility(View.VISIBLE);
        storeGesture(false);
    }

    private void handleGeneralError(Exception exc) {
    }

    private void onClickClear() {
        mTempGesture = new ExpShape();
        clearOverlay();
    }

    protected void clearOverlay() {
        mOverlay.setFadeOffset(Consts.FADE_INTERVAL_CLEAR);
        mOverlay.clear(true);
        mOverlay.setFadeOffset(Consts.FADE_INTERVAL);
        mTxtStatus.setVisibility(View.INVISIBLE);
    }

    private void storeGesture(boolean isAuth) {
        mTxtStatus.setText("Saving...");
        ApiMgr apiMgr = new ApiMgr(getApplicationContext(), mTxtStatus);
        apiMgr.storeData(mTempGesture, isAuth);
        clearOverlay();
        showLoading();
    }

    private void showLoading() {
        mTxtStatus.setVisibility(View.VISIBLE);
    }

    private void onClickAuth() {
        mTxtStatus.setVisibility(View.VISIBLE);
        storeGesture(true);
    }

    public class GestureDrawProcessor implements GestureOverlayView.OnGestureListener {

        @Override
        public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
            mTempStroke = new ExpStroke();
            ExpMotionEventCompact tempEvent = new ExpMotionEventCompact(event);
            mTempStroke.ListEvents.add(tempEvent);
        }

        @Override
        public void onGesture(GestureOverlayView overlay, MotionEvent event) {
            ExpMotionEventCompact tempEvent = new ExpMotionEventCompact(event);
            mTempStroke.ListEvents.add(tempEvent);
        }

        @Override
        public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
            ExpMotionEventCompact tempEvent = new ExpMotionEventCompact(event);
            mTempStroke.ListEvents.add(tempEvent);
            mTempGesture.Strokes.add(mTempStroke);
            mTempStroke = new ExpStroke();
        }

        @Override
        public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {

        }
    }
}