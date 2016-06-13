package com.software.verifyoo.verifyooofflinesdk.Utils;

/**
 * Created by roy on 1/14/2016.
 */
public class UtilsInstructions {
    public static String GetInstruction(int idx) {
        String instruction = "FIVE";

        switch (idx) {
            case 0:
                instruction = "FIVE";
                break;
            case 1:
                instruction = "EIGHT";
                break;
            case 2:
                instruction = "ALETTER";
                break;
            case 3:
                instruction = "RLETTER";
                break;
            case 4:
                instruction = "TRIANGULAR";
                break;
            case 5:
                instruction = "HEART";
                break;
        }

        return instruction;
    }
}
