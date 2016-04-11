package com.software.cognitho.cognithoapp.Logic;

import android.content.Context;
import android.content.Intent;

import com.software.cognitho.cognithoapp.Activities.AuthResult;
import com.software.cognitho.cognithoapp.ErrorPage;
import com.software.cognitho.cognithoapp.General.ApiVerbEnum;
import com.software.cognitho.cognithoapp.General.AppData;
import com.software.cognitho.cognithoapp.General.Consts;
import com.software.cognitho.cognithoapp.Tools.ApiUtis;

/**
 * Created by roy on 9/30/2015.
 */
public class ApiActionRequestAuthToken implements IApiAction {

    private static Context _applicationContext = null;

    public ApiActionRequestAuthToken(Context applicationContext) {
        _applicationContext = applicationContext;
    }

    @Override
    public void postAction(String result, boolean isSuccess) {
        Intent intent;
        if (result != null && result.length() > 0) {
            intent = new Intent(_applicationContext, AuthResult.class);
            intent.putExtra(Consts.EXTRA_AUTH_RESULT, result);
        }
        else {
            intent = new Intent(_applicationContext, ErrorPage.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _applicationContext.startActivity(intent);
    }

    @Override
    public String getUrl() {
        return String.format("%s%sAuth/RequestAuthTokenKey?tokenId=%s&callbackUrl=%s", ApiUtis.getBaseIP(), ApiUtis.getBaseApiUrl(), AppData.tokenId, AppData.callbackUrl);
    }

    @Override
    public ApiVerbEnum getVerb() {
        return ApiVerbEnum.POST;
    }

}