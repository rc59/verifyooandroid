package com.software.verifyoo.verifyooofflinesdk.ServerAPI.API;

import android.view.WindowManager;

/**
 * Created by roy on 2/25/2016.
 */
public class ApiMgrStoreDataParams {
    boolean IsStoreData;
    String UserName;
    String Company;
    String State;
    WindowManager WindowMgr;
    double XDpi;
    double YDpi;

    public String AnalysisString;
    public double Score;

    public ApiMgrStoreDataParams(String username, String company, String state, WindowManager windowManager, double xDpi, double yDpi, boolean isStoreData) {
        UserName = username;
        Company = company;
        State = state;
        WindowMgr = windowManager;
        XDpi = xDpi;
        YDpi = yDpi;
        IsStoreData = isStoreData;
        Score = -1;
        AnalysisString = "";
    }
}
