package com.software.cognitho.cognithoapp.Logic;

import android.content.Context;
import android.content.Intent;

import com.software.cognitho.cognithoapp.Activities.TemplateCreated;
import com.software.cognitho.cognithoapp.ErrorPage;
import com.software.cognitho.cognithoapp.General.ApiVerbEnum;
import com.software.cognitho.cognithoapp.General.AppData;
import com.software.cognitho.cognithoapp.Tools.ApiUtis;

/**
 * Created by roy on 8/25/2015.
 */
public class ApiActionCreateTemplate implements IApiAction {

    private static Context _applicationContext = null;

    public ApiActionCreateTemplate(Context applicationContext) {
        _applicationContext = applicationContext;
    }

    @Override
    public void postAction(String result, boolean isSuccess) {
        Intent intent;
        if (result.compareTo("true") == 0) {
            intent = new Intent(_applicationContext, TemplateCreated.class);
        }
        else {
            intent = new Intent(_applicationContext, ErrorPage.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _applicationContext.startActivity(intent);
    }

    @Override
    public String getUrl() {
        return String.format("%s%sTemplates/Post?tokenId=%s&verifyUrl=%s", ApiUtis.getBaseIP(), ApiUtis.getBaseApiUrl(), AppData.tokenId, AppData.callbackUrl);
    }

    @Override
    public ApiVerbEnum getVerb() {
        return ApiVerbEnum.POST;
    }
}
