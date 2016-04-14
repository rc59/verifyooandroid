package com.software.cognitho.cognithoapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.software.cognitho.cognithoapp.General.ActionTypeEnum;
import com.software.cognitho.cognithoapp.General.AppData;
import com.software.cognitho.cognithoapp.Logic.ApiActionGetInstruction;
import com.software.cognitho.cognithoapp.Logic.ApiActionGetInstructionByUser;
import com.software.cognitho.cognithoapp.Logic.ApiHandler;
import com.software.cognitho.cognithoapp.Objects.Template;
import com.software.cognitho.cognithoapp.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Test extends Activity {

    EditText mTxtUserName;
    TextView mTxtLoading;
    Button mBtnSignIn;
    Button mBtnSignUp;
    Button mBtnUpdateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_test);

        init();
    }

    private void init() {
        AppData.NumInstructionsInTemplate = 0;
        AppData.NumOfFutilityInstructions = 0;
        AppData.CurrentNumInstructionsInTemplate = 0;
        AppData.CurrentNumOfFutilityInstructions = 0;

        mBtnSignIn = (Button) findViewById(R.id.btnSignIn);
        mBtnSignUp = (Button) findViewById(R.id.btnSignUp);
        mBtnUpdateUser = (Button) findViewById(R.id.btnUpdateUser);

        mTxtUserName = (EditText) findViewById(R.id.txtUserName);
        mTxtLoading = (TextView) findViewById(R.id.textLoading);

        mBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignIn();
            }
        });

        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignUp();
            }
        });

        mBtnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateUser();
            }
        });

        String storedUsername = readFromFile();
        if (storedUsername != null && storedUsername.length() > 0) {
            mTxtUserName.setText(storedUsername);
        }
    }

    private void writeToFile(String data) {
        try {
            //File file = new File(Environment.getExternalStorageDirectory(), "/shapeRecognition/user.txt");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("shapesrec.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile() {

        String ret = "";

        try {
            InputStream inputStream = openFileInput("shapesrec.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        catch (Exception e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }

    private void initTemplate() {
        AppData.tokenId = "666";
        String user = mTxtUserName.getText().toString();

//        if (user.length() == 0) {
//            user = "u";
//        }

        mTxtUserName.setVisibility(View.GONE);
        mBtnSignIn.setVisibility(View.GONE);
        mBtnSignUp.setVisibility(View.GONE);
        mBtnUpdateUser.setVisibility(View.GONE);
        mTxtLoading.setVisibility(View.VISIBLE);

        writeToFile(user);

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
