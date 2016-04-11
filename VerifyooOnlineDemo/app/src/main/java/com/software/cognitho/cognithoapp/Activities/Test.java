package com.software.cognitho.cognithoapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.software.cognitho.cognithoapp.General.ActionTypeEnum;
import com.software.cognitho.cognithoapp.General.AppData;
import com.software.cognitho.cognithoapp.Logic.ApiActionGetInstruction;
import com.software.cognitho.cognithoapp.Logic.ApiActionGetInstructionByUser;
import com.software.cognitho.cognithoapp.Logic.ApiHandler;
import com.software.cognitho.cognithoapp.Objects.Template;
import com.software.cognitho.cognithoapp.R;

public class Test extends Activity {

    EditText mTxtUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        init();
    }

    private void init() {
        AppData.NumInstructionsInTemplate = 0;
        AppData.NumOfFutilityInstructions = 0;
        AppData.CurrentNumInstructionsInTemplate = 0;
        AppData.CurrentNumOfFutilityInstructions = 0;

        Button btnSignIn = (Button) findViewById(R.id.btnSignIn);
        Button btnSignUp = (Button) findViewById(R.id.btnSignUp);
        Button btnUpdateUser = (Button) findViewById(R.id.btnUpdateUser);

        mTxtUserName = (EditText) findViewById(R.id.txtUserName);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignIn();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignUp();
            }
        });

        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateUser();
            }
        });
    }

    private void initTemplate() {
        AppData.tokenId = "666";
        String user = mTxtUserName.getText().toString();
//        if (user.length() == 0) {
//            user = "u";
//        }
        AppData.userId = user;

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

    private void onClickSignUp() {
        String user = mTxtUserName.getText().toString();
        if (user.length() > 0) {
            AppData.actionType = ActionTypeEnum.CREATE;
            initTemplate();
            ApiActionGetInstruction getInstruction = new ApiActionGetInstruction(getApplicationContext());
            ApiHandler apiHandler = new ApiHandler(getInstruction);
            apiHandler.Execute();
        }
    }

    private void onClickSignIn() {
        String user = mTxtUserName.getText().toString();
        if (user.length() > 0) {
            AppData.actionType = ActionTypeEnum.SIGNIN;
            initTemplate();
            ApiActionGetInstructionByUser getInstruction = new ApiActionGetInstructionByUser(getApplicationContext(), AppData.userId, false);
            ApiHandler apiHandler = new ApiHandler(getInstruction);
            apiHandler.Execute();
        }
    }

    private void onClickUpdateUser() {
        String user = mTxtUserName.getText().toString();
        if (user.length() > 0) {
            AppData.actionType = ActionTypeEnum.UPDATE;

            initTemplate();
            ApiActionGetInstructionByUser getInstruction = new ApiActionGetInstructionByUser(getApplicationContext(), AppData.userId, true);
            ApiHandler apiHandler = new ApiHandler(getInstruction);
            apiHandler.Execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }
}
