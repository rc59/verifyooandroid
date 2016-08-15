package com.software.verifyoo.verifyooofflinesdk.Models;

import java.util.ArrayList;

import Data.UserProfile.Raw.Stroke;

/**
 * Created by roy on 8/15/2016.
 */
public class ModelStroke {
    public ArrayList<ModelEvent> ListEvents;

    public ModelStroke() {

    }

    public ModelStroke(Stroke inputStroke) {
        ListEvents = new ArrayList<>();
        for(int idx = 0; idx < inputStroke.ListEvents.size(); idx++) {
            ListEvents.add(new ModelEvent(inputStroke.ListEvents.get(idx)));
        }
    }

    public Stroke ToStroke() {
        Stroke tempStroke = new Stroke();

        for(int idx = 0; idx < ListEvents.size(); idx++) {
            tempStroke.ListEvents.add(ListEvents.get(idx).ToEvent());
        }

        return tempStroke;
    }
}
