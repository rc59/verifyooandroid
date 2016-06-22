package com.software.verifyoo.verifyooofflinesdk.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.software.verifyoo.verifyooofflinesdk.Abstract.GestureDrawProcessorAbstract;
import com.software.verifyoo.verifyooofflinesdk.Abstract.GestureInputAbstract;
import com.software.verifyoo.verifyooofflinesdk.R;
import com.software.verifyoo.verifyooofflinesdk.ServerAPI.API.ApiMgr;
import com.software.verifyoo.verifyooofflinesdk.Utils.AESCrypt;
import com.software.verifyoo.verifyooofflinesdk.Utils.Consts;
import com.software.verifyoo.verifyooofflinesdk.Utils.ConstsMessages;
import com.software.verifyoo.verifyooofflinesdk.Utils.Files;
import com.software.verifyoo.verifyooofflinesdk.Utils.UtilsConvert;
import com.software.verifyoo.verifyooofflinesdk.Utils.UtilsGeneral;
import com.software.verifyoo.verifyooofflinesdk.Utils.VerifyooConsts;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import Data.Comparison.CompareResultSummary;
import Data.Comparison.Interfaces.ICompareResult;
import Data.UserProfile.Extended.GestureExtended;
import Data.UserProfile.Extended.TemplateExtended;
import Data.UserProfile.Raw.Stroke;
import Data.UserProfile.Raw.Template;
import Logic.Comparison.GestureComparer;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;


public class VerifyooAuthenticate extends GestureInputAbstract {

    public String mCompanyName;
    public String mUserName;

    private double mAccumulatedScore;
    private ArrayList<Double> mListScores;

    private ArrayList<Stroke> mListStrokes;
    private Button mBtnAuth;
    private Button mBtnClear;
    private TextView mTextView;

    boolean mIsParamsValid;

    private Template mTemplateStored;
    private Template mTemplateAuth;

    ApiMgr mApiMgr;

    private double mXdpi;
    private double mYdpi;

    private int[] mInstructionIndexes;

    private int mStrokeCount = 0;

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
        IsShowTrail = true;
        initInstructionIndexes();
        mApiMgr = new ApiMgr();

        getDPI();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        setTitle("");
        mAccumulatedScore = 0;
        mListScores = new ArrayList<>();
        mTextView = (TextView) findViewById(R.id.textInstruction);
        mListStrokes = new ArrayList<>();

        mGesturesProcessor = new GestureDrawProcessorAuthenticate();
        super.init(mGesturesProcessor);

        Resources res = getResources();
        int colorGreen = Color.parseColor(Consts.VERIFYOO_GREEN);
        int colorGray = Color.parseColor(Consts.VERIFYOO_GRAY);

        mBtnAuth = (Button) findViewById(R.id.btnAuth);
        mBtnAuth.setBackgroundColor(colorGreen);

        mBtnClear = (Button) findViewById(R.id.btnClear);
        mBtnClear.setBackgroundColor(colorGray);

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

        if (UtilsGeneral.StoredTemplateExtended != null) {
            mTemplateStored = UtilsGeneral.StoredTemplateExtended;
        }
        else {
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
                    UtilsGeneral.StoredTemplateExtended = new TemplateExtended(mTemplateStored);
                    //mTemplateStored.Init();
                } catch (Exception exc) {
                    handleGeneralError(exc);
                }
            }
        }

        int idxTempInstruction;
        for(int idxGesture = 0; idxGesture < Consts.DEFAULT_NUM_REQ_GESTURES_AUTH; idxGesture++) {
            idxTempInstruction = mInstructionIndexes[idxGesture];
            mStrokeCount += mTemplateStored.ListGestures.get(idxTempInstruction).ListStrokes.size();
        }

        ArrayList<GestureExtended> listGestures = UtilsGeneral.StoredTemplateExtended.ListGestureExtended;
        mTextView.setText(listGestures.get(mInstructionIndexes[0]).Instruction);

        String title = getTitle(listGestures);
        setTitle(title);
    }

    private String getTitle(ArrayList<GestureExtended> listGestures) {
        String title = "";

        for(int idx = 0; idx < Consts.DEFAULT_NUM_REQ_GESTURES_AUTH; idx++) {
            title += UtilsConvert.InstructionCodeToInstruction(listGestures.get(mInstructionIndexes[idx]).Instruction);
            title += ", ";
        }

        title = title.substring(0, title.length() - 2);
        return title;
    }

    private void initInstructionIndexes() {
        mInstructionIndexes = new int[Consts.DEFAULT_NUM_REQ_GESTURES_REG];

        for(int idx = 0; idx < Consts.DEFAULT_NUM_REQ_GESTURES_REG; idx++) {
            mInstructionIndexes[idx] = idx;
        }

        int tempRandom1 = 0;
        int tempRandom2 = 0;
        boolean isRandomGenerated;
        int tempIndex;

        Random rand = new Random();
        for(int idx = 0; idx < Consts.DEFAULT_NUM_REQ_GESTURES_REG; idx++) {
            isRandomGenerated = false;
            while(!isRandomGenerated) {
                tempRandom1 = rand.nextInt(6);
                tempRandom2 = rand.nextInt(6);

                if (tempRandom1 != tempRandom2) {
                    isRandomGenerated = true;
                }
            }

            tempIndex = mInstructionIndexes[tempRandom1];
            mInstructionIndexes[tempRandom1] = mInstructionIndexes[tempRandom2];
            mInstructionIndexes[tempRandom2] = tempIndex;
        }
    }

    private void onClickAuth() {
        ArrayList<GestureExtended> listGesturesToUse = new ArrayList<>();

        TemplateExtended templateStoredExtended = UtilsGeneral.StoredTemplateExtended;
        ArrayList<GestureExtended> listGesturesStored = templateStoredExtended.ListGestureExtended;

        int totalStrokes = 0;
        int currentIdx;

        for(int idx = 0; idx < Consts.DEFAULT_NUM_REQ_GESTURES_AUTH; idx++) {
            currentIdx = mInstructionIndexes[idx];
            totalStrokes += listGesturesStored.get(currentIdx).ListStrokes.size();
            listGesturesToUse.add(listGesturesStored.get(currentIdx));
        }

        double finalScore = 0;
        boolean isAuth = false;

        String modelName = UtilsGeneral.getDeviceName();

        HashMap<String, Double> compareFilters = new HashMap<>();
        if (modelName != null && modelName.compareTo("LGE Nexus 5") != 0) {
            compareFilters = new HashMap<>();
            compareFilters.put("CompareGesturePressure", (double) 1);
            compareFilters.put("CompareGestureSurface", (double) 1);
        }

        if(totalStrokes == mListStrokes.size()) {
            Data.UserProfile.Raw.Gesture tempGestureBase;
            Data.UserProfile.Raw.Gesture tempGestureAuth;
            int numStrokes;

            GestureComparer gestureComparer = new GestureComparer(true);

            Template tempTemplateBase = new Template();
            tempTemplateBase.ListGestures = new ArrayList<>();

            Template tempTemplateAuth = new Template();
            tempTemplateAuth.ListGestures = new ArrayList<>();

            for(int idxGesture = 0; idxGesture < listGesturesToUse.size(); idxGesture++) {
                tempGestureBase = listGesturesToUse.get(idxGesture);
                numStrokes = tempGestureBase.ListStrokes.size();

                tempGestureAuth = new Data.UserProfile.Raw.Gesture();
                tempGestureAuth.Instruction = tempGestureBase.Instruction;
                tempGestureAuth.ListStrokes = new ArrayList<>();
                for(int idxStroke = 0; idxStroke < numStrokes; idxStroke++) {
                    tempGestureAuth.ListStrokes.add(mListStrokes.remove(0));
                }

                tempTemplateBase.ListGestures.add(listGesturesToUse.get(idxGesture));
                tempTemplateAuth.ListGestures.add(tempGestureAuth);
            }

            mTemplateAuth = tempTemplateAuth;

            TemplateExtended templateExtendedAuth = new TemplateExtended(tempTemplateAuth);

            String gestureResultSummary;
            StringBuilder stringBuilder = new StringBuilder();

            for(int idxGesture = 0; idxGesture < templateExtendedAuth.ListGestureExtended.size(); idxGesture++) {
                GestureExtended gestureExtendedAuth = templateExtendedAuth.ListGestureExtended.get(idxGesture);
                GestureExtended gestureExtendedBase = templateStoredExtended.ListGestureExtended.get(mInstructionIndexes[idxGesture]);

                if (compareFilters.keySet().size() > 0) {
                    gestureComparer.CompareGestures(gestureExtendedBase, gestureExtendedAuth, compareFilters);
                }
                else {
                    gestureComparer.CompareGestures(gestureExtendedBase, gestureExtendedAuth);
                }

                gestureResultSummary = resultSummaryToString(gestureComparer.GetResultsSummary(), gestureComparer.GetMinCosineDistance());

                double score = gestureComparer.GetScore();
                String strScore = String.valueOf(score);
                if (strScore.length() > 5) {
                    strScore = strScore.substring(0, 5);
                }
                stringBuilder.append(String.format("[%s: Score:%s %s], ", String.valueOf(idxGesture),  strScore, gestureResultSummary));

                mAccumulatedScore += score;
                mListScores.add(score);
            }

            UtilsGeneral.ResultAnalysis = stringBuilder.toString();
        }


        finalScore = getFinalScore(mListScores);

        if (finalScore > 0.85) {
            isAuth = true;

            UpdateTemplate();
        }

        Intent intent = this.getIntent();
        intent.putExtra(VerifyooConsts.EXTRA_DOUBLE_SCORE, finalScore);
        intent.putExtra(VerifyooConsts.EXTRA_BOOLEAN_IS_AUTHORIZED, isAuth);
        this.setResult(RESULT_OK, intent);
        finish();
    }

    private int FindOldestGestureByInstruction(String instruction) {
        Template storedTemplate = UtilsGeneral.StoredTemplate;

        int idxOldestGesture = -1;
        double tempStartTime = 0;
        double currentStartTime = 0;

        for(int idxGestureStored = 0; idxGestureStored < storedTemplate.ListGestures.size(); idxGestureStored++) {
            if (storedTemplate.ListGestures.get(idxGestureStored).Instruction.compareTo(instruction) == 0) {
                if (idxOldestGesture == -1) {
                    idxOldestGesture = idxGestureStored;
                    currentStartTime = storedTemplate.ListGestures.get(idxGestureStored).ListStrokes.get(0).ListEvents.get(0).EventTime;
                }
                else {
                    tempStartTime = storedTemplate.ListGestures.get(idxGestureStored).ListStrokes.get(0).ListEvents.get(0).EventTime;

                    if (tempStartTime < currentStartTime) {
                        idxOldestGesture = idxGestureStored;
                        currentStartTime = tempStartTime;
                    }
                }
            }
        }

        return idxOldestGesture;
    }

    private void UpdateTemplate() {
        Template storedTemplate = UtilsGeneral.StoredTemplate;

        Data.UserProfile.Raw.Gesture tempGesture;

        HashMap<String, Double> hashGestureCount = new HashMap<>();
        String tempInstruction;
        double tempGestureCount;

        int maxGestureCount = 5;

        for(int idxGestureStored = 0; idxGestureStored < storedTemplate.ListGestures.size(); idxGestureStored++) {
            tempInstruction = storedTemplate.ListGestures.get(idxGestureStored).Instruction;
            if (!hashGestureCount.containsKey(tempInstruction)) {
                hashGestureCount.put(tempInstruction, (double) 1);
            }
            else {
                tempGestureCount = hashGestureCount.get(tempInstruction);
                hashGestureCount.put(tempInstruction, (tempGestureCount + 1));
            }
        }

        int idxGestureToRemove;
        for(int idxGestureAuth = 0; idxGestureAuth < mTemplateAuth.ListGestures.size(); idxGestureAuth++) {
            tempGesture = mTemplateAuth.ListGestures.get(idxGestureAuth);

            for(int idxGestureStored = 0; idxGestureStored < Consts.DEFAULT_NUM_REQ_GESTURES_REG; idxGestureStored++) {
                if (storedTemplate.ListGestures.get(idxGestureStored).Instruction.compareTo(tempGesture.Instruction) == 0) {
                    if (hashGestureCount.get(storedTemplate.ListGestures.get(idxGestureStored).Instruction) >= maxGestureCount) {
                        idxGestureToRemove = FindOldestGestureByInstruction(storedTemplate.ListGestures.get(idxGestureStored).Instruction);
                        storedTemplate.ListGestures.remove(idxGestureToRemove);
                        storedTemplate.ListGestures.add(idxGestureToRemove, tempGesture);
                    }
                    else {
                        storedTemplate.ListGestures.add(tempGesture);
                    }
                }
            }
        }

        UtilsGeneral.StoredTemplate = storedTemplate;
        UtilsGeneral.StoredTemplateExtended = new TemplateExtended(storedTemplate);

        new TemplateStorer().execute("");
    }

    private class TemplateStorer extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            JSONSerializer serializer = new JSONSerializer();

            Template template = UtilsGeneral.StoredTemplate;

            String jsonTemplate = serializer.deepSerialize(template);

            try {
                String key = UtilsGeneral.GetUserKey(mUserName);
                jsonTemplate = AESCrypt.encrypt(key, jsonTemplate);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
                handleError(ConstsMessages.E00002);
            }

            OutputStreamWriter outputStreamWriter = null;
            try {
                String fileName = Files.GetFileName(mUserName);
                deleteFile(fileName);

                FileOutputStream f = openFileOutput(fileName, Context.MODE_PRIVATE);
                outputStreamWriter = new OutputStreamWriter(f);
            } catch (FileNotFoundException e) {
                handleError(ConstsMessages.E00003);
                e.printStackTrace();
            }
            Files.writeToFile(jsonTemplate, outputStreamWriter);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private String resultSummaryToString(CompareResultSummary compareResultSummary, double cosineScore) {
        StringBuilder stringBuilder = new StringBuilder();

        ICompareResult compareResult;
        String paramName;
        double paramScore;
        double paramWeight;

        String paramScoreStr;
        String paramWeightStr;

        String tempParamSummary;

        int maxLength = 5;
        int currentLength;

        Collections.sort(compareResultSummary.ListCompareResults, new Comparator<ICompareResult>() {
            @Override
            public int compare(ICompareResult score1, ICompareResult score2) {
                if (Math.abs(score1.GetValue()) > Math.abs(score2.GetValue())) {
                    return 1;
                }
                if (Math.abs(score1.GetValue()) < Math.abs(score2.GetValue())) {
                    return -1;
                }
                return 0;
            }
        });


        tempParamSummary = String.format("{Cosine Distance: %s}, ", cosineScore);
        stringBuilder.append(tempParamSummary);

        for(int idx = 0; idx < compareResultSummary.ListCompareResults.size(); idx++) {
            compareResult = compareResultSummary.ListCompareResults.get(idx);

            paramName = compareResult.GetName();
            paramScore = compareResult.GetValue();
            paramWeight = compareResult.GetWeight();

            paramScoreStr = String.valueOf(paramScore);
            paramWeightStr = String.valueOf(paramWeight);

            if (paramScoreStr.length() > maxLength) {
                paramScoreStr = paramScoreStr.substring(0, maxLength);
            }
            if (paramWeightStr.length() > maxLength) {
                paramWeightStr = paramWeightStr.substring(0, maxLength);
            }

            tempParamSummary = String.format("{%s: %s (%s)}, ", paramName, paramScoreStr, paramWeightStr);

            stringBuilder.append(tempParamSummary);
        }

        return stringBuilder.toString();
    }

    private Data.UserProfile.Raw.Gesture getGestureByInstruction(ArrayList<Data.UserProfile.Raw.Gesture> listGestures, String instructionCode) {
        Data.UserProfile.Raw.Gesture selectedGesture = null;
        for(int idx = 0; idx < listGestures.size(); idx++) {
            if (listGestures.get(idx).Instruction.compareTo(instructionCode) == 0) {
                selectedGesture = listGestures.get(idx);
                break;
            }
        }

        return selectedGesture;
    }

    private double getFinalScore(ArrayList<Double> mListScores) {
        if (mListScores.size() < 3) {
            return 0;
        }

        Collections.sort(mListScores);

        double scores = 0;
        double weights = 0;
        double tempWeight;
//        mListScores.remove(0);
//        for(int idx = 0; idx < mListScores.size(); idx++) {
//            tempWeight = idx + 1;
//            weights += tempWeight;
//
//            scores += tempWeight * mListScores.get(idx);
//            scores += mListScores.get(idx);
//        }

        scores = mListScores.get(0) * 1 + mListScores.get(1) * 1.25 + mListScores.get(2) * 1.5;

        //double finalScore = scores / mListScores.size();
        double finalScore = scores / 3.75;

        return finalScore;
    }

    private void onClickClear() {
        UtilsGeneral.AuthStartTime = 0;
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
            UtilsGeneral.AuthEndTime = new Date().getTime();

            unRegisterSensors();
            super.onGesture(overlay, event);

            Gesture gesture  = overlay.getGesture();
            double currentLength = gesture.getLength();

            int strokesCount = gesture.getStrokes().size();
            if (strokesCount > 1) {
                gesture.getStrokes().remove(0);
            }

            if (currentLength > 50) {
                super.InitPrevStroke(mGesturesProcessor.getStroke(), mListStrokes, gesture.getLength());

                Stroke tempStroke = mGesturesProcessor.getStroke();
                tempStroke.Xdpi = mXdpi;
                tempStroke.Ydpi = mYdpi;
                tempStroke.Length = gesture.getLength();
                mListStrokes.add(tempStroke);
                mGesturesProcessor.clearStroke();
            }
        }
    }
}