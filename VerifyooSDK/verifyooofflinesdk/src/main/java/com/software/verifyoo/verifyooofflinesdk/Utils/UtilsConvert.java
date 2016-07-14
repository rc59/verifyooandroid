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
                instruction = ConstsInstructions.INSTRUCTION_STRING_A;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_BLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_B;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_ELETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_E;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_GLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_G;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_KLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_K;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_MLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_M;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_NLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_N;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_RLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_R;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_WLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_W;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_ZLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_Z;
                break;
        }

        return instruction;
    }
}

