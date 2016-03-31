package com.software.verifyoo.verifyooofflinesdk.Abstract;

import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;

import com.software.verifyoo.verifyooofflinesdk.R;
import com.software.verifyoo.verifyooofflinesdk.Utils.Consts;

/**
 * Created by roy on 12/28/2015.
 */
public abstract class GestureInputAbstract extends ActionBarActivity {
    protected static GestureDrawProcessorAbstract mGesturesProcessor;
    protected static GestureOverlayView mOverlay;

    public GestureInputAbstract() {

    }

    protected void clearOverlay() {
        mOverlay.setFadeOffset(Consts.FADE_INTERVAL_CLEAR);
        mOverlay.clear(true);
        mOverlay.setFadeOffset(Consts.FADE_INTERVAL);
    }

    protected void init(GestureDrawProcessorAbstract gesturesProcessor) {
        mGesturesProcessor = gesturesProcessor;

        try {
            mOverlay = (GestureOverlayView) findViewById(R.id.gesturesOverlay);
            mOverlay.addOnGestureListener(mGesturesProcessor);
            mOverlay.setFadeOffset(Consts.FADE_INTERVAL);
            mOverlay.setBackgroundColor(Color.rgb(33, 33, 33));
            mGesturesProcessor.init(getApplicationContext());
        } catch (Exception exc) {
            String s = exc.getMessage();
        }
    }
}
