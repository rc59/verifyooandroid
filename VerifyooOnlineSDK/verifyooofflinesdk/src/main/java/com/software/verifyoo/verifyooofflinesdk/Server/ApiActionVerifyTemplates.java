package com.software.verifyoo.verifyooofflinesdk.Server;

import android.widget.Button;
import android.widget.TextView;

/**
 * Created by roy on 9/10/2015.
 */
public class ApiActionVerifyTemplates implements IApiAction {

    Button _btnSave;
    Button _btnConfirm;
    TextView _lblStatus;

    public ApiActionVerifyTemplates() {
    }

    @Override
    public void postAction(String result, boolean isSuccess) {
        if (result.compareTo("true") == 0) {

        }
        else {

        }
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
