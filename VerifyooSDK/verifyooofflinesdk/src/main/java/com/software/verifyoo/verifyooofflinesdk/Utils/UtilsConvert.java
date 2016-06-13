package com.software.verifyoo.verifyooofflinesdk.Utils;

import android.view.MotionEvent;

import Data.UserProfile.Raw.MotionEventCompact;

/**
 * Created by roy on 3/30/2016.
 */
public class UtilsConvert {
    public static MotionEventCompact ConvertMotionEvent(MotionEvent event) {
        MotionEventCompact tempEvent = new MotionEventCompact();
        tempEvent.Xpixel = event.getX();
        tempEvent.Ypixel = event.getY();
        tempEvent.EventTime = event.getEventTime();
        tempEvent.Pressure = event.getPressure();
        tempEvent.TouchSurface = event.getSize();

        return tempEvent;
    }

    public static String InstructionCodeToInstruction(String instructionCode) {
        String instruction = "";
        switch (instructionCode) {
            case ConstsInstructions.INSTRUCTION_CODE_ALETTER:
                instruction = "Letter A";
                break;
            case ConstsInstructions.INSTRUCTION_CODE_EIGHT:
                instruction = "Digit 8";
                break;
            case ConstsInstructions.INSTRUCTION_CODE_FIVE:
                instruction = "Digit 5";
                break;
            case ConstsInstructions.INSTRUCTION_CODE_HEART:
                instruction = "Heart";
                break;
            case ConstsInstructions.INSTRUCTION_CODE_RLETTER:
                instruction = "Letter R";
                break;
            case ConstsInstructions.INSTRUCTION_CODE_TRIANGULAR:
                instruction = "Triangle";
                break;
        }

        return instruction;
    }
}

