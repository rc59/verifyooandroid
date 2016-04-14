package com.software.verifyoo.verifyooofflinesdk.ServerAPI.Objects;

import java.util.ArrayList;

/**
 * Created by roy on 2/24/2016.
 */
public class ExpTemplate {
    public ArrayList<ExpShape> ExpShapeList;

    public String State;
    public String Company;
    public String OS;
    public String Name;
    public String DeviceId;
    public String ModelName;
    public double ScreenWidth;
    public double ScreenHeight;
    public double Xdpi;
    public double Ydpi;

    public ExpTemplate() {
        ExpShapeList = new ArrayList<>();
    }
}
