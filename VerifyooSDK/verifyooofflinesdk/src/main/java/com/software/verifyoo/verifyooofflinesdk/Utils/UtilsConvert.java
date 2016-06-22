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
                instruction = "A";
                break;
            case ConstsInstructions.INSTRUCTION_CODE_EIGHT:
                instruction = "8";
                break;
            case ConstsInstructions.INSTRUCTION_CODE_FIVE:
                instruction = "5";
                break;
            case ConstsInstructions.INSTRUCTION_CODE_HEART:
                instruction = "♡";
                break;
            case ConstsInstructions.INSTRUCTION_CODE_RLETTER:
                instruction = "R";
                break;
            case ConstsInstructions.INSTRUCTION_CODE_TRIANGULAR:
                instruction = "Δ";
                break;
            case ConstsInstructions.INSTRUCTION_CODE_BLETTER:
                instruction = "B";
                break;
            case ConstsInstructions.INSTRUCTION_CODE_GLETTER:
                instruction = "G";
                break;
            case ConstsInstructions.INSTRUCTION_CODE_TWO:
                instruction = "2";
                break;
        }

        return instruction;
    }
}

