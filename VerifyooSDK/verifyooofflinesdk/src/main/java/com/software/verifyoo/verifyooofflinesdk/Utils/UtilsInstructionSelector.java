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
    ArrayList<String> mListWords;

    public UtilsInstructionSelector(TemplateExtended templateExtended) {
        mTemplateExtended = templateExtended;

        createWords();
        init();
    }

    private void init() {
        mRandom = new Random();
        mInstructionExtra = "";
        mInstructionsAuth = new ArrayList<>();
        mInstructionsExtra = new ArrayList<>();

        int idxSelectedWord = mRandom.nextInt(mListWords.size());
        String selectedWord = mListWords.get(idxSelectedWord);

        int numGestures;
        boolean isGestureUsedForAuth;
        String tempInstruction;
        String tempChar;
        for(int idxChar = 0; idxChar < selectedWord.length(); idxChar++) {
            tempChar = selectedWord.substring(idxChar, idxChar + 1);
            for(int idxInstruction = 0; idxInstruction < Consts.TOTAL_NUM_GESTURES; idxInstruction++) {
                tempInstruction = UtilsInstructions.GetInstruction(idxInstruction);
                if (UtilsConvert.InstructionCodeToInstruction(tempInstruction).toLowerCase().compareTo(tempChar) == 0) {
                    mInstructionsAuth.add(tempInstruction);
                }
            }
        }
    }

    private void createWords() {
        mListWords = new ArrayList<>();
        mListWords.add("ager");
        mListWords.add("amen");
        mListWords.add("anew");
        mListWords.add("bake");
        mListWords.add("bane");
        mListWords.add("bang");
        mListWords.add("bank");
        mListWords.add("bare");
        mListWords.add("bark");
        mListWords.add("barm");
        mListWords.add("barn");
        mListWords.add("beak");
        mListWords.add("beam");
        mListWords.add("bean");
        mListWords.add("bear");
        mListWords.add("bema");
        mListWords.add("berg");
        mListWords.add("berk");
        mListWords.add("berm");
        mListWords.add("brae");
        mListWords.add("brag");
        mListWords.add("bran");
        mListWords.add("braw");
        mListWords.add("bren");
        mListWords.add("brew");
        mListWords.add("earn");
        mListWords.add("gaen");
        mListWords.add("gamb");
        mListWords.add("game");
        mListWords.add("gane");
        mListWords.add("garb");
        mListWords.add("gawk");
        mListWords.add("gear");
        mListWords.add("germ");
        mListWords.add("gnar");
        mListWords.add("gnaw");
        mListWords.add("grab");
        mListWords.add("gram");
        mListWords.add("gran");
        mListWords.add("grew");
        mListWords.add("kame");
        mListWords.add("kane");
        mListWords.add("karn");
        mListWords.add("kbar");
        mListWords.add("kerb");
        mListWords.add("kern");
        mListWords.add("knar");
        mListWords.add("knew");
        mListWords.add("mabe");
        mListWords.add("mage");
        mListWords.add("make");
        mListWords.add("mane");
        mListWords.add("mare");
        mListWords.add("mark");
        mListWords.add("mawn");
        mListWords.add("mean");
        mListWords.add("mega");
        mListWords.add("merk");
        mListWords.add("nabe");
        mListWords.add("name");
        mListWords.add("nark");
        mListWords.add("near");
        mListWords.add("nema");
        mListWords.add("rage");
        mListWords.add("rake");
        mListWords.add("rang");
        mListWords.add("rank");
        mListWords.add("ream");
        mListWords.add("wage");
        mListWords.add("wake");
        mListWords.add("wame");
        mListWords.add("wane");
        mListWords.add("wank");
        mListWords.add("ware");
        mListWords.add("wark");
        mListWords.add("warm");
        mListWords.add("warn");
        mListWords.add("weak");
        mListWords.add("wean");
        mListWords.add("wear");
        mListWords.add("weka");
        mListWords.add("wren");
    }

    private void init1() {
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
