package com.software.verifyoo.verifyooofflinesdk.Utils;

/**
 * Created by roy on 1/14/2016.
 */
public class UtilsInstructions {
    public static String GetInstruction(int idx) {
        String instruction = ConstsInstructions.INSTRUCTION_CODE_ALETTER;

        switch (idx) {
            case 0:
                instruction = ConstsInstructions.INSTRUCTION_CODE_ALETTER;
                break;
            case 1:
                instruction = ConstsInstructions.INSTRUCTION_CODE_BLETTER;
                break;
            case 2:
                instruction = ConstsInstructions.INSTRUCTION_CODE_ELETTER;
                break;
            case 3:
                instruction = ConstsInstructions.INSTRUCTION_CODE_GLETTER;
                break;
            case 4:
                instruction = ConstsInstructions.INSTRUCTION_CODE_KLETTER;
                break;
            case 5:
                instruction = ConstsInstructions.INSTRUCTION_CODE_MLETTER;
                break;
            case 6:
                instruction = ConstsInstructions.INSTRUCTION_CODE_NLETTER;
                break;
            case 7:
                instruction = ConstsInstructions.INSTRUCTION_CODE_RLETTER;
                break;
            case 8:
                instruction = ConstsInstructions.INSTRUCTION_CODE_WLETTER;
                break;
            case 9:
                instruction = ConstsInstructions.INSTRUCTION_CODE_ZLETTER;
                break;
        }

        return instruction;
    }

    public static int GetInstructionCodeIdx(String instruction) {

        int instructionIdx = -1;
        switch (instruction) {
            case ConstsInstructions.INSTRUCTION_CODE_ALETTER:
                instructionIdx = 0;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_BLETTER:
                instructionIdx = 1;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_ELETTER:
                instructionIdx = 2;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_GLETTER:
                instructionIdx = 3;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_KLETTER:
                instructionIdx = 4;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_MLETTER:
                instructionIdx = 5;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_NLETTER:
                instructionIdx = 6;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_RLETTER:
                instructionIdx = 7;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_WLETTER:
                instructionIdx = 8;
                break;
            case ConstsInstructions.INSTRUCTION_CODE_ZLETTER:
                instructionIdx = 9;
                break;
        }

        return instructionIdx;
    }

    public static int GetInstructionIdx(String instructionStr) {

        int instructionIdx = -1;
        switch (instructionStr) {
            case ConstsInstructions.INSTRUCTION_STRING_A:
                instructionIdx = 0;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_B:
                instructionIdx = 1;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_E:
                instructionIdx = 2;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_G:
                instructionIdx = 3;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_K:
                instructionIdx = 4;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_M:
                instructionIdx = 5;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_N:
                instructionIdx = 6;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_R:
                instructionIdx = 7;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_W:
                instructionIdx = 8;
                break;
            case ConstsInstructions.INSTRUCTION_STRING_Z:
                instructionIdx = 9;
                break;
        }

        return instructionIdx;
    }
}
