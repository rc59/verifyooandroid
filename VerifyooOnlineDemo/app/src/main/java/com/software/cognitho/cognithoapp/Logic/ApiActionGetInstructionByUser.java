package com.software.cognitho.cognithoapp.Logic;

import android.content.Context;
import android.content.Intent;

import com.software.cognitho.cognithoapp.Activities.InstructionInputSelection;
import com.software.cognitho.cognithoapp.Activities.SignInInputSelection;
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

public class ApiActionGetInstructionByUser implements IApiAction {

    private static Context _applicationContext = null;
    private static String _userId = "";
    private static boolean _isGetAll = false;

    public ApiActionGetInstructionByUser(Context applicationContext, String userId, boolean isGetAll) {
        _applicationContext = applicationContext;
        _userId = userId;
        _isGetAll = isGetAll;
    }

    @Override
    public void postAction(String result, boolean isSuccess) {

        ArrayList<InstructionObject> listInstructions = new ArrayList<InstructionObject>();

        try {
            JSONDeserializer<ArrayList<HashMap>> deserializer = new JSONDeserializer<ArrayList<HashMap>>();
            ArrayList<HashMap> tempList = deserializer.deserialize(result);

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
            Intent intent;
            if (_isGetAll) {
                intent = new Intent(_applicationContext, InstructionInputSelection.class);
            }
            else {
                intent = new Intent(_applicationContext, SignInInputSelection.class);
            }
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
        return String.format("%s%sInstructions?userId=%s&getAll=%s", ApiUtis.getBaseIP(), ApiUtis.getBaseApiUrl(), _userId, _isGetAll);
    }

    @Override
    public ApiVerbEnum getVerb() {
        return ApiVerbEnum.GET;
    }
}
