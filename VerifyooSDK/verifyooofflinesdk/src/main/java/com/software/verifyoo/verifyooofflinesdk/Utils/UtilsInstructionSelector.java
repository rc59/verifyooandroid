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
//        mListWords.add("ssss");

        mListWords.add("abed");  mListWords.add("amps");  mListWords.add("aped");  mListWords.add("aper");  mListWords.add("apes");  mListWords.add("apse");  mListWords.add("arbs");  mListWords.add("ares");  mListWords.add("arfs");  mListWords.add("arks");  mListWords.add("arms");  mListWords.add("arse");  mListWords.add("bade");  mListWords.add("bads");  mListWords.add("bake");  mListWords.add("bams");  mListWords.add("baps");  mListWords.add("bard");  mListWords.add("bare");  mListWords.add("barf");  mListWords.add("bark");  mListWords.add("barm");  mListWords.add("bars");  mListWords.add("base");  mListWords.add("bask");  mListWords.add("bead");  mListWords.add("beak");  mListWords.add("beam");  mListWords.add("bear");  mListWords.add("beds");  mListWords.add("bema");  mListWords.add("berk");  mListWords.add("berm");  mListWords.add("brad");  mListWords.add("brae");  mListWords.add("bras");  mListWords.add("bred");  mListWords.add("dabs");  mListWords.add("daks");  mListWords.add("dame");  mListWords.add("damp");  mListWords.add("dams");  mListWords.add("daps");  mListWords.add("darb");  mListWords.add("dare");  mListWords.add("dark");  mListWords.add("deaf");  mListWords.add("dear");  mListWords.add("debs");  mListWords.add("derm");  mListWords.add("desk");  mListWords.add("drab");  mListWords.add("dram");  mListWords.add("drek");  mListWords.add("ears");  mListWords.add("emfs");  mListWords.add("eras");  mListWords.add("fabs");  mListWords.add("fade");  mListWords.add("fads");  mListWords.add("fake");  mListWords.add("fame");  mListWords.add("fard");  mListWords.add("fare");  mListWords.add("farm");  mListWords.add("fear");  mListWords.add("feds");  mListWords.add("fems");  mListWords.add("frae");  mListWords.add("frap");  mListWords.add("kabs");  mListWords.add("kaes");  mListWords.add("kafs");  mListWords.add("kame");  mListWords.add("kbar");  mListWords.add("keas");  mListWords.add("kefs");  mListWords.add("kemp");  mListWords.add("keps");  mListWords.add("kerb");  mListWords.add("kerf");  mListWords.add("mabe");  mListWords.add("made");  mListWords.add("mads");  mListWords.add("maes");  mListWords.add("make");  mListWords.add("maps");  mListWords.add("mare");  mListWords.add("mark");  mListWords.add("mars");  mListWords.add("mask");  mListWords.add("mead");  mListWords.add("meds");  mListWords.add("merk");  mListWords.add("mesa");  mListWords.add("pads");  mListWords.add("pams");  mListWords.add("pard");  mListWords.add("pare");  mListWords.add("park");  mListWords.add("pars");  mListWords.add("pase");  mListWords.add("peak");  mListWords.add("pear");  mListWords.add("peas");  mListWords.add("peds");  mListWords.add("perk");  mListWords.add("perm");  mListWords.add("pram");  mListWords.add("rads");  mListWords.add("rake");  mListWords.add("ramp");  mListWords.add("rams");  mListWords.add("raps");  mListWords.add("rase");  mListWords.add("rasp");  mListWords.add("read");  mListWords.add("ream");  mListWords.add("reap");  mListWords.add("rebs");  mListWords.add("reds");  mListWords.add("refs");  mListWords.add("rems");  mListWords.add("reps");  mListWords.add("sabe");  mListWords.add("sade");  mListWords.add("safe");  mListWords.add("sake");  mListWords.add("same");  mListWords.add("samp");  mListWords.add("sard");  mListWords.add("sark");  mListWords.add("seam");  mListWords.add("sear");  mListWords.add("sera");  mListWords.add("serf");  mListWords.add("skep");  mListWords.add("spae");  mListWords.add("spam");  mListWords.add("spar");  mListWords.add("sped");
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
