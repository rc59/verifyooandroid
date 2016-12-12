package com.software.dalal.signaturecapture.ApiMgr;

import com.software.dalal.signaturecapture.Models.ExpShape;
import com.software.dalal.signaturecapture.Models.ExpTemplate;

import flexjson.JSONSerializer;

/**
 * Created by roy on 12/12/2016.
 */
public class ApiMgr {
    public void storeData(ExpShape gesture) {
        ExpTemplate tempTemplate = new ExpTemplate();
        tempTemplate.ExpShapeList.add(gesture);

        JSONSerializer serializer = new JSONSerializer();
        String strTemplate = serializer.deepSerialize(tempTemplate);

        new ApiStoreTemplate().run(strTemplate);
    }
}
