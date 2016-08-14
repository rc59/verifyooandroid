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
//        mListWords.add("ager");
//        mListWords.add("amen");
//        mListWords.add("anew");
//        mListWords.add("bake");
//        mListWords.add("bane");
//        mListWords.add("bang");
//        mListWords.add("bank");
//        mListWords.add("bare");
//        mListWords.add("bark");
//        mListWords.add("barm");
//        mListWords.add("barn");
//        mListWords.add("beak");
//        mListWords.add("beam");
//        mListWords.add("bean");
//        mListWords.add("bear");
//        mListWords.add("bema");
//        mListWords.add("berg");
//        mListWords.add("berk");
//        mListWords.add("berm");
//        mListWords.add("brae");
//        mListWords.add("brag");
//        mListWords.add("bran");
//        mListWords.add("braw");
//        mListWords.add("bren");
//        mListWords.add("brew");
//        mListWords.add("earn");
//        mListWords.add("gaen");
//        mListWords.add("gamb");
//        mListWords.add("game");
//        mListWords.add("gane");
//        mListWords.add("garb");
//        mListWords.add("gawk");
//        mListWords.add("gear");
//        mListWords.add("germ");
//        mListWords.add("gnar");
//        mListWords.add("gnaw");
//        mListWords.add("grab");
//        mListWords.add("gram");
//        mListWords.add("gran");
//        mListWords.add("grew");
//        mListWords.add("kame");
//        mListWords.add("kane");
//        mListWords.add("karn");
//        mListWords.add("kbar");
//        mListWords.add("kerb");
//        mListWords.add("kern");
//        mListWords.add("knar");
//        mListWords.add("knew");
//        mListWords.add("mabe");
//        mListWords.add("mage");
//        mListWords.add("make");
//        mListWords.add("mane");
//        mListWords.add("mare");
//        mListWords.add("mark");
//        mListWords.add("mawn");
//        mListWords.add("mean");
//        mListWords.add("mega");
//        mListWords.add("merk");
//        mListWords.add("nabe");
//        mListWords.add("name");
//        mListWords.add("nark");
//        mListWords.add("near");
//        mListWords.add("nema");
//        mListWords.add("rage");
//        mListWords.add("rake");
//        mListWords.add("rang");
//        mListWords.add("rank");
//        mListWords.add("ream");
//        mListWords.add("wage");
//        mListWords.add("wake");
//        mListWords.add("wame");
//        mListWords.add("wane");
//        mListWords.add("ware");
//        mListWords.add("wark");
//        mListWords.add("warm");
//        mListWords.add("warn");
//        mListWords.add("weak");
//        mListWords.add("wean");
//        mListWords.add("wear");
//        mListWords.add("weka");
//        mListWords.add("wren");
          mListWords.add("abed");  mListWords.add("adze");  mListWords.add("aped");  mListWords.add("aper");  mListWords.add("bade");  mListWords.add("bake");  mListWords.add("bard");  mListWords.add("bare");  mListWords.add("barf");  mListWords.add("bark");  mListWords.add("barm");  mListWords.add("bead");  mListWords.add("beak");  mListWords.add("beam");  mListWords.add("bear");  mListWords.add("bema");  mListWords.add("berk");  mListWords.add("berm");  mListWords.add("brad");  mListWords.add("brae");  mListWords.add("bred");  mListWords.add("dame");  mListWords.add("damp");  mListWords.add("darb");  mListWords.add("dare");  mListWords.add("dark");  mListWords.add("daze");  mListWords.add("deaf");  mListWords.add("dear");  mListWords.add("derm");  mListWords.add("drab");  mListWords.add("dram");  mListWords.add("drek");  mListWords.add("fade");  mListWords.add("fake");  mListWords.add("fame");  mListWords.add("fard");  mListWords.add("fare");  mListWords.add("farm");  mListWords.add("faze");  mListWords.add("fear");  mListWords.add("frae");  mListWords.add("frap");  mListWords.add("kame");  mListWords.add("kbar");  mListWords.add("kemp");  mListWords.add("kerb");  mListWords.add("kerf");  mListWords.add("mabe");  mListWords.add("made");  mListWords.add("make");  mListWords.add("mare");  mListWords.add("mark");  mListWords.add("maze");  mListWords.add("mead");  mListWords.add("merk");  mListWords.add("pard");  mListWords.add("pare");  mListWords.add("park");  mListWords.add("peak");  mListWords.add("pear");  mListWords.add("perk");  mListWords.add("perm");  mListWords.add("pram");  mListWords.add("prez");  mListWords.add("rake");  mListWords.add("ramp");  mListWords.add("raze");  mListWords.add("read");  mListWords.add("ream");  mListWords.add("reap");  mListWords.add("zarf");  mListWords.add("zerk");
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
