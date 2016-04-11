package com.software.cognitho.cognithoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.WindowManager;

import com.software.cognitho.cognithoapp.Activities.Test;
import com.software.cognitho.cognithoapp.General.ActionTypeEnum;
import com.software.cognitho.cognithoapp.General.AppData;
import com.software.cognitho.cognithoapp.General.Consts;
import com.software.cognitho.cognithoapp.Logic.ApiActionGetInstruction;
import com.software.cognitho.cognithoapp.Logic.ApiActionGetInstructionByUser;
import com.software.cognitho.cognithoapp.Logic.ApiHandler;
import com.software.cognitho.cognithoapp.Objects.Template;


public class MainActivity extends Activity {

    String mAction, mUser, mTokenId, mCallbackUrl;
    boolean mIsAppInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }



    private void init() {
        mIsAppInit = false;
        getParams();
        if (mIsAppInit) {
            checkAction();
        }
        else {
            goToErrorPage();
        }
    }

    private void goToErrorPage() {
        Intent intent = new Intent(getApplicationContext(), Test.class);
        startActivity(intent);
    }

    private void checkAction() {
        //String action = "SIGNIN";
        //String action = "CREATE";
        //String action = "UPDATE";

        mAction = mAction.toUpperCase();
        AppData.setActionType(mAction);

        if (mAction.compareTo(ActionTypeEnum.CREATE.toString()) == 0) {
            createUser();
        }

        if (mAction.compareTo(ActionTypeEnum.UPDATE.toString()) == 0) {
            updateUser();
        }

        if (mAction.compareTo(ActionTypeEnum.SIGNIN.toString()) == 0) {
            signIn();
        }
    }

    private void initTemplate() {
        String serviceName = Context.TELEPHONY_SERVICE;
        TelephonyManager telephonyMgr = (TelephonyManager) getSystemService(serviceName);
        WindowManager windowMgr = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        double xdpi = metrics.xdpi;
        double ydpi = metrics.ydpi;

        AppData.template = new Template(getApplicationContext(), telephonyMgr, windowMgr, locationManager);
        AppData.template.Xdpi = xdpi;
        AppData.template.Ydpi = ydpi;
    }

    private void signIn() {
        initTemplate();
        ApiActionGetInstructionByUser getInstruction = new ApiActionGetInstructionByUser(getApplicationContext(), AppData.userId, false);
        ApiHandler apiHandler = new ApiHandler(getInstruction);
        apiHandler.Execute();
    }

    private void updateUser() {
        initTemplate();
        ApiActionGetInstructionByUser getInstruction = new ApiActionGetInstructionByUser(getApplicationContext(), AppData.userId, true);
        ApiHandler apiHandler = new ApiHandler(getInstruction);
        apiHandler.Execute();
    }

    private void createUser() {
        initTemplate();
        ApiActionGetInstruction getInstruction = new ApiActionGetInstruction(getApplicationContext());
        ApiHandler apiHandler = new ApiHandler(getInstruction);
        apiHandler.Execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private String getQueryParam(String paramId) {
        String param = "";
        if (getIntent().getData().getQueryParameter(paramId) != null) {
            param = getIntent().getData().getQueryParameter(paramId);
        }
        return param;
    }

    public void getParams() {
        try {
            if (getIntent().getData() != null) {
                mAction = getQueryParam(Consts.INIT_APP_ARG_ACTION);
                mUser = getQueryParam(Consts.INIT_APP_ARG_USER);
                mTokenId = getQueryParam(Consts.INIT_APP_ARG_TOKEN_ID);
                mCallbackUrl = getQueryParam(Consts.INIT_APP_ARG_URL);

                if (mAction.length() > 0 && mUser.length() > 0 && mTokenId.length() > 0 && mCallbackUrl.length() > 0) {
                    AppData.userId = mUser;
                    AppData.tokenId = mTokenId;
                    AppData.callbackUrl = mCallbackUrl;

                    mIsAppInit = true;
                }
            }
        } catch (Exception exc) {
            mIsAppInit = false;
        }
    }
}
