package com.software.verifyoo.verifyooofflinesdk.ServerAPI.Objects;

import java.util.ArrayList;

/**
 * Created by roy on 2/24/2016.
 */
public class ExpShape {
    public String Instruction;
    public ArrayList<ExpStroke> Strokes;

    public ExpShape() {
        Strokes = new ArrayList<>();
    }
}
