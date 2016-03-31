package com.software.verifyoo.verifyooofflinesdk.Objects.ParamProperties.Interfaces;

/**
 * Created by roy on 2/17/2016.
 */
public abstract class BaseParamAbstract implements IBaseParam {
    public String ParamName;
    public int Weight;

    public int GetWeight() {
        return Weight;
    }
}
