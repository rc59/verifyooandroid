package com.software.verifyoo.verifyooofflinesdk.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.software.verifyoo.verifyooofflinesdk.Abstract.GestureDrawProcessorAbstract;
import com.software.verifyoo.verifyooofflinesdk.Abstract.GestureInputAbstract;
import com.software.verifyoo.verifyooofflinesdk.R;
import com.software.verifyoo.verifyooofflinesdk.ServerAPI.API.ApiMgr;
import com.software.verifyoo.verifyooofflinesdk.ServerAPI.API.ApiMgrStoreDataParams;
import com.software.verifyoo.verifyooofflinesdk.Utils.AESCrypt;
import com.software.verifyoo.verifyooofflinesdk.Utils.Consts;
import com.software.verifyoo.verifyooofflinesdk.Utils.ConstsMessages;
import com.software.verifyoo.verifyooofflinesdk.Utils.Files;
import com.software.verifyoo.verifyooofflinesdk.Utils.UtilsConvert;
import com.software.verifyoo.verifyooofflinesdk.Utils.UtilsGeneral;
import com.software.verifyoo.verifyooofflinesdk.Utils.UtilsInstructions;
import com.software.verifyoo.verifyooofflinesdk.Utils.VerifyooConsts;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import Data.UserProfile.Extended.GestureExtended;
import Data.UserProfile.Extended.TemplateExtended;
import Data.UserProfile.Raw.Stroke;
import Data.UserProfile.Raw.Template;
import Logic.Comparison.GestureComparer;
import flexjson.JSONSerializer;

public class VerifyooRegister extends GestureInputAbstract {

    public String mCompanyName;
    public String mUserName;

    private ArrayList<Data.UserProfile.Raw.Gesture> mListGestures;

    private ArrayList<Data.UserProfile.Raw.Stroke> mListStrokes;
    private ArrayList<Data.UserProfile.Raw.Stroke> mListStrokesTemp;

    private Button mBtnSave;
    private Button mBtnClear;

    private TextView mTextStatus;
    private View mLayoutStatus;

    boolean mIsRequiredToRepeatGesture;

    boolean mIsParamsValid;
    boolean mIsFirstGestureEntered;

    int mConsecutiveFalses;

    ApiMgr mApiMgr;

    private double mXdpi;
    private double mYdpi;

    private int mNumberRepeats;
    private int mCurrentGesture;

    private ImageView mImageWait;
    private TextView mTextViewWait;

    private HashMap<String, Integer> mHashNumStrokesPerGesture;

    private int[] mInstructionIndexes;

    private class TemplateStorer extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            storeTemplate();
//            TextView txt = (TextView) findViewById(R.id.output);
//            txt.setText("Executed");
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

        }

        @Override
        protected void onPreExecute() {
            mImageWait.setVisibility(View.VISIBLE);
            mTextViewWait.setVisibility(View.VISIBLE);

            mOverlay.setVisibility(View.INVISIBLE);
            mBtnSave.setVisibility(View.INVISIBLE);
            mBtnClear.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyoo_register);

        try {
            mIsParamsValid = true;
            checkParameters();
            if (mIsParamsValid) {
                init();
            }
        } catch (Exception exc) {
            handleGeneralError(exc);
        }
    }

    private void checkParameters() {
        if (getIntent().getExtras() != null &&
                getIntent().getExtras().containsKey(VerifyooConsts.EXTRA_STRING_COMPANY_NAME) &&
                getIntent().getExtras().containsKey(VerifyooConsts.EXTRA_STRING_USER_NAME)) {

            mCompanyName = getIntent().getStringExtra(VerifyooConsts.EXTRA_STRING_COMPANY_NAME);
            mUserName = getIntent().getStringExtra(VerifyooConsts.EXTRA_STRING_USER_NAME);
            mIsRequiredToRepeatGesture = getIntent().getBooleanExtra(VerifyooConsts.EXTRA_BOOLEAN_IS_USE_REPEAT_GESTURE, true);
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

    private void updateTitle(String instruction) {
        int total = (Consts.DEFAULT_NUM_REQ_GESTURES_REG * Consts.DEFAULT_NUM_REPEATS_PER_INSTRUCTION);
        int currentGeseture = mListGestures.size() + 1;
        String completion = String.format("(%s/%s)", String.valueOf(currentGeseture), String.valueOf(total));
        String title = String.format("%s %s", completion, instruction);

        setTitle(title);
    }

    private void init() {
        initInstructionIndexes();
        mHashNumStrokesPerGesture = new HashMap<>();
        mNumberRepeats = 1;
        mCurrentGesture = 0;
        mConsecutiveFalses = 0;
        getDPI();
        mApiMgr = new ApiMgr();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        mListStrokes = new ArrayList<>();
        mListStrokesTemp = new ArrayList<>();
        mListGestures = new ArrayList<>();

        updateTitle(getTitleString(UtilsInstructions.GetInstruction(mInstructionIndexes[0])));

        mIsFirstGestureEntered = false;

        mGesturesProcessor = new GestureDrawProcessorRegister();
        super.init(mGesturesProcessor);

        Resources res = getResources();
        int colorGreen = Color.parseColor(Consts.VERIFYOO_GREEN);
        int colorGray = Color.parseColor(Consts.VERIFYOO_GRAY);

        mBtnSave = (Button) findViewById(R.id.btnSave);
        mBtnSave.setBackgroundColor(colorGreen);

        mBtnClear = (Button) findViewById(R.id.btnClear);
        mBtnClear.setBackgroundColor(colorGray);



        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onClickSave();
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
                } catch (Exception exc) {
                    handleGeneralError(exc);
                }
            }
        });

        mImageWait = (ImageView) findViewById(R.id.imageWait);
        mImageWait.setImageResource(R.drawable.wait);

        mTextViewWait = (TextView) findViewById(R.id.txtWait);

        mTextStatus = (TextView) findViewById(R.id.textStatus);
        mLayoutStatus = findViewById(R.id.layoutStatus);
        UtilsGeneral.AuthStartTime = new Date().getTime();
    }

    private String getTitleString(String instructionCode) {
        String title = UtilsConvert.InstructionCodeToInstruction(instructionCode);

        title = String.format("DRAW %s", title);

        return title;
    }

    private void initInstructionIndexes() {
        mInstructionIndexes = UtilsGeneral.generateInstructionsList(Consts.TOTAL_NUM_GESTURES);
    }

    private void onClickClear() {
        mBtnSave.setVisibility(View.INVISIBLE);
        mBtnClear.setVisibility(View.INVISIBLE);

        mIsFirstGestureEntered = false;
        clearOverlay();
        mBtnSave.setEnabled(false);

        mListStrokes = new ArrayList<>();
        mListStrokesTemp = new ArrayList<>();
        updateStatus("");
    }

    private void updateStatus(String status) {
        mTextStatus.setText(status);
        if (status.length() > 0) {
            mLayoutStatus.setVisibility(View.VISIBLE);
        }
        else {
            mLayoutStatus.setVisibility(View.GONE);
        }
    }

    private void onClickSave() {
        clearOverlay();

        boolean isNumStrokesValid = true;
        String currentInstruction = UtilsInstructions.GetInstruction(mInstructionIndexes[mCurrentGesture]);
        if(mHashNumStrokesPerGesture.containsKey(currentInstruction))
        {
            int numStrokes = mHashNumStrokesPerGesture.get(currentInstruction);
            if (numStrokes != mListStrokes.size()) {
                isNumStrokesValid = false;
            }

            if (isNumStrokesValid) {
                Data.UserProfile.Raw.Gesture originalGesture = mListGestures.get(mCurrentGesture);
                Data.UserProfile.Raw.Gesture currentGesture = new Data.UserProfile.Raw.Gesture();
                currentGesture.ListStrokes = mListStrokes;
                currentGesture.Instruction = originalGesture.Instruction;

                Template originalTemplate = new Template();
                Template currentTemplate = new Template();

                originalTemplate.ListGestures = new ArrayList<>();
                originalTemplate.ListGestures.add(originalGesture);

                currentTemplate.ListGestures = new ArrayList<>();
                currentTemplate.ListGestures.add(currentGesture);

                TemplateExtended originalTemplateExtended = new TemplateExtended(originalTemplate);
                TemplateExtended currentTemplateExtended = new TemplateExtended(currentTemplate);

                GestureExtended originalGestureExtended = originalTemplateExtended.ListGestureExtended.get(0);
                GestureExtended currentGestureExtended = currentTemplateExtended.ListGestureExtended.get(0);

                GestureComparer comparer = new GestureComparer(true);
                comparer.CompareGestures(originalGestureExtended, currentGestureExtended);

                double cosineDistance = comparer.GetMinCosineDistance();

                if (cosineDistance < 1) {
                    isNumStrokesValid = false;
                }
            }
        }

        if (isNumStrokesValid) {
            mConsecutiveFalses = 0;
            mBtnSave.setEnabled(false);

            Data.UserProfile.Raw.Gesture gesture = new Data.UserProfile.Raw.Gesture();

            gesture.ListStrokes = mListStrokes;
            gesture.Instruction = UtilsInstructions.GetInstruction(mInstructionIndexes[mCurrentGesture]);
            mListGestures.add(gesture);
            mListStrokes = new ArrayList<>();

            if (!mHashNumStrokesPerGesture.containsKey(gesture.Instruction)) {
                mHashNumStrokesPerGesture.put(gesture.Instruction, gesture.ListStrokes.size());
            }

            boolean isUpdateTitle = true;
            mCurrentGesture++;

            if (mNumberRepeats >= Consts.DEFAULT_NUM_REPEATS_PER_INSTRUCTION) {
                if (mCurrentGesture >= Consts.DEFAULT_NUM_REQ_GESTURES_REG) {
                    setTitle("");
                    isUpdateTitle = false;

                    SharedPreferences prefs = getSharedPreferences("VerifyooPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putFloat("Score", (float) -1);
                    editor.commit();
                    new TemplateStorer().execute("");
                }
            }
            else {
                if (mCurrentGesture >= Consts.DEFAULT_NUM_REQ_GESTURES_REG) {
                    mCurrentGesture = 0;
                    mNumberRepeats++;
                }
            }

            if (isUpdateTitle) {
                updateTitle(getTitleString(UtilsInstructions.GetInstruction(mInstructionIndexes[mCurrentGesture])));
            }
        }
        else {
            updateStatus("The gestures are not similar");
            mListStrokes.clear();
            mConsecutiveFalses++;

            if (mConsecutiveFalses >= 2) {
                Toast.makeText(getApplicationContext(), "You have been rejected twice, we recommend that you restart the registration.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void storeTemplate() {
        Data.UserProfile.Raw.Template template = new Data.UserProfile.Raw.Template();
        template.ListGestures = mListGestures;

        try {
            WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            ApiMgrStoreDataParams params = new ApiMgrStoreDataParams(mUserName, mCompanyName, "Register", wm, mXdpi, mYdpi, true);
            mApiMgr.StoreData(params, template);
        } catch (Exception exc) {
            handleGeneralError(exc);
        }

        JSONSerializer serializer = new JSONSerializer();

//        TemplateExtended templateExtended = new TemplateExtended(template);
//        GestureExtended gestureExtended;
//        StrokeExtended strokeExtended;
//
//        for(int idxGesture = 0; idxGesture < templateExtended.ListGestureExtended.size(); idxGesture++) {
//            templateExtended.ListGestureExtended.get(idxGesture);
//
//            gestureExtended = templateExtended.ListGestureExtended.get(idxGesture);
//            for(int idxStroke = 0; idxStroke < gestureExtended.ListStrokesExtended.size(); idxStroke++) {
//                strokeExtended = gestureExtended.ListStrokesExtended.get(idxStroke);
//                strokeExtended.ListEventsExtended.clear();
//                strokeExtended.ListEvents.clear();
//            }
//        }

        TemplateExtended templateExtended = new TemplateExtended(template);
        UtilsGeneral.StoredTemplate = template;
        UtilsGeneral.StoredTemplateExtended = templateExtended;

        String jsonTemplate = serializer.deepSerialize(template);

        try {
            String key = UtilsGeneral.GetUserKey(Consts.STORAGE_NAME);
            jsonTemplate = AESCrypt.encrypt(key, jsonTemplate);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            handleError(ConstsMessages.E00002);
        }

        OutputStreamWriter outputStreamWriter = null;
        try {
            String fileName = Files.GetFileName(Consts.STORAGE_NAME);
            deleteFile(fileName);

            FileOutputStream f = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStreamWriter = new OutputStreamWriter(f);
        } catch (FileNotFoundException e) {
            handleError(ConstsMessages.E00003);
            e.printStackTrace();
        }
        Files.writeToFile(jsonTemplate, outputStreamWriter);

        Intent intent = this.getIntent();
        this.setResult(RESULT_OK, intent);
        saveUserName();
        finish();
    }

    private void saveUserName() {
        SharedPreferences prefs = getSharedPreferences("VerifyooPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Username", mUserName);
        editor.commit();
    }

    private void getDPI() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mXdpi = metrics.xdpi;
        mYdpi = metrics.ydpi;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_verifyoo_register, menu);
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

//    public void CheckGestureStrength() {
//        Data.UserProfile.Raw.Gesture tempGesture = new Data.UserProfile.Raw.Gesture();
//        tempGesture.ListStrokes = mListStrokes;
//        tempGesture.Instruction = UtilsInstructions.GetInstruction(mCurrentGesture);
//
//        Template tempTemplate = new Template();
//        tempTemplate.ListGestures = mListGestures;
//        tempTemplate.ListGestures.add(tempGesture);
//
//        try {
//            TemplateExtended tempTemplateExtended = new TemplateExtended(tempTemplate);
//            GestureExtended tempGestureExtended = tempTemplateExtended.ListGestureExtended.get(mCurrentGesture);
//            String gestureStrength = tempGestureExtended.GetGestureStrength();
//            setGestureStrength(gestureStrength);
//        } catch (Exception exc) {
//            handleGeneralError(exc);
//        }
//
//        int idxGestureToRemove = tempTemplate.ListGestures.size();
//        tempTemplate.ListGestures.remove(idxGestureToRemove - 1);
//
//    }

    public class GestureDrawProcessorRegister extends GestureDrawProcessorAbstract {
        public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
            UtilsGeneral.AuthEndTime = new Date().getTime();

            unRegisterSensors();
            super.onGesture(overlay, event);

            updateStatus("");
            mBtnSave.setEnabled(true);
            Gesture gesture = overlay.getGesture();
            double currentLength = gesture.getLength();

            int strokesCount = gesture.getStrokes().size();
            if (strokesCount > 1) {
                gesture.getStrokes().remove(0);
            }

            Stroke tempStroke = mGesturesProcessor.getStroke();
            tempStroke.Xdpi = mXdpi;
            tempStroke.Ydpi = mYdpi;
            tempStroke.Length = gesture.getLength();

            if (tempStroke.Length > 50) {
                mListStrokes.add(tempStroke);
                mListStrokesTemp.add(tempStroke);

                mBtnSave.setVisibility(View.VISIBLE);
                mBtnClear.setVisibility(View.VISIBLE);

                mGesturesProcessor.clearStroke();
            }
        }
    }
}