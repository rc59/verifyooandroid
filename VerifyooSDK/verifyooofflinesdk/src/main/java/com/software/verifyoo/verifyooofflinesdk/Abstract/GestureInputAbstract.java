package com.software.verifyoo.verifyooofflinesdk.Abstract;

import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;

import com.software.verifyoo.verifyooofflinesdk.R;
import com.software.verifyoo.verifyooofflinesdk.Utils.Consts;

/**
 * Created by roy on 12/28/2015.
 */
public abstract class GestureInputAbstract extends ActionBarActivity {
    protected static GestureDrawProcessorAbstract mGesturesProcessor;
    protected static GestureOverlayView mOverlay;

    private Handler handler = new Handler();
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
            mOverlay.setBackgroundColor(Color.rgb(33, 33, 33));

            Display display= getWindowManager().getDefaultDisplay();
            Bitmap bitmap = Bitmap.createBitmap(display.getWidth(), display.getHeight(), Bitmap.Config.ARGB_8888);

            mGesturesProcessor.init(getApplicationContext(), mOverlay, bitmap);
        } catch (Exception exc) {
            String s = exc.getMessage();
        }
    }
}
