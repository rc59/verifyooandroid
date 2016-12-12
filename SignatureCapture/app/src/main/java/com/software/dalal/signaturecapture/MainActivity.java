package com.software.dalal.signaturecapture;

import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.software.dalal.signaturecapture.ApiMgr.ApiMgr;
import com.software.dalal.signaturecapture.Models.ExpMotionEventCompact;
import com.software.dalal.signaturecapture.Models.ExpShape;
import com.software.dalal.signaturecapture.Models.ExpStroke;

public class MainActivity extends AppCompatActivity {

    private Button mBtnAuth;
    private Button mBtnClear;

    protected ExpStroke mTempStroke;
    protected ExpShape mTempGesture;

    protected static GestureDrawProcessor mGesturesProcessor;
    protected GestureOverlayView mOverlay;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        mTempGesture = new ExpShape();

        int colorBlue = Color.parseColor(Consts.VERIFYOO_BLUE);
        int colorGray = Color.parseColor(Consts.VERIFYOO_GRAY);

        mGesturesProcessor = new GestureDrawProcessor();

        mOverlay = (GestureOverlayView) findViewById(R.id.gesturesOverlay);
        mOverlay.addOnGestureListener(mGesturesProcessor);
        mOverlay.setGestureStrokeWidth(8);
        mOverlay.setFadeOffset(Consts.FADE_INTERVAL);

        mBtnAuth = (Button) findViewById(R.id.btnAuth);
        mBtnAuth.setBackgroundColor(colorBlue);

        mBtnClear = (Button) findViewById(R.id.btnClear);
        mBtnClear.setBackgroundColor(colorGray);

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
    }

    private void storeGesture() {
        ApiMgr apiMgr = new ApiMgr();
        apiMgr.storeData(mTempGesture);
    }

    private void onClickAuth() {
        storeGesture();
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
