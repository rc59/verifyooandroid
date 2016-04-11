package com.software.cognitho.cognithoapp.Logic;

import android.widget.Button;
import android.widget.TextView;

import com.software.cognitho.cognithoapp.General.ApiVerbEnum;
import com.software.cognitho.cognithoapp.General.AppData;
import com.software.cognitho.cognithoapp.Tools.ApiUtis;

/**
 * Created by roy on 9/10/2015.
 */
public class ApiActionVerifyTemplates implements IApiAction {

    Button _btnSave;
    Button _btnConfirm;
    TextView _lblStatus;

    public ApiActionVerifyTemplates(Button btnSave, Button btnConfirm, TextView lblStatus) {
        _btnSave = btnSave;
        _btnConfirm = btnConfirm;
        _lblStatus = lblStatus;
    }

    @Override
    public void postAction(String result, boolean isSuccess) {
        if (result.compareTo("true") == 0) {
            AppData.CurrentNumInstructionsInTemplate++;
            AppData.listInstructions.get(AppData.instructionIdx).IsInTemplate = true;
            //_btnConfirm.setVisibility(View.INVISIBLE);
            //_btnSave.setVisibility(View.VISIBLE);
            //_lblStatus.setText("Shapes match - please save");
        }
        else {
            AppData.CurrentNumOfFutilityInstructions++;
            //_lblStatus.setText("Shapes do not match - please retry");
        }

        _btnSave.callOnClick();
    }

    @Override
    public String getUrl() {
        return String.format("%s%s%s", ApiUtis.getBaseIP(), ApiUtis.getBaseApiUrl(), "Templates/Verify");
    }

    @Override
    public ApiVerbEnum getVerb() {
        return ApiVerbEnum.POST;
    }
}
