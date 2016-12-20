package com.software.dalal.signaturecapture.ApiMgr;

import android.content.Context;
import android.widget.TextView;

import com.software.dalal.signaturecapture.Models.ExpShape;
import com.software.dalal.signaturecapture.Models.ExpTemplate;

import flexjson.JSONSerializer;

/**
 * Created by roy on 12/12/2016.
 */
public class ApiMgr {
    Context mApplicationContext;
    TextView mTextView;

    public ApiMgr(Context applicationContext, TextView textView) {
        mApplicationContext = applicationContext;
        mTextView = textView;
    }

    public void storeData(ExpShape gesture) {
        ExpTemplate tempTemplate = new ExpTemplate();
        tempTemplate.ExpShapeList.add(gesture);

        JSONSerializer serializer = new JSONSerializer();
        String strTemplate = serializer.deepSerialize(tempTemplate);

        new ApiStoreTemplate(mApplicationContext, mTextView).run(strTemplate);
    }
}
