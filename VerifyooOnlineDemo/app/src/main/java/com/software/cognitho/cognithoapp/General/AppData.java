package com.software.cognitho.cognithoapp.General;

import com.software.cognitho.cognithoapp.Objects.InstructionObject;
import com.software.cognitho.cognithoapp.Objects.Template;

import java.util.ArrayList;

public class AppData {
    public static void setActionType(String strAction) {
        if (strAction.compareToIgnoreCase(Consts.ACTION_TYPE_CREATE) == 0) {
            actionType = ActionTypeEnum.CREATE;
        }
        if (strAction.compareToIgnoreCase(Consts.ACTION_TYPE_UPDATE) == 0) {
            actionType = ActionTypeEnum.UPDATE;
        }
        if (strAction.compareToIgnoreCase(Consts.ACTION_TYPE_SIGNIN) == 0) {
            actionType = ActionTypeEnum.SIGNIN;
        }
    }

    public static ArrayList<InstructionObject> listInstructions = new ArrayList<InstructionObject>();
    public static Template template;

    public static int NumInstructionsInTemplate = 0;
    public static int NumOfFutilityInstructions = 0;

    public static int CurrentNumInstructionsInTemplate = 0;
    public static int CurrentNumOfFutilityInstructions = 0;

    public static int instructionIdx = 0;

    public static String callbackUrl;
    public static String tokenId;
    public static String userId;
    public static ActionTypeEnum actionType;

    public static String AppId = "358239059984288";
    public static String AppKey = "599842883582390";
}
