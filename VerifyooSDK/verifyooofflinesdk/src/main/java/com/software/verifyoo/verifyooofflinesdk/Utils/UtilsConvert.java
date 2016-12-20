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
            case ConstsInstructions.INSTRUCTION_CODE_BLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_B;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_DLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_D;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_ELETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_E;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_FLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_F;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_KLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_K;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_MLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_M;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_PLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_P;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_RLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_R;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_SLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_S;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_ALETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_A;
                break;


            case ConstsInstructions.INSTRUCTION_CODE_GETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_G;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_HLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_H;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_ILETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_I;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_JLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_J;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_QLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_Q;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_TLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_T;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_WLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_W;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_YLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_Y;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_ZLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_Z;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_NLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_N;
                break;

            case ConstsInstructions.INSTRUCTION_CODE_CLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_C;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_LLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_L;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_OLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_O;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_ULETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_U;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_VLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_V;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_XLETTER:
                instruction = ConstsInstructions.INSTRUCTION_STRING_X;
                break;
        }

        return instruction;
    }
}

