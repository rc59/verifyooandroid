package com.software.verifyoo.verifyoosdk;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.software.verifyoo.verifyooofflinesdk.Activities.VerifyooAuthenticate;
import com.software.verifyoo.verifyooofflinesdk.Activities.VerifyooRegister;
import com.software.verifyoo.verifyooofflinesdk.Utils.AESCrypt;
import com.software.verifyoo.verifyooofflinesdk.Utils.Consts;
import com.software.verifyoo.verifyooofflinesdk.Utils.Files;
import com.software.verifyoo.verifyooofflinesdk.Utils.UtilsGeneral;
import com.software.verifyoo.verifyooofflinesdk.Utils.VerifyooConsts;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.HashMap;

import Data.MetaData.StoredMetaDataMgr;
import Data.UserProfile.Extended.TemplateExtended;
import Data.UserProfile.Raw.Template;
import Logic.Comparison.Stats.Interfaces.IFeatureMeanData;
import Logic.Comparison.Stats.Norms.NormMgr;

public class Main2Activity extends ActionBarActivity {

    Button mBtnReg;
    Button mBtnAuth;
    Button mBtnHack;
    Button mBtnAnalysis;

    EditText mTxtUser;

    TextView mTxtStatus;
    TextView mTxtViewWait;

    String mUserName;
    String mCompany;

    boolean mIsHack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        init();
    }

    private void init() {
        UtilsGeneral.IdxCurrentWord = 0;
        UtilsGeneral.TempThreshold = 0.85;
        mIsHack = false;
        mCompany = "Verifyoo Experiment";
        mTxtUser = (EditText) findViewById(R.id.txtUserName);

        mTxtStatus = (TextView) findViewById(R.id.txtStatus);
        mTxtViewWait = (TextView) findViewById(R.id.txtLoading);

        mBtnReg = (Button) findViewById(R.id.btnReg);
        mBtnAuth = (Button) findViewById(R.id.btnAuth);
        mBtnHack = (Button) findViewById(R.id.btnHack);
        mBtnAnalysis = (Button) findViewById(R.id.btnAnalysis);

        int colorBlue = Color.parseColor(Consts.VERIFYOO_BLUE);
        int colorGray = Color.parseColor(Consts.VERIFYOO_GRAY);
        int colorRed = Color.parseColor(Consts.VERIFYOO_RED);

        mBtnAuth.setBackgroundColor(colorBlue);
        mBtnHack.setBackgroundColor(colorRed);
        mBtnReg.setBackgroundColor(colorGray);
        mBtnAnalysis.setBackgroundColor(colorGray);

        mBtnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAuth();
            }
        });

        mBtnHack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHack();
            }
        });

        mBtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickReg();
            }
        });

        mBtnAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAnalysis();
            }
        });
    }

    private void setControlsVisibility(boolean isVisible) {
        mTxtStatus.setText("");
        mTxtStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        if(isVisible) {
            mBtnAuth.setVisibility(View.VISIBLE);
            mBtnHack.setVisibility(View.VISIBLE);
            mBtnReg.setVisibility(View.VISIBLE);
            mBtnAnalysis.setVisibility(View.VISIBLE);
            mTxtUser.setVisibility(View.VISIBLE);
        }
        else {
            mBtnAuth.setVisibility(View.GONE);
            mBtnHack.setVisibility(View.GONE);
            mBtnReg.setVisibility(View.GONE);
            mBtnAnalysis.setVisibility(View.GONE);
            mTxtUser.setVisibility(View.GONE);
        }
    }

    private void onClickReg() {
        if (mTxtUser.getText().toString().length() > 0) {
            setControlsVisibility(false);
            mUserName = getUserName();

            UtilsGeneral.AuthStartTime = 0;
            Intent i = new Intent(getApplicationContext(), VerifyooRegister.class);
            i.putExtra(VerifyooConsts.EXTRA_STRING_USER_NAME, mUserName);
            i.putExtra(VerifyooConsts.EXTRA_STRING_COMPANY_NAME, mCompany);
            i.putExtra(VerifyooConsts.EXTRA_BOOLEAN_IS_USE_REPEAT_GESTURE, false);

            startActivityForResult(i, 1);
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter a valid username", Toast.LENGTH_SHORT).show();
        }
    }

    private void authOrHack(boolean isHack) {
        if (mTxtUser.getText().toString().length() > 0) {
            setControlsVisibility(false);
            mIsHack = isHack;
            mUserName = getUserName();
            new TemplateLoader().execute("");
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter a valid username", Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickHack() {
//        UtilsGeneral.TempThreshold = 0.95;
        authOrHack(true);
    }

    private void onClickAuth() {
//        UtilsGeneral.TempThreshold = 0.6;
        authOrHack(false);
    }

    protected void loadTemplate() {
        UtilsGeneral.StoredTemplate = null;
        UtilsGeneral.StoredTemplateExtended = null;

        String errMsg;

        InputStream inputStream = null;
        InputStream inputStreamNorms = null;
        try {
            inputStream = openFileInput(Files.GetFileName(UtilsGeneral.GetStorageName(mUserName)));
            inputStreamNorms = openFileInput(Files.GetFileNameUpdatedNorms(UtilsGeneral.GetStorageName(mUserName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String storedTemplate = Files.readFromFile(inputStream);
        String storedNorms = Files.readFromFile(inputStreamNorms);

        UtilsGeneral.GsonBuilder = new GsonBuilder();
        UtilsGeneral.Gson = UtilsGeneral.GsonBuilder.enableComplexMapKeySerialization().create();

        StoredMetaDataMgr storedMetaDataManager = null;
        if(storedNorms.length() > 0) {
            try {
                try {
                    String key = UtilsGeneral.GetUserKey(UtilsGeneral.GetStorageName(mUserName));
                    storedNorms = AESCrypt.decrypt(key, storedNorms);
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                }

                Object hashMapNormsCtrObj = UtilsGeneral.Gson.fromJson(storedNorms, StoredMetaDataMgr.class);
                storedMetaDataManager = (StoredMetaDataMgr) hashMapNormsCtrObj;

            } catch (Exception e) {
                errMsg = e.getMessage();
            }
        }

        NormMgr.GetInstance();
        if (storedTemplate.length() > 0) {
            try {
                try {
                    String key = UtilsGeneral.GetUserKey(UtilsGeneral.GetStorageName(mUserName));
                    storedTemplate = AESCrypt.decrypt(key, storedTemplate);
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                }

                Object listGesturesObj = UtilsGeneral.Gson.fromJson(storedTemplate, Template.class); //deserializer.deserialize(storedTemplate, Template.class);

                Template storedTemplateObj = ((Template) listGesturesObj);
                if (storedMetaDataManager != null) {
                    UtilsGeneral.StoredMetaDataManager = storedMetaDataManager;
                    NormMgr.GetInstance().GetStoredMetaDataMgr().HashParamBoundaries = UtilsGeneral.StoredMetaDataManager.HashParamBoundaries;
                    HashMap<String, IFeatureMeanData> tempHash = storedMetaDataManager.ToHash();
                    storedTemplateObj.InitHashMap(tempHash);
                }
                else {
                    UtilsGeneral.StoredMetaDataManager = new StoredMetaDataMgr();
                }

                TemplateExtended templateExtended = new TemplateExtended(storedTemplateObj);
                UtilsGeneral.StoredTemplateExtended = templateExtended;
                UtilsGeneral.StoredTemplateExtended.Name = mUserName;
                UtilsGeneral.StoredTemplate = storedTemplateObj;
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setControlsVisibility(true);
        mTxtStatus.setText("");
        mTxtStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (data.getExtras() != null &&
                        data.getExtras().containsKey(VerifyooConsts.EXTRA_DOUBLE_SCORE) &&
                        data.getExtras().containsKey(VerifyooConsts.EXTRA_BOOLEAN_IS_AUTHORIZED)) {

                    double score = data.getExtras().getDouble(VerifyooConsts.EXTRA_DOUBLE_SCORE);

                    score = score * 10000;
                    score = Math.round(score);
                    score = score / 10000;

                    if (score >= 0.87) {
                        mTxtStatus.setText("Authorized");
                        mTxtStatus.setTextColor(Color.parseColor(Consts.VERIFYOO_BLUE));
                        mTxtStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.verified, 0, 0, 0);
                    } else {
                        mTxtStatus.setText("Not Authorized");
                        mTxtStatus.setTextColor(Color.RED);
                        mTxtStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.notauth, 0, 0, 0);
                    }
                }
                else {
                    if (data.getExtras() != null && data.getExtras().containsKey(VerifyooConsts.EXTRA_STRING_ERROR_MESSAGE)) {
                        String errorMessage = data.getExtras().getString(VerifyooConsts.EXTRA_STRING_ERROR_MESSAGE);
                        mTxtStatus.setText(errorMessage);
                    }
                }
            }
            else {
                mTxtStatus.setText("Registration Complete");
            }
        }
    }

    private class TemplateLoader extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            boolean isTemplateLoaded = (UtilsGeneral.StoredTemplateExtended != null && UtilsGeneral.StoredTemplateExtended.Name != null && UtilsGeneral.StoredTemplateExtended.Name.compareTo(mUserName) == 0);

            if (!isTemplateLoaded) {
                loadTemplate();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            mUserName = getUserName();
            Intent i = new Intent(getApplicationContext(), VerifyooAuthenticate.class);
            i.putExtra(VerifyooConsts.EXTRA_STRING_USER_NAME, mUserName);
            i.putExtra(VerifyooConsts.EXTRA_STRING_COMPANY_NAME, mCompany);

            if(mIsHack) {
                mIsHack = false;
                i.putExtra("IsHack", true);
            }

            mTxtViewWait.setVisibility(View.GONE);
            startActivityForResult(i, 1);
        }

        @Override
        protected void onPreExecute() {
            mTxtViewWait.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    private void onClickAnalysis() {
        Intent i = new Intent(getApplicationContext(), Analysis.class);
        startActivity(i);
    }

    private String getUserName() {
        return mTxtUser.getText().toString().toLowerCase();
    }

}
