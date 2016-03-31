package com.software.verifyoo.verifyooofflinesdk.Logic.Statistics.Interfaces;

import com.software.verifyoo.verifyooofflinesdk.Objects.CompactGesture;
import com.software.verifyoo.verifyooofflinesdk.Objects.Stroke;

/**
 * Created by roy on 12/29/2015.
 */
public interface IStatEngine {
    public double GetProbability(Stroke stroke1, Stroke stroke2);
    public double GetProbability(CompactGesture gesture1, CompactGesture gesture2);
}
