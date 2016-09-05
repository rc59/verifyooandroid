package com.software.verifyoo.verifyoosdk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import Data.UserProfile.Extended.TemplateExtended;
import Data.UserProfile.Raw.Template;
import Logic.Comparison.Stats.Norms.NormMgr;
import flexjson.JSONDeserializer;


public class MainActivity extends ActionBarActivity {

    TextView mTxtScore;
    TextView mTxtStatus;
    TextView mTxtTotalTime;

    String mUserName;
    String mCompany;

    TextView mTxtError;

    Button mBtnReg;
    Button mBtnAuth;
    ImageView mImage;

    TextView mtxtviewUserName;
    EditText mTxtUser;

    double mThreshold = 0.85;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserName = "";
        mCompany = "Verifyoo";

        init();
        postInit();
    }

    protected void loadNorms() {
        NormMgr.GetInstance();
    }

    protected void loadTemplate() {
        double tsStart, tsTemp, tsDeserializeTemplates, tsDeserializeOcr;

        tsStart = System.currentTimeMillis()/1000;

        UtilsGeneral.StoredTemplate = null;
        UtilsGeneral.StoredTemplateExtended = null;

        InputStream inputStream = null;
//        InputStream inputStreamOcr = null;
        try {
            inputStream = openFileInput(Files.GetFileName(Consts.STORAGE_NAME));
//            inputStreamOcr = openFileInput(Files.GetFileName(Consts.STORAGE_FILE_OCR_DB));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
//                mTxtError.setText(e.getMessage());
        }
        String storedTemplate = Files.readFromFile(inputStream);
//        String storedTemplateOcr = Files.readFromFile(inputStreamOcr);

        if (storedTemplate.length() > 0) {

            JSONDeserializer<Template> deserializer = new JSONDeserializer<Template>();
//            JSONDeserializer<NormalizedGestureContainer> deserializerOcr = new JSONDeserializer<NormalizedGestureContainer>();
            try {
                try {
                    String key = UtilsGeneral.GetUserKey(Consts.STORAGE_NAME);
                    storedTemplate = AESCrypt.decrypt(key, storedTemplate);
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                }

                Object listGesturesObj = deserializer.deserialize(storedTemplate, Template.class);

                Template storedTemplateObj = ((Template) listGesturesObj);
                TemplateExtended templateExtended = new TemplateExtended(storedTemplateObj);
                UtilsGeneral.StoredTemplateExtended = templateExtended;
                UtilsGeneral.StoredTemplate = storedTemplateObj;

//                UtilsGeneral.NormalizedGestureContainerObj = deserializerOcr.deserialize(storedTemplateOcr);
//                tsDeserializeOcr  = System.currentTimeMillis()/1000;

            } catch (Exception e) {
//                    mTxtError.setText(e.getMessage());
            }

        }


    }

    private class NormsLoader extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            loadNorms();
            return null;
        }
    }

    private class TemplateLoader extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            loadTemplate();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            InitScore();
            mBtnAuth.setVisibility(View.VISIBLE);
            mImage.setVisibility(View.VISIBLE);

            if(UtilsGeneral.StoredTemplate == null) {
                mBtnReg.setVisibility(View.VISIBLE);
                mBtnAuth.setVisibility(View.GONE);
                mTxtUser.setVisibility(View.VISIBLE);
            }
            else {
                mBtnReg.setVisibility(View.GONE);
                mBtnAuth.setVisibility(View.VISIBLE);
                loadUserName();
            }
        }

        @Override
        protected void onPreExecute() {
            mBtnAuth.setVisibility(View.GONE);
            mImage.setVisibility(View.GONE);
            mTxtUser.setVisibility(View.GONE);
            mBtnReg.setVisibility(View.GONE);
            mBtnAuth.setVisibility(View.GONE);
            mTxtStatus.setText("Loading...Please wait");
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }


    private void postInit() {
        if (UtilsGeneral.StoredTemplateExtended == null) {
            new TemplateLoader().execute("");
        }
        else {
            InitScore();
        }
    }

    private void init() {
        new NormsLoader().execute("");
        setTitle("Verifyoo Demo");
        Resources res = getResources();
        int color = Color.parseColor(Consts.VERIFYOO_BLUE);

        mTxtError = (TextView) findViewById(R.id.txtErrorMsg);

        mImage = (ImageView) findViewById(R.id.welcomeImage);
        mImage.setImageResource(R.drawable.logo);

        mTxtScore = (TextView) findViewById(R.id.txtScore);
        mTxtStatus = (TextView) findViewById(R.id.txtStatus);
        mTxtTotalTime = (TextView) findViewById(R.id.txtTotalTime);

        //mTxtScore.setTextColor(color);
        mTxtStatus.setTextColor(color);

        mTxtUser = (EditText) findViewById(R.id.txtUserName);
        mtxtviewUserName = (TextView) findViewById(R.id.txtviewUserName);

        mBtnAuth = (Button) findViewById(R.id.btnAuth);
        mBtnReg = (Button) findViewById(R.id.btnReg);
        Button btnHack = (Button) findViewById(R.id.btnHack);

        mBtnAuth.setBackgroundColor(color);
        mBtnReg.setBackgroundColor(color);
        btnHack.setBackgroundColor(color);

        mBtnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAuth();
            }
        });

        mBtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickReg();
            }
        });

        btnHack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHack();
            }
        });

//        Typeface font = Typeface.createFromAsset(getAssets(), "Prototype.ttf");
//        mBtnAuth.setTypeface(font);
    }

    private void InitScore() {
        mTxtScore.setText("");
        mTxtStatus.setText("");
        mTxtError.setText("");
        mTxtStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    private void onClickReg() {
        if (mTxtUser.getText().toString().length() > 0) {
            mUserName = mTxtUser.getText().toString();
            UtilsGeneral.AuthStartTime = 0;
            InitScore();
            Intent i = new Intent(getApplicationContext(), VerifyooRegister.class);
            i.putExtra(VerifyooConsts.EXTRA_STRING_USER_NAME, mUserName);
            i.putExtra(VerifyooConsts.EXTRA_STRING_COMPANY_NAME, mCompany);
            i.putExtra(VerifyooConsts.EXTRA_BOOLEAN_IS_USE_REPEAT_GESTURE, false);

            mTxtTotalTime.setText("");
            startActivityForResult(i, 1);
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter a valid username", Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickAuth() {
        if (mTxtUser.getText().toString().length() > 0) {
            mUserName = mTxtUser.getText().toString();
            saveUserName();
            UtilsGeneral.AuthStartTime = 0;
            mTxtScore.setText("Loading...Please wait");
            InitScore();
            Intent i = new Intent(getApplicationContext(), VerifyooAuthenticate.class);
            i.putExtra(VerifyooConsts.EXTRA_STRING_USER_NAME, mUserName);
            i.putExtra(VerifyooConsts.EXTRA_STRING_COMPANY_NAME, mCompany);

            mTxtTotalTime.setText("");
            startActivityForResult(i, 1);
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter a valid username", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserName() {
        SharedPreferences prefs = getSharedPreferences("VerifyooPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Username", mUserName);
        editor.commit();
    }

    private void getThresholdScore() {
        SharedPreferences prefs = getSharedPreferences("VerifyooPrefs", MODE_PRIVATE);
        double thresholdScore = prefs.getFloat("Score", -1);

        if (thresholdScore > -1) {
            mThreshold = thresholdScore;
        }
        else {
            mThreshold = 0.85;
        }
    }

    private void setThresholdScore(double score) {
        SharedPreferences prefs = getSharedPreferences("VerifyooPrefs", MODE_PRIVATE);
        double thresholdScore = prefs.getFloat("Score", -1);

        if (thresholdScore == -1) {
            if (score < 0.9) {
                thresholdScore = 0.8;
            }
            else {
                thresholdScore = 0.85;
            }

            SharedPreferences.Editor editor = prefs.edit();
            editor.putFloat("Score", (float) thresholdScore);
            editor.commit();
        }
    }

    private void loadUserName() {
        SharedPreferences prefs = getSharedPreferences("VerifyooPrefs", MODE_PRIVATE);
        String user = prefs.getString("Username", "");

        if (user != null & user.length() > 0) {
            mUserName = user;
            mTxtUser.setVisibility(View.GONE);
        }
        else {
            mUserName = "";
            mTxtUser.setVisibility(View.VISIBLE);
        }

        if (mUserName.length() > 0) {
            mtxtviewUserName.setText(String.format("Hello %s", mUserName));
            mtxtviewUserName.setVisibility(View.VISIBLE);
        }
        else {
            mtxtviewUserName.setVisibility(View.GONE);
        }

        mTxtUser.setText(mUserName);
    }

    private void clearUserName() {
        SharedPreferences prefs = getSharedPreferences("VerifyooPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Username", "");
        editor.commit();
    }

    private void onClickHack() {
        InitScore();
        Intent i = new Intent(getApplicationContext(), VerifyooAuthenticate.class);
        i.putExtra(VerifyooConsts.EXTRA_STRING_USER_NAME, mUserName);
        i.putExtra(VerifyooConsts.EXTRA_STRING_COMPANY_NAME, mCompany);
        i.putExtra("IsHack", true);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (UtilsGeneral.AuthStartTime != 0) {
                double totalTime = UtilsGeneral.AuthEndTime - UtilsGeneral.AuthStartTime;
                totalTime = totalTime / 1000;

                mTxtTotalTime.setText(String.format("Total Time: %s seconds", String.valueOf(totalTime)));
                UtilsGeneral.AuthStartTime = 0;
            }
            else {
                mTxtTotalTime.setText("");
            }

            if (data != null) {
                if (data.getExtras() != null &&
                        data.getExtras().containsKey(VerifyooConsts.EXTRA_DOUBLE_SCORE) &&
                        data.getExtras().containsKey(VerifyooConsts.EXTRA_BOOLEAN_IS_AUTHORIZED)) {

                    double score = data.getExtras().getDouble(VerifyooConsts.EXTRA_DOUBLE_SCORE);
                    boolean isAuthenticated = data.getExtras().getBoolean(VerifyooConsts.EXTRA_BOOLEAN_IS_AUTHORIZED);

                    score = score * 10000;
                    score = Math.round(score);
                    score = score / 10000;

//                    if (score == 0) {
//                        Random generator = new Random();
//                        int i = 10 - generator.nextInt(10);
//                        score = ((double) i) / 1000;
//                    }

                    String result = Double.toString(score * 100);
                    if (result.length() > 4) {
                        result = result.substring(0, 4);
                    }
                    result += "%";

                    mTxtScore.setText(result);

                    //mResultImage.setVisibility(View.VISIBLE);

                    setThresholdScore(score);
                    getThresholdScore();

                    if (score >= mThreshold) {
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
                        mTxtError.setText(errorMessage);
                    }
                }
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(UtilsGeneral.StoredTemplate == null) {
            mBtnAuth.setVisibility(View.GONE);
        }
        else {
            mBtnReg.setVisibility(View.GONE);
            mBtnAuth.setVisibility(View.VISIBLE);
            loadUserName();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_register) {
            onClickReg();
        }
        if (id == R.id.action_analysis) {
            onClickAnalysis();
        }
        if (id == R.id.reset_user) {
            resetUser();
        }

        return super.onOptionsItemSelected(item);
    }

    private void resetUser() {
        clearUserName();
        mTxtUser.setText("");
        mUserName = "";
        mTxtUser.setVisibility(View.VISIBLE);
        mBtnReg.setVisibility(View.VISIBLE);
        mBtnAuth.setVisibility(View.GONE);
    }

    private void onClickAnalysis() {
        Intent i = new Intent(getApplicationContext(), Analysis.class);
        startActivity(i);
    }
}
