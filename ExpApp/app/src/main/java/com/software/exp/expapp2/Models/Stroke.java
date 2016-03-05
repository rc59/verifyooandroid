package com.software.exp.expapp2.Models;

import com.software.exp.expapp2.Logic.MotionEventCompact;

import java.util.ArrayList;

public class Stroke {
    public ArrayList<MotionEventCompact> ListEvents;
    public double Length;

    public Stroke() {
        Length = 0;
        ListEvents = new ArrayList<MotionEventCompact>();
    }
}
