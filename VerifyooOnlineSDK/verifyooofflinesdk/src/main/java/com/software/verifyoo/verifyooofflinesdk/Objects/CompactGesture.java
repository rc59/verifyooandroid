package com.software.verifyoo.verifyooofflinesdk.Objects;

import java.util.ArrayList;

/**
 * Created by roy on 12/28/2015.
 */
public class CompactGesture {

    public String Instruction;
    public ArrayList<Stroke> Strokes;

    public double UniqueFactor;

    public double TimeInterval;

    public double Width;
    public double Height;
    public double BoundingBox;

    public double StrokePauseAvg;
    public double StrokePauseMax;

    public double StrokeDistanceAvg;
    public double StrokeDistanceMax;

    public  CompactGesture() {

    }

    public CompactGesture(ArrayList<Stroke> listStrokes) {
        Strokes = listStrokes;
    }
}
