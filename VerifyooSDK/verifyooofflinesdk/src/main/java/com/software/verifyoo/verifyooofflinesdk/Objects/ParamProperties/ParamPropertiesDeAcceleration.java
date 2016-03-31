package com.software.verifyoo.verifyooofflinesdk.Objects.ParamProperties;

import com.software.verifyoo.verifyooofflinesdk.Abstract.ParamPropertiesAbstract;
import com.software.verifyoo.verifyooofflinesdk.Objects.ParamProperties.Interfaces.IBaseParam;
import com.software.verifyoo.verifyooofflinesdk.Utils.Consts;
import com.software.verifyoo.verifyooofflinesdk.Utils.ConstsParams;
import com.software.verifyoo.verifyooofflinesdk.Utils.UtilsCalc;
import com.software.verifyoo.verifyooofflinesdk.Utils.UtilsData;

import java.util.HashMap;

/**
 * Created by roy on 2/15/2016.
 */
public class ParamPropertiesDeAcceleration extends ParamPropertiesAbstract {
    public ParamPropertiesDeAcceleration(double[] values, int length, String paramName, HashMap<String, IBaseParam> hashParams) {
        super(values, length, paramName, hashParams);
    }

    @Override
    protected void InitSubParameters(HashMap<String, IBaseParam> hashParams) {
        try {
            UtilsData.AddNumericalParameter(hashParams, UtilsCalc.GetParamName(Name, ConstsParams.StrokeExtendedParams.AVERAGE), Average, Consts.WEIGHT_LOW);
            UtilsData.AddNumericalParameter(hashParams, UtilsCalc.GetParamName(Name, ConstsParams.StrokeExtendedParams.MIN), Min, Consts.WEIGHT_LOW);
            UtilsData.AddNumericalParameter(hashParams, UtilsCalc.GetParamName(Name, ConstsParams.StrokeExtendedParams.STANDARD_DEVIATION), StandardDeviation, Consts.WEIGHT_LOW);
            UtilsData.AddNumericalParameter(hashParams, UtilsCalc.GetParamName(Name, ConstsParams.StrokeExtendedParams.MEDIAN), Median, Consts.WEIGHT_LOW);
            UtilsData.AddNumericalParameter(hashParams, UtilsCalc.GetParamName(Name, ConstsParams.StrokeExtendedParams.INTER_Q3_Q1_RANGE), InterQ3Q1Range, Consts.WEIGHT_LOW);

//            hashParams.put(UtilsCalc.GetParamName(Name, ConstsParams.StrokeExtendedParams.QUARTER1), Quarter1);
//            hashParams.put(UtilsCalc.GetParamName(Name, ConstsParams.StrokeExtendedParams.QUARTER3), Quarter3);
        }
        catch (Exception exc) {
            String msg = exc.getMessage();
        }
    }
}
