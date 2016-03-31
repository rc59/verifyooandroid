package com.software.verifyoo.verifyooofflinesdk.Objects.ParamProperties;

import com.software.verifyoo.verifyooofflinesdk.Abstract.ParamPropertiesAbstract;
import com.software.verifyoo.verifyooofflinesdk.Objects.ParamProperties.Interfaces.IBaseParam;

import java.util.HashMap;

/**
 * Created by roy on 2/15/2016.
 */
public class ParamPropertiesGeneral extends ParamPropertiesAbstract {
    public ParamPropertiesGeneral(double[] values, int length, String paramName, HashMap<String, IBaseParam> hashParams) {
        super(values, length, paramName, hashParams);
    }
}
