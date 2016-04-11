package com.software.cognitho.cognithoapp.Objects;

import java.util.ArrayList;

public class Stroke {
    public ArrayList<MotionEventCompact> ListEvents;
    public double Length;

    public Stroke() {
        Length = 0;
        ListEvents = new ArrayList<MotionEventCompact>();
    }
}

