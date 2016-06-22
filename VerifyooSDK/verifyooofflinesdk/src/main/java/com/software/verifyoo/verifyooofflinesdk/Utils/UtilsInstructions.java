package com.software.verifyoo.verifyooofflinesdk.Utils;

/**
 * Created by roy on 1/14/2016.
 */
public class UtilsInstructions {
    public static String GetInstruction(int idx) {
        String instruction = ConstsInstructions.INSTRUCTION_CODE_FIVE;

        switch (idx) {
            case 0:
                instruction = ConstsInstructions.INSTRUCTION_CODE_FIVE;
                break;
            case 1:
                instruction = ConstsInstructions.INSTRUCTION_CODE_EIGHT;
                break;
            case 2:
                instruction = ConstsInstructions.INSTRUCTION_CODE_ALETTER;
                break;
            case 3:
                instruction = ConstsInstructions.INSTRUCTION_CODE_RLETTER;
                break;
            case 4:
                instruction = ConstsInstructions.INSTRUCTION_CODE_TRIANGULAR;
                break;
            case 5:
                instruction = ConstsInstructions.INSTRUCTION_CODE_HEART;
                break;
            case 6:
                instruction = ConstsInstructions.INSTRUCTION_CODE_TWO;
                break;
            case 7:
                instruction = ConstsInstructions.INSTRUCTION_CODE_BLETTER;
                break;
            case 8:
                instruction = ConstsInstructions.INSTRUCTION_CODE_GLETTER;
                break;
        }

        return instruction;
    }
}
