package com.software.dalal.signaturecapture.Models;

import java.util.ArrayList;

/**
 * Created by roy on 12/12/2016.
 */
public class ExpTemplate {
    public String Name;
    public ArrayList<ExpShape> ExpShapeList;

    public ExpTemplate() {
        Name = "TestSignature";
        ExpShapeList = new ArrayList<>();
    }
}
