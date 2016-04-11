package com.software.cognitho.cognithoapp.Logic;

import android.content.Context;
import android.content.Intent;

import com.software.cognitho.cognithoapp.Activities.InstructionInputSelection;
import com.software.cognitho.cognithoapp.ErrorPage;
import com.software.cognitho.cognithoapp.General.ApiVerbEnum;
import com.software.cognitho.cognithoapp.General.AppData;
import com.software.cognitho.cognithoapp.General.Consts;
import com.software.cognitho.cognithoapp.Objects.InstructionObject;
import com.software.cognitho.cognithoapp.R;
import com.software.cognitho.cognithoapp.Tools.ApiUtis;

import java.util.ArrayList;
import java.util.HashMap;

import flexjson.JSONDeserializer;

public class ApiActionGetInstruction implements IApiAction {

    private static Context _applicationContext = null;

    public ApiActionGetInstruction(Context applicationContext) {
        _applicationContext = applicationContext;
    }

    @Override
    public void postAction(String result, boolean isSuccess) {

        ArrayList<InstructionObject> listInstructions = new ArrayList<InstructionObject>();

        try {
            JSONDeserializer<Object> deserializer = new JSONDeserializer<Object>();
            Object deserializedObj = deserializer.deserialize(result);

            AppData.NumInstructionsInTemplate = (int) ((HashMap) deserializedObj).get("NumOfInstructionsInTemplate");
            AppData.NumOfFutilityInstructions = (int) ((HashMap) deserializedObj).get("NumOfFutilityInstructions");

            ArrayList<HashMap> tempList = (ArrayList<HashMap>)(((HashMap)deserializedObj).get("ListInstructions"));

            for (int idx = 0; idx < tempList.size(); idx++) {
                listInstructions.add(new InstructionObject(tempList.get(idx)));
            }
            if (listInstructions.size() == 0) {
                isSuccess = false;
            }
        } catch (Exception exc) {
            isSuccess = false;
        }

        if(isSuccess) {
            AppData.listInstructions = listInstructions;
            AppData.instructionIdx = 0;
            Intent intent = new Intent(_applicationContext, InstructionInputSelection.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _applicationContext.startActivity(intent);
        }
        else {
            Intent intent = new Intent(_applicationContext, ErrorPage.class);
            intent.putExtra(Consts.EXTRA_ERROR_MESSAGE, _applicationContext.getString(R.string.errCannotFetchInstructions));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _applicationContext.startActivity(intent);
        }
    }

    @Override
    public String getUrl() {
        return String.format("%s%s%s", ApiUtis.getBaseIP(), ApiUtis.getBaseApiUrl(), "Instructions");
    }

    @Override
    public ApiVerbEnum getVerb() {
        return ApiVerbEnum.GET;
    }
}
