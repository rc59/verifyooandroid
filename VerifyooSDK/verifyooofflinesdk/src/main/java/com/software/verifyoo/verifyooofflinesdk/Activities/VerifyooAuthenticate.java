package com.software.verifyoo.verifyooofflinesdk.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.software.verifyoo.verifyooofflinesdk.Abstract.GestureDrawProcessorAbstract;
import com.software.verifyoo.verifyooofflinesdk.Abstract.GestureInputAbstract;
import com.software.verifyoo.verifyooofflinesdk.R;
import com.software.verifyoo.verifyooofflinesdk.ServerAPI.API.ApiMgr;
import com.software.verifyoo.verifyooofflinesdk.ServerAPI.API.ApiMgrStoreDataParams;
import com.software.verifyoo.verifyooofflinesdk.Utils.AESCrypt;
import com.software.verifyoo.verifyooofflinesdk.Utils.Consts;
import com.software.verifyoo.verifyooofflinesdk.Utils.ConstsMessages;
import com.software.verifyoo.verifyooofflinesdk.Utils.Files;
import com.software.verifyoo.verifyooofflinesdk.Utils.UtilsGeneral;
import com.software.verifyoo.verifyooofflinesdk.Utils.VerifyooConsts;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import Data.UserProfile.Extended.GestureExtended;
import Data.UserProfile.Extended.TemplateExtended;
import Data.UserProfile.Raw.Stroke;
import Data.UserProfile.Raw.Template;
import Logic.Comparison.GestureComparer;
import flexjson.JSONDeserializer;


public class VerifyooAuthenticate extends GestureInputAbstract {

    public String mCompanyName;
    public String mUserName;

    private double mAccumulatedScore;

    private ArrayList<Stroke> mListStrokes;
    private Button mBtnAuth;
    private Button mBtnClear;
    private TextView mTextView;

    boolean mIsParamsValid;

    private int mCurrentGesture;

    private Template mTemplateStored;

    ApiMgr mApiMgr;

    private double mXdpi;
    private double mYdpi;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyoo_authenticate);

        try {
            preInit();
            checkParameters();
            if (mIsParamsValid) {
                init();
            }
        } catch (Exception exc) {
            handleGeneralError(exc);
        }
    }

    private void preInit() {
        mIsParamsValid = true;
    }

    private void checkParameters() {
        if (getIntent().getExtras() != null &&
                getIntent().getExtras().containsKey(VerifyooConsts.EXTRA_STRING_COMPANY_NAME) &&
                getIntent().getExtras().containsKey(VerifyooConsts.EXTRA_STRING_USER_NAME)) {

            mCompanyName = getIntent().getStringExtra(VerifyooConsts.EXTRA_STRING_COMPANY_NAME);
            mUserName = getIntent().getStringExtra(VerifyooConsts.EXTRA_STRING_USER_NAME);
        }

        if (mUserName == null || mUserName.length() == 0) {
            mIsParamsValid = false;
            handleError(ConstsMessages.E00001);
        }
        if (mCompanyName == null || mCompanyName.length() == 0) {
            mIsParamsValid = false;
            handleError(ConstsMessages.E00005);
        }
    }

    private void init() {
        mApiMgr = new ApiMgr();

        getDPI();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        setTitle("");
        mCurrentGesture = 0;
        mAccumulatedScore = 0;
        mTextView = (TextView) findViewById(R.id.textInstruction);
        mListStrokes = new ArrayList<>();

        mGesturesProcessor = new GestureDrawProcessorAuthenticate();
        super.init(mGesturesProcessor);

        Resources res = getResources();
        int color = Color.parseColor(Consts.VERIFYOO_BLUE);

        mBtnAuth = (Button) findViewById(R.id.btnAuth);
        mBtnAuth.setBackgroundColor(color);

        mBtnClear = (Button) findViewById(R.id.btnClear);
        mBtnClear.setBackgroundColor(color);

        mBtnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onClickAuth();
                } catch (Exception exc) {
                    handleGeneralError(exc);
                }
            }
        });

        mBtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onClickClear();
                }
                catch (Exception exc) {
                    handleGeneralError(exc);
                }
            }
        });

        InputStream inputStream = null;
        try {
            inputStream = openFileInput(Files.GetFileName(mUserName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            handleError(ConstsMessages.E00002);
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
                    handleError(ConstsMessages.E00003);
                }

                Object listGesturesObj = deserializer.deserialize(storedTemplate);
                mTemplateStored = (Template) listGesturesObj;
                //mTemplateStored.Init();
            } catch (Exception exc) {
                handleGeneralError(exc);
            }

            ArrayList<Data.UserProfile.Raw.Gesture> listGestures = mTemplateStored.ListGestures;
            mTextView.setText(listGestures.get(0).Instruction);
            setTitle(listGestures.get(0).Instruction);
        }
    }

    private void onClickAuth() {
        mBtnAuth.setEnabled(false);
        ArrayList<Data.UserProfile.Raw.Gesture> listGesturesStored = mTemplateStored.ListGestures;

        ArrayList<Stroke> listStrokesStored = listGesturesStored.get(mCurrentGesture).ListStrokes;

        Data.UserProfile.Raw.Gesture gesture1 = new Data.UserProfile.Raw.Gesture();
        gesture1.ListStrokes = mListStrokes;

        Data.UserProfile.Raw.Gesture gesture2 = new Data.UserProfile.Raw.Gesture();
        gesture2.ListStrokes = listStrokesStored;

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        GestureComparer gestureComparer = new GestureComparer(true);

        try {
            Template tempTemplateAuth = new Template();
            tempTemplateAuth.ListGestures = new ArrayList<>();
            tempTemplateAuth.ListGestures.add(gesture1);

            Template tempTemplateReg = new Template();
            tempTemplateReg.ListGestures = new ArrayList<>();
            tempTemplateReg.ListGestures.add(gesture2);

            WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

            String state = "Authenticate";
            if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("IsHack")) {
                state = "Hack";
            }

            ApiMgrStoreDataParams params = new ApiMgrStoreDataParams(mUserName, mCompanyName, state, wm, mXdpi, mYdpi, true);
            mApiMgr.StoreData(params, tempTemplateAuth);
        } catch (Exception exc) {
            handleGeneralError(exc);
        }

        Template tempTemplate1 = new Template();
        Template tempTemplate2 = new Template();

        gesture1.Instruction = "RLETTER";
        tempTemplate1.ListGestures.add(gesture1);
        gesture2.Instruction = "RLETTER";
        tempTemplate2.ListGestures.add(gesture2);

        TemplateExtended templateExtended1 = new TemplateExtended(tempTemplate1);
        TemplateExtended templateExtended2 = new TemplateExtended(tempTemplate2);

        GestureExtended gestureExtended1 = templateExtended1.ListGestureExtended.get(0);
        GestureExtended gestureExtended2 = templateExtended2.ListGestureExtended.get(0);

        gestureComparer.CompareGestures(gestureExtended1, gestureExtended2);

        double score = gestureComparer.GetScore();

        mAccumulatedScore += score;
        mCurrentGesture++;
        if (mCurrentGesture >= Consts.DEFAULT_NUM_REQ_GESTURES_AUTH) {
            double finalScore = mAccumulatedScore / mCurrentGesture;
            boolean isAuth = false;

            if (finalScore > 0.85) {
                isAuth = true;
            }

            Intent intent = this.getIntent();
            intent.putExtra(VerifyooConsts.EXTRA_DOUBLE_SCORE, finalScore);
            intent.putExtra(VerifyooConsts.EXTRA_BOOLEAN_IS_AUTHORIZED, isAuth);
            this.setResult(RESULT_OK, intent);
            finish();
        }
        else {
            mListStrokes = new ArrayList<>();

            listGesturesStored = mTemplateStored.ListGestures;
            mTextView.setText(listGesturesStored.get(mCurrentGesture).Instruction);
            setTitle(listGesturesStored.get(mCurrentGesture).Instruction);

            clearOverlay();
        }
    }

    private void onClickClear() {
        clearOverlay();
        mListStrokes = new ArrayList<>();
    }

    private void handleError(String errorMessage) {
        Intent intent = this.getIntent();
        intent.putExtra(VerifyooConsts.EXTRA_STRING_ERROR_MESSAGE, errorMessage);
        this.setResult(RESULT_OK, intent);
        finish();
    }

    private void handleGeneralError(Exception exc) {
        String errorMessage = String.format(ConstsMessages.E00004, exc.getMessage());
        handleError(errorMessage);
    }

    private void getDPI() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mXdpi = metrics.xdpi;
        mYdpi = metrics.ydpi;
    }

    private void caluclateDPI() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int f = metrics.densityDpi;

        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        int dpW = (int)(width / (metrics.density));
        int dpH = (int)(height / (metrics.density));

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mXdpi = Math.pow(dm.widthPixels/dm.xdpi,2);
        mYdpi = Math.pow(dm.heightPixels/dm.ydpi,2);
    }

    @Override
     public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_verifyoo_authenticate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class GestureDrawProcessorAuthenticate extends GestureDrawProcessorAbstract {
        public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
            unRegisterSensors();
            super.onGesture(overlay, event);

            Gesture gesture  = overlay.getGesture();

            int strokesCount = gesture.getStrokes().size();
            if (strokesCount > 1) {
                gesture.getStrokes().remove(0);
            }

            super.InitPrevStroke(mGesturesProcessor.getStroke(), mListStrokes, gesture.getLength());

            Stroke tempStroke = mGesturesProcessor.getStroke();
            tempStroke.Xdpi = mXdpi;
            tempStroke.Ydpi = mYdpi;
            tempStroke.Length = gesture.getLength();
            mListStrokes.add(tempStroke);
            mGesturesProcessor.clearStroke();
            mBtnAuth.setEnabled(true);

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    clearOverlay();
                }
            };

            handler.postDelayed(runnable, 100);
        }
    }
}
