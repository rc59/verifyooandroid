package com.software.verifyoo.verifyooofflinesdk.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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
    private ArrayList<Data.UserProfile.Raw.Stroke> mListStrokesRepeat;
    private ArrayList<Data.UserProfile.Raw.Stroke> mListStrokesTemp;

    private Button mBtnSave;
    private Button mBtnClear;

    private TextView mTextStrength;
    private TextView mTextView;
    private TextView mTextStatus;

    boolean mIsRequiredToRepeatGesture;

    boolean mIsParamsValid;
    boolean mIsFirstGestureEntered;

    ApiMgr mApiMgr;

    private double mXdpi;
    private double mYdpi;

    private int mNumberRepeats;
    private int mCurrentGesture;

    private HashMap<String, Integer> mHashNumStrokesPerGesture;

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

    private void init() {
        mHashNumStrokesPerGesture = new HashMap<>();
        mNumberRepeats = 1;
        mCurrentGesture = 0;
        getDPI();
        mApiMgr = new ApiMgr();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        setTitle(getTitleString(UtilsInstructions.GetInstruction(0)));

        mListStrokes = new ArrayList<>();
        mListStrokesRepeat = new ArrayList<>();
        mListStrokesTemp = new ArrayList<>();
        mListGestures = new ArrayList<>();

        mIsFirstGestureEntered = false;

        mGesturesProcessor = new GestureDrawProcessorRegister();
        super.init(mGesturesProcessor);

        Resources res = getResources();
        int color = Color.parseColor(Consts.VERIFYOO_BLUE);

        mBtnSave = (Button) findViewById(R.id.btnSave);
        mBtnSave.setBackgroundColor(color);

        mBtnClear = (Button) findViewById(R.id.btnClear);
        mBtnClear.setBackgroundColor(color);

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


        mTextView = (TextView) findViewById(R.id.textInstruction);
        mTextStrength = (TextView) findViewById(R.id.textStrength);
        mTextStatus = (TextView) findViewById(R.id.textStatus);

        if (!mIsRequiredToRepeatGesture) {
            mTextStatus.setVisibility(View.GONE);
        }
        UtilsGeneral.StartTime = new Date().getTime();
    }

    private String getTitleString(String instructionCode) {
        String title = UtilsConvert.InstructionCodeToInstruction(instructionCode);

        title = String.format("Input %s", title);

        return title;
    }

    private void onClickClear() {
        mIsFirstGestureEntered = false;
        clearOverlay();
        mBtnSave.setEnabled(false);
        mListStrokes = new ArrayList<>();
        mListStrokesRepeat = new ArrayList<>();
        mListStrokesTemp = new ArrayList<>();
        mTextStatus.setText("");
        setGestureStrength(getString(R.string.gestureStrNone));
    }

    private void onClickSave() {

        boolean isNumStrokesValid = true;
        String currentInstruction = UtilsInstructions.GetInstruction(mCurrentGesture);
        if(mHashNumStrokesPerGesture.containsKey(currentInstruction))
        {
            int numStrokes = mHashNumStrokesPerGesture.get(currentInstruction);
            if (numStrokes != mListStrokes.size()) {
                isNumStrokesValid = false;
            }

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

        if (isNumStrokesValid) {
            mBtnSave.setEnabled(false);

            Data.UserProfile.Raw.Gesture gesture = new Data.UserProfile.Raw.Gesture();

            gesture.ListStrokes = mListStrokes;
            gesture.Instruction = UtilsInstructions.GetInstruction(mCurrentGesture);
            mListGestures.add(gesture);
            mListStrokes = new ArrayList<>();

            if (!mHashNumStrokesPerGesture.containsKey(gesture.Instruction)) {
                mHashNumStrokesPerGesture.put(gesture.Instruction, gesture.ListStrokes.size());
            }

            mCurrentGesture++;
            if (mNumberRepeats >= Consts.DEFAULT_NUM_REPEATS_PER_INSTRUCTION) {
                if (mCurrentGesture >= Consts.DEFAULT_NUM_REQ_GESTURES_REG) {
                    storeTemplate();
                }
            }
            else {
                if (mCurrentGesture >= Consts.DEFAULT_NUM_REQ_GESTURES_REG) {
                    mCurrentGesture = 0;
                    mNumberRepeats++;
                }
            }

            setTitle(getTitleString(UtilsInstructions.GetInstruction(mCurrentGesture)));
        }
        else {
            Toast.makeText(getApplicationContext(), "The gestures are not similar", Toast.LENGTH_SHORT).show();
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

        Intent intent = this.getIntent();
        this.setResult(RESULT_OK, intent);
        finish();
    }

    private void onClickSave1() {
        mBtnSave.setEnabled(false);
        clearOverlay();
        mTextStatus.setText("");

        if (mIsFirstGestureEntered || !mIsRequiredToRepeatGesture) {
            setGestureStrength(getString(R.string.gestureStrNone));
            mIsFirstGestureEntered = false;
            Data.UserProfile.Raw.Gesture gesture = new Data.UserProfile.Raw.Gesture();

            int idxMiddle = mListStrokes.size() / 2;
            ArrayList<Stroke> tempListStrokes = new ArrayList<>();
            for(int idx = 0; idx < idxMiddle; idx++) {
                tempListStrokes.add(mListStrokes.remove(0));
            }

            gesture.ListStrokes = mListStrokes;
            gesture.Instruction = UtilsInstructions.GetInstruction(mListGestures.size());
            mListGestures.add(gesture);

//            gesture = new Data.UserProfile.Raw.Gesture();
//            gesture.Instruction = UtilsInstructions.GetInstruction(mListGestures.size());
//            gesture.ListStrokes = tempListStrokes;
//            mListGestures.add(gesture);

            mListStrokes = new ArrayList<>();
            mListStrokesRepeat = new ArrayList<>();
            mListStrokesTemp = new ArrayList<>();

            if ((mListGestures.size()) >= Consts.DEFAULT_NUM_REQ_GESTURES_REG) {
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

                Intent intent = this.getIntent();
                this.setResult(RESULT_OK, intent);
                finish();
            } else {
                String instruction = UtilsInstructions.GetInstruction(mListGestures.size());
                mTextView.setText(instruction);
                setTitle(getTitleString(instruction));
            }
        }
        else {
            mIsFirstGestureEntered = true;
            mTextStatus.setText(getString(R.string.statusRepeatGesture));
        }

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

    private void setGestureStrength(String strength) {
        mTextStrength.setText(String.format("Gesture Strength: %s", strength));
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
//
//        setGestureStrength(getString(R.string.gestureStrLow));
//
//        int numStrokesLongerThanMin = 0;
//        double sumStrokeLengths = 0;
//
//        StrokeComparer strokeComparer = new StrokeComparer();
//        Stroke tempStrokeCurrent, tempStrokePrev;
//        double tempStrokeScore;
//
//        for (int idxStroke = 0; idxStroke < tempGesture.ListStrokes.size(); idxStroke++) {
//            if (idxStroke == 0) {
//                if (tempGesture.ListStrokes.get(idxStroke).Length > Consts.MIN_STROKE_LENGTH) {
//                    numStrokesLongerThanMin++;
//                    sumStrokeLengths += tempGesture.ListStrokes.get(idxStroke).Length;
//                }
//            }
//            else {
//
//                try {
//                    tempStrokeCurrent = tempGesture.ListStrokes.get(idxStroke);
//                    tempStrokePrev = tempGesture.ListStrokes.get(idxStroke - 1);
//
//                    tempStrokeCurrent.InitParams();
//                    tempStrokePrev.InitParams();
//
//                    tempStrokeScore = 1;
//                    tempStrokeScore = strokeComparer.Compare(tempStrokeCurrent, tempStrokePrev);
//                } catch (Exception exc) {
//                    tempStrokeScore = 1;
//                }
//
//                if (tempStrokeScore < 0.85) {
//                    if (tempGesture.ListStrokes.get(idxStroke).Length > Consts.MIN_STROKE_LENGTH) {
//                        numStrokesLongerThanMin++;
//                        sumStrokeLengths += tempGesture.ListStrokes.get(idxStroke).Length;
//                    }
//                }
//            }
//        }
//
//        if (numStrokesLongerThanMin >= 1 && sumStrokeLengths > 3000) {
//            setGestureStrength(getString(R.string.gestureStrMedium));
//        }
//        if (numStrokesLongerThanMin >= 2 && sumStrokeLengths > 4000) {
//            setGestureStrength(getString(R.string.gestureStrHigh));
//        }
//        if (numStrokesLongerThanMin >= 3 && sumStrokeLengths > 5000) {
//            setGestureStrength(getString(R.string.gestureStrVeryHigh));
//        }
//    }

    public class GestureDrawProcessorRegister extends GestureDrawProcessorAbstract {
        public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
            unRegisterSensors();
            super.onGesture(overlay, event);

            mBtnSave.setEnabled(true);
            Gesture gesture  = overlay.getGesture();

            int strokesCount = gesture.getStrokes().size();
            if (strokesCount > 1) {
                gesture.getStrokes().remove(0);
            }

            Stroke tempStroke = mGesturesProcessor.getStroke();
            tempStroke.Xdpi = mXdpi;
            tempStroke.Ydpi = mYdpi;
            tempStroke.Length = gesture.getLength();
            mListStrokes.add(tempStroke);
            mListStrokesTemp.add(tempStroke);

            mGesturesProcessor.clearStroke();
        }
    }
}