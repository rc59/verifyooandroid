package com.software.verifyoo.verifyooofflinesdk.Server;

public interface IApiAction {

    public void postAction(String result, boolean isSuccess);
    public String getUrl();
    public ApiVerbEnum getVerb();

}
