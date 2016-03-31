package com.software.verifyoo.verifyooofflinesdk.Utils;

import android.view.MotionEvent;
import VerifyooLogic.UserProfile.MotionEventCompact;

/**
 * Created by roy on 3/30/2016.
 */
public class UtilsConvert {
    public static MotionEventCompact ConvertMotionEvent(MotionEvent event) {
        MotionEventCompact tempEvent = new MotionEventCompact(event.getX(), event.getY(), event.getRawX(), event.getRawY(), event.getEventTime(), event.getPressure(), event.getSize());
        return tempEvent;
    }
}

