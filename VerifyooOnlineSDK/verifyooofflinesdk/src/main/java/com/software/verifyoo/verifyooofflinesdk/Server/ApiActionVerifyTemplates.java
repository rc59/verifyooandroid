package com.software.verifyoo.verifyooofflinesdk.Server;

import android.content.Intent;

import com.software.verifyoo.verifyooofflinesdk.Abstract.GestureInputAbstract;
import com.software.verifyoo.verifyooofflinesdk.Utils.VerifyooConsts;

/**
 * Created by roy on 9/10/2015.
 */
public class ApiActionVerifyTemplates implements IApiAction {

    GestureInputAbstract mGestureInputAbstract;

    public ApiActionVerifyTemplates(GestureInputAbstract gestureInputAbstract) {
        mGestureInputAbstract = gestureInputAbstract;
    }

    @Override
    public void postAction(String result, boolean isSuccess) {
        boolean isAuth = false;
        if (result.compareTo("true") == 0) {
            isAuth = true;
        }

        Intent intent = mGestureInputAbstract.getIntent();
        intent.putExtra(VerifyooConsts.EXTRA_BOOLEAN_IS_AUTHORIZED, isAuth);
        mGestureInputAbstract.setResult(-1, intent);
        mGestureInputAbstract.finish();
    }

    @Override
    public String getUrl() {
        return String.format("%s%s%s", ApiUtils.getBaseIP(), ApiUtils.getBaseApiUrl(), "Templates/Verify");
    }

    @Override
    public ApiVerbEnum getVerb() {
        return ApiVerbEnum.POST;
    }
}
