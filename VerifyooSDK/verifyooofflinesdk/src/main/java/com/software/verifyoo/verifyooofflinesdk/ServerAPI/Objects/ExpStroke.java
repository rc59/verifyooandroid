package com.software.verifyoo.verifyooofflinesdk.ServerAPI.Objects;

import java.util.ArrayList;

/**
 * Created by roy on 2/24/2016.
 */
public class ExpStroke {
    public double Length;
    public ArrayList<ExpMotionEventCompact> ListEvents;

    public ExpStroke() {
        ListEvents = new ArrayList<>();
    }
}
