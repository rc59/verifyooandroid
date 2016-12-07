package com.software.verifyoo.verifyooofflinesdk.Utils;

/**
 * Created by roy on 1/14/2016.
 */
public class UtilsInstructions {
    public static String GetInstruction(int idx) {
        String instruction = ConstsInstructions.INSTRUCTION_CODE_BLETTER;

        switch (idx) {
            case 0:
                instruction = ConstsInstructions.INSTRUCTION_CODE_BLETTER;
                break;
            case 1:
                instruction = ConstsInstructions.INSTRUCTION_CODE_DLETTER;
                break;
            case 2:
                instruction = ConstsInstructions.INSTRUCTION_CODE_ELETTER;
                break;
            case 3:
                instruction = ConstsInstructions.INSTRUCTION_CODE_FLETTER;
                break;
            case 4:
                instruction = ConstsInstructions.INSTRUCTION_CODE_KLETTER;
                break;
            case 5:
                instruction = ConstsInstructions.INSTRUCTION_CODE_MLETTER;
                break;
            case 6:
                instruction = ConstsInstructions.INSTRUCTION_CODE_PLETTER;
                break;
            case 7:
                instruction = ConstsInstructions.INSTRUCTION_CODE_RLETTER;
                break;
            case 8:
                instruction = ConstsInstructions.INSTRUCTION_CODE_SLETTER;
                break;
            case 9:
                instruction = ConstsInstructions.INSTRUCTION_CODE_ALETTER;
                break;


            case 10:
                instruction = ConstsInstructions.INSTRUCTION_CODE_GETTER;
                break;
            case 11:
                instruction = ConstsInstructions.INSTRUCTION_CODE_HLETTER;
                break;
            case 12:
                instruction = ConstsInstructions.INSTRUCTION_CODE_ILETTER;
                break;
            case 13:
                instruction = ConstsInstructions.INSTRUCTION_CODE_JLETTER;
                break;
            case 14:
                instruction = ConstsInstructions.INSTRUCTION_CODE_QLETTER;
                break;
            case 15:
                instruction = ConstsInstructions.INSTRUCTION_CODE_TLETTER;
                break;
            case 16:
                instruction = ConstsInstructions.INSTRUCTION_CODE_WLETTER;
                break;
            case 17:
                instruction = ConstsInstructions.INSTRUCTION_CODE_YLETTER;
                break;
            case 18:
                instruction = ConstsInstructions.INSTRUCTION_CODE_ZLETTER;
                break;
            case 19:
                instruction = ConstsInstructions.INSTRUCTION_CODE_NLETTER;
                break;
        }

        return instruction;
    }

    public static int GetInstructionCodeIdx(String instruction) {

        int instructionIdx = -1;
        switch (instruction) {
            case ConstsInstructions.INSTRUCTION_CODE_BLETTER:
                instructionIdx = 0;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_DLETTER:
                instructionIdx = 1;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_ELETTER:
                instructionIdx = 2;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_FLETTER:
                instructionIdx = 3;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_KLETTER:
                instructionIdx = 4;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_MLETTER:
                instructionIdx = 5;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_PLETTER:
                instructionIdx = 6;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_RLETTER:
                instructionIdx = 7;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_SLETTER:
                instructionIdx = 8;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_ALETTER:
                instructionIdx = 9;
                break;

            case ConstsInstructions.INSTRUCTION_CODE_GETTER:
                instructionIdx = 10;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_HLETTER:
                instructionIdx = 11;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_ILETTER:
                instructionIdx = 12;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_JLETTER:
                instructionIdx = 13;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_QLETTER:
                instructionIdx = 14;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_TLETTER:
                instructionIdx = 15;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_WLETTER:
                instructionIdx = 16;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_YLETTER:
                instructionIdx = 17;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_ZLETTER:
                instructionIdx = 18;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_NLETTER:
                instructionIdx = 19;
                break;
        }

        return instructionIdx;
    }

    public static int GetInstructionIdx(String instructionStr) {

        int instructionIdx = -1;
        switch (instructionStr) {
            case ConstsInstructions.INSTRUCTION_STRING_B:
                instructionIdx = 0;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_D:
                instructionIdx = 1;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_E:
                instructionIdx = 2;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_F:
                instructionIdx = 3;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_K:
                instructionIdx = 4;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_M:
                instructionIdx = 5;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_P:
                instructionIdx = 6;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_R:
                instructionIdx = 7;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_S:
                instructionIdx = 8;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_A:
                instructionIdx = 9;
                break;

            case ConstsInstructions.INSTRUCTION_STRING_G:
                instructionIdx = 10;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_H:
                instructionIdx = 11;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_I:
                instructionIdx = 12;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_J:
                instructionIdx = 13;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_Q:
                instructionIdx = 14;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_T:
                instructionIdx = 15;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_W:
                instructionIdx = 16;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_Y:
                instructionIdx = 17;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_Z:
                instructionIdx = 18;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_N:
                instructionIdx = 19;
                break;
        }

        return instructionIdx;
    }
}
