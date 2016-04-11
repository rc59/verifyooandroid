package com.software.cognitho.cognithoapp.Logic;

import com.software.cognitho.cognithoapp.General.ApiVerbEnum;

public interface IApiAction {

    public void postAction(String result, boolean isSuccess);
    public String getUrl();
    public ApiVerbEnum getVerb();

}
