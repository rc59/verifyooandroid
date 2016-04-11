package com.software.cognitho.cognithoapp.Objects;

import java.util.ArrayList;

public class GestureObj {
    public int InstructionIdx;

    public boolean IsInTemplate;

    public ArrayList<Stroke> Strokes;

    public GestureObj() {
        Strokes = new ArrayList<Stroke>();
    }

}
