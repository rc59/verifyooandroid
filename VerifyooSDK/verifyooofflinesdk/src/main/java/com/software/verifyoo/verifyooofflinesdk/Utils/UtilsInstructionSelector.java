package com.software.verifyoo.verifyooofflinesdk.Utils;

import java.util.ArrayList;
import java.util.Random;

import Data.UserProfile.Extended.TemplateExtended;

/**
 * Created by roy on 7/7/2016.
 */
public class UtilsInstructionSelector {
    protected TemplateExtended mTemplateExtended;

    protected ArrayList<String> mInstructionsAuth;
    protected ArrayList<String> mInstructionsExtra;

    protected String mInstructionExtra;

    protected Random mRandom;

    public UtilsInstructionSelector(TemplateExtended templateExtended) {
        mTemplateExtended = templateExtended;

        init();
    }

    private void init() {
        mRandom = new Random();
        mInstructionsAuth = new ArrayList<>();
        mInstructionsExtra = new ArrayList<>();

        String tempInstruction;
        int numGestures;
        boolean isGestureUsedForAuth;

        for(int idx = 0; idx < Consts.TOTAL_NUM_GESTURES; idx++) {
            tempInstruction = UtilsInstructions.GetInstruction(idx);

            isGestureUsedForAuth = true;
            if (mTemplateExtended.GetHashGesturesByInstruction().containsKey(tempInstruction)) {
                numGestures = mTemplateExtended.GetHashGesturesByInstruction().get(tempInstruction).size();

                if (numGestures < Consts.DEFAULT_NUM_REPEATS_PER_INSTRUCTION) {
                    isGestureUsedForAuth = false;
                }
            }
            else {
                isGestureUsedForAuth = false;
            }

            if (isGestureUsedForAuth) {
                RandomAdd(mInstructionsAuth, tempInstruction);
            }
            else {
                RandomAdd(mInstructionsExtra, tempInstruction);
            }
        }

        int totalNumGestures = Consts.DEFAULT_NUM_REQ_GESTURES_AUTH;
        mInstructionExtra = "";
        if (mInstructionsExtra.size() > 0) {
            totalNumGestures--;
            mInstructionExtra = mInstructionsExtra.get(0);
        }

        while (mInstructionsAuth.size() > totalNumGestures) {
            mInstructionsAuth.remove(0);
        }
    }

    private void RandomAdd(ArrayList<String> listInstructions, String tempInstruction) {
        int numEelements = listInstructions.size();

        if (numEelements == 0) {
            listInstructions.add(tempInstruction);
        } else {
            int insertIdx = mRandom.nextInt(numEelements + 1);
            listInstructions.add(insertIdx, tempInstruction);
        }
    }

    public String GetInstructionExtra() {
        return mInstructionExtra;
    }

    public ArrayList<String> GetInstructionsAuth() {
        return mInstructionsAuth;
    }
}
