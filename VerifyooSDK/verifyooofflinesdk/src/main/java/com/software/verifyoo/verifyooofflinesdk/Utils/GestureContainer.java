package com.software.verifyoo.verifyooofflinesdk.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import Data.UserProfile.Extended.TemplateExtended;
import Data.UserProfile.Raw.Gesture;
import Data.UserProfile.Raw.Template;
import Logic.Comparison.GestureComparer;

/**
 * Created by roy on 7/14/2016.
 */
public class GestureContainer {
    public String Instruction;
    HashMap<Integer, ArrayList<Gesture>> mListGestures;

    public GestureContainer(String instruction) {
        mListGestures = new HashMap<>();
        Instruction = instruction;
    }

    public void AddGesture(Gesture newGesture) {
        int numStrokes = newGesture.ListStrokes.size();

        if (mListGestures.containsKey(numStrokes)) {
            mListGestures.get(numStrokes).add(newGesture);
        }
        else {
            ArrayList<Gesture> tempListGesture = new ArrayList<>();
            tempListGesture.add(newGesture);
            mListGestures.put(numStrokes, tempListGesture);
        }
    }

    public ArrayList<Gesture> GetGestures() {
        ArrayList<Gesture> tempListGestures = new ArrayList<>();
        boolean isCosineDistanceValid;

        HashMap<Integer, Boolean> hashMapMatchingGestures = new HashMap<>();

        for (int key : mListGestures.keySet()) {
            if (mListGestures.get(key).size() >= Consts.DEFAULT_NUM_REPEATS_PER_INSTRUCTION) {
                tempListGestures = mListGestures.get(key);

                for(int idxGesture1 = 0; idxGesture1 < tempListGestures.size(); idxGesture1++) {
                    for(int idxGesture2 = idxGesture1 + 1; idxGesture2 < tempListGestures.size(); idxGesture2++) {
                        isCosineDistanceValid = CheckCosineDistance(tempListGestures.get(idxGesture1), tempListGestures.get(idxGesture2));
                        if (isCosineDistanceValid) {
                            if (!hashMapMatchingGestures.containsKey(idxGesture1)) {
                                hashMapMatchingGestures.put(idxGesture1, true);
                            }
                            if (!hashMapMatchingGestures.containsKey(idxGesture2)) {
                                hashMapMatchingGestures.put(idxGesture2, true);
                            }
                        }

                        if (hashMapMatchingGestures.keySet().size() >= Consts.DEFAULT_NUM_REPEATS_PER_INSTRUCTION) {
                            break;
                        }
                    }

                }
            }
        }

        ArrayList<Gesture> finalListGestures = new ArrayList<>();

        for (int key : hashMapMatchingGestures.keySet()) {
            finalListGestures.add(tempListGestures.get(key));
        }

        return finalListGestures;
    }

    private boolean CheckCosineDistance(Gesture gesture1, Gesture gesture2) {
        Template template1 = new Template();
        Template template2 = new Template();

        template1.ListGestures.add(gesture1);
        template2.ListGestures.add(gesture2);

        TemplateExtended templateExtended1 = new TemplateExtended(template1);
        TemplateExtended templateExtended2 = new TemplateExtended(template2);

        GestureComparer comparer = new GestureComparer(true);
        comparer.CompareGestures(templateExtended1.ListGestureExtended.get(0), templateExtended2.ListGestureExtended.get(0));

        boolean isCosineDistanceValid = comparer.IsStrokeCosineDistanceValid();

        return isCosineDistanceValid;
    }
}
