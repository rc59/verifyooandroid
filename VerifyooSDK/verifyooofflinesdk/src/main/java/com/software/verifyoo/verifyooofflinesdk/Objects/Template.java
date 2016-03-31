package com.software.verifyoo.verifyooofflinesdk.Objects;

import com.software.verifyoo.verifyooofflinesdk.Logic.Statistics.Mgr.NormMgr;
import com.software.verifyoo.verifyooofflinesdk.Logic.Statistics.Mgr.StatEngine;
import com.software.verifyoo.verifyooofflinesdk.Utils.Consts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by roy on 1/14/2016.
 */
public class Template {
    public ArrayList<CompactGesture> ListGestures;
    public int NumInstructionsReg;
    public int NumInstructionsAuth;

    public void Init() {
        NumInstructionsReg = Consts.DEFAULT_NUM_REQ_GESTURES_REG;
        NumInstructionsAuth = Consts.DEFAULT_NUM_REQ_GESTURES_AUTH;

        CompactGesture tempGesture;

        NormMgr normMgr = new NormMgr();
        StatEngine statEngine = new StatEngine(normMgr);

        for (int idx = 0; idx < ListGestures.size(); idx++) {
            tempGesture = ListGestures.get(idx);
            tempGesture.InitParams();
            tempGesture.UniqueFactor = statEngine.GetGestureUniqueFactor(tempGesture);
        }

        Collections.sort(ListGestures, new Comparator<CompactGesture>() {
            @Override
            public int compare(CompactGesture gesture1, CompactGesture gesture2) {
                if (Math.abs(gesture1.UniqueFactor) > Math.abs(gesture2.UniqueFactor)) {
                    return -1;
                }
                if (Math.abs(gesture1.UniqueFactor) < Math.abs(gesture2.UniqueFactor)) {
                    return 1;
                }
                return 0;
            }
        });

        String s = "";
    }
}
