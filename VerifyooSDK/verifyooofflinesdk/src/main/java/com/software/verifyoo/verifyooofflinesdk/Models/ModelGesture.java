package com.software.verifyoo.verifyooofflinesdk.Models;

import java.util.ArrayList;

import Data.UserProfile.Raw.Gesture;

/**
 * Created by roy on 8/15/2016.
 */
public class ModelGesture {
    String Instruction;
    public ArrayList<ModelStroke> ListStrokes;

    public ModelGesture() {

    }

    public ModelGesture(Gesture inputGesture) {
        Instruction = inputGesture.Instruction;
        ListStrokes = new ArrayList<>();
        for(int idx = 0; idx < inputGesture.ListStrokes.size(); idx++) {
            ListStrokes.add(new ModelStroke(inputGesture.ListStrokes.get(idx)));
        }
    }

    public Gesture ToGesture() {
        Gesture tempGesture = new Gesture();

        for(int idx = 0; idx < ListStrokes.size(); idx++) {
            tempGesture.ListStrokes.add(ListStrokes.get(idx).ToStroke());
        }

        return tempGesture;
    }
}
