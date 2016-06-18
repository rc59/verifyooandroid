package com.software.verifyoo.verifyoosdk;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.Date;

import Data.UserProfile.Extended.TemplateExtended;
import Data.UserProfile.Raw.Template;
import flexjson.JSONDeserializer;


public class MainActivity extends ActionBarActivity {

    TextView mTxtScore;
    TextView mTxtStatus;
    TextView mTxtTotalTime;

    ImageView mResultImage;

    String mUserName;
    String mCompany;

    TextView mTxtError;

    Button mBtnAuth;
    ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserName = "user-roy";
        mCompany = "Verifyoo";

        init();
        postInit();
    }

    protected void loadTemplate() {
        InputStream inputStream = null;
        try {
            inputStream = openFileInput(Files.GetFileName(mUserName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
//                mTxtError.setText(e.getMessage());
        }
        String storedTemplate = Files.readFromFile(inputStream);

        if (storedTemplate.length() > 0) {

            JSONDeserializer<Template> deserializer = new JSONDeserializer<Template>();
            try {
                try {
                    String key = UtilsGeneral.GetUserKey(mUserName);
                    storedTemplate = AESCrypt.decrypt(key, storedTemplate);
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
//                        mTxtError.setText(e.getMessage());
                }

                Object listGesturesObj = deserializer.deserialize(storedTemplate);
                TemplateExtended templateExtended = new TemplateExtended((Template) listGesturesObj);
                UtilsGeneral.StoredTemplateExtended = templateExtended;
                //mTemplateStored.Init();
            } catch (Exception e) {
//                    mTxtError.setText(e.getMessage());
            }

        }
    }

    private class TemplateLoader extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            loadTemplate();
//            TextView txt = (TextView) findViewById(R.id.output);
//            txt.setText("Executed");
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            InitScore();
            mBtnAuth.setVisibility(View.VISIBLE);
            mImage.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPreExecute() {
            mBtnAuth.setVisibility(View.INVISIBLE);
            mImage.setVisibility(View.INVISIBLE);
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

        setTitle("Verifyoo Demo");
        Resources res = getResources();
        int color = Color.parseColor(Consts.VERIFYOO_BLUE);

        TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setTextColor(color);

        mTxtError = (TextView) findViewById(R.id.txtErrorMsg);

        mImage = (ImageView) findViewById(R.id.welcomeImage);
        mImage.setImageResource(R.drawable.logo);

        mResultImage = (ImageView) findViewById(R.id.resultImage);

        mTxtScore = (TextView) findViewById(R.id.txtScore);
        mTxtStatus = (TextView) findViewById(R.id.txtStatus);
        mTxtTotalTime = (TextView) findViewById(R.id.txtTotalTime);

        mTxtScore.setTextColor(color);
        mTxtStatus.setTextColor(color);

        mBtnAuth = (Button) findViewById(R.id.btnAuth);
        Button btnReg = (Button) findViewById(R.id.btnReg);
        Button btnHack = (Button) findViewById(R.id.btnHack);

        mBtnAuth.setBackgroundColor(color);
        btnReg.setBackgroundColor(color);
        btnHack.setBackgroundColor(color);

        mBtnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAuth();
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
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
    }

    private void InitScore() {
        mTxtScore.setText("No Score");
        mTxtStatus.setText("");
        mTxtError.setText("");
    }

    private void onClickReg() {
        InitScore();
        Intent i = new Intent(getApplicationContext(), VerifyooRegister.class);
        i.putExtra(VerifyooConsts.EXTRA_STRING_USER_NAME, mUserName);
        i.putExtra(VerifyooConsts.EXTRA_STRING_COMPANY_NAME, mCompany);
        i.putExtra(VerifyooConsts.EXTRA_BOOLEAN_IS_USE_REPEAT_GESTURE, false);

        mTxtTotalTime.setText("");
        startActivityForResult(i, 1);
    }

    private void onClickAuth() {
        mTxtScore.setText("Loading...Please wait");
        InitScore();
        Intent i = new Intent(getApplicationContext(), VerifyooAuthenticate.class);
        i.putExtra(VerifyooConsts.EXTRA_STRING_USER_NAME, mUserName);
        i.putExtra(VerifyooConsts.EXTRA_STRING_COMPANY_NAME, mCompany);

        mTxtTotalTime.setText("");
        startActivityForResult(i, 1);
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

        mResultImage.setVisibility(View.GONE);
        if (resultCode == RESULT_OK) {
            if (UtilsGeneral.StartTime != 0) {
                double currTime = new Date().getTime();
                double totalTime = currTime - UtilsGeneral.StartTime;
                totalTime = totalTime / 1000;

                mTxtTotalTime.setText(String.format("Total Time: %s seconds", String.valueOf(totalTime)));
                UtilsGeneral.StartTime = 0;
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

                    mResultImage.setVisibility(View.VISIBLE);
                    if (score >= 0.85) {
                        mResultImage.setImageResource(R.drawable.success);
                        mTxtStatus.setText("Authorized");
                        mTxtStatus.setTextColor(Color.GREEN);
                    } else {
                        mResultImage.setImageResource(R.drawable.failed);
                        mTxtStatus.setText("Not Authorized");
                        mTxtStatus.setTextColor(Color.RED);
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

        return super.onOptionsItemSelected(item);
    }

    private void onClickAnalysis() {
        Intent i = new Intent(getApplicationContext(), Analysis.class);
        startActivity(i);
    }
}
