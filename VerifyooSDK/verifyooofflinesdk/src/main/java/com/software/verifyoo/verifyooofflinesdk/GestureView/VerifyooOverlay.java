package com.software.verifyoo.verifyooofflinesdk.GestureView;

import android.content.Context;
import android.gesture.GestureOverlayView;
import android.graphics.Canvas;

/**
 * Created by roy on 6/2/2016.
 */
public class VerifyooOverlay extends GestureOverlayView {
    public VerifyooOverlay(Context context) {
        super(context);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

//        if (mCurrentGesture != null && mGestureVisible) {
//            canvas.drawPath(mPath, mGesturePaint);
//        }
    }
}
