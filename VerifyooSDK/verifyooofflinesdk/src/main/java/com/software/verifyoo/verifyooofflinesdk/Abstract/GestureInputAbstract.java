package com.software.verifyoo.verifyooofflinesdk.Abstract;

import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.graphics.Path;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;

import com.software.verifyoo.verifyooofflinesdk.R;
import com.software.verifyoo.verifyooofflinesdk.Utils.Consts;

import java.util.ArrayList;

/**
 * Created by roy on 12/28/2015.
 */
public abstract class GestureInputAbstract extends ActionBarActivity {
    protected static GestureDrawProcessorAbstract mGesturesProcessor;
    protected static GestureOverlayView mOverlay;

    protected ArrayList<Float> mListX;
    protected ArrayList<Float> mListY;

    protected int mCount;
    protected final int GESTURE_TRAIL_SIZE = 30;

    public boolean IsShowTrail = false;

    protected void clearOverlay() {
        mOverlay.setFadeOffset(Consts.FADE_INTERVAL_CLEAR);
        mOverlay.clear(true);
        mOverlay.setFadeOffset(Consts.FADE_INTERVAL);
    }

    protected void init(GestureDrawProcessorAbstract gesturesProcessor) {
        mGesturesProcessor = gesturesProcessor;
        mListX = new ArrayList<>();
        mListY = new ArrayList<>();

        try {
//            Runnable runnable = new Runnable() {
//                @Override
//                public void run() {
//                    clearOverlay();
//                    handler.postDelayed(this, 500);
//                }
//            };
//
//            handler.postDelayed(runnable, 100);

            mOverlay = (GestureOverlayView) findViewById(R.id.gesturesOverlay);
            mOverlay.addOnGestureListener(mGesturesProcessor);
            mOverlay.setFadeOffset(Consts.FADE_INTERVAL);
            mOverlay.setBackgroundColor(Color.rgb(44, 44, 44));

            if (IsShowTrail) {
                mOverlay.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        mListX.add(event.getX());
                        mListY.add(event.getY());

                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            mOverlay.cancelClearAnimation();
                            mListX.clear();
                            mListY.clear();
                        }
                        else {
                            if(mListX.size() > GESTURE_TRAIL_SIZE) {

                                mListX.remove(0);
                                mListY.remove(0);
                                Path path = new Path();

                                path.moveTo(mListX.get(0), mListY.get(0));

                                for(int idx = 1; idx < mListX.size(); idx++) {
                                    path.lineTo(mListX.get(idx), mListY.get(idx));
                                }
                                mOverlay.getGesturePath().set(path);
                            }
                        }
                        return true;
                    }
                });
            }

            mGesturesProcessor.init(getApplicationContext(), mOverlay);
        } catch (Exception exc) {
            String s = exc.getMessage();
        }
    }
}
