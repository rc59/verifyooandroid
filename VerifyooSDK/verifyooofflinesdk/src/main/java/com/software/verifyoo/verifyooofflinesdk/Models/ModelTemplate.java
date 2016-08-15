package com.software.verifyoo.verifyooofflinesdk.Models;

import java.util.ArrayList;

import Data.UserProfile.Raw.Template;

/**
 * Created by roy on 8/15/2016.
 */
public class ModelTemplate {
    public ArrayList<ModelGesture> ListGestures;

    public ModelTemplate() {

    }

    public ModelTemplate(Template inputTemplate) {
        ListGestures = new ArrayList<>();
        for(int idx = 0; idx < inputTemplate.ListGestures.size(); idx++) {
            ListGestures.add(new ModelGesture(inputTemplate.ListGestures.get(idx)));
        }
    }

    public Template ToTemplate() {
        Template template = new Template();

        for(int idx = 0; idx < ListGestures.size(); idx++) {
            template.ListGestures.add(ListGestures.get(idx).ToGesture());
        }

        return template;
    }
}
