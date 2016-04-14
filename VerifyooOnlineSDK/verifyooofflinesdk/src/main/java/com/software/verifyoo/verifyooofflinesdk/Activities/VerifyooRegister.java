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

import com.software.verifyoo.verifyooofflinesdk.Abstract.GestureDrawProcessorAbstract;
import com.software.verifyoo.verifyooofflinesdk.Abstract.GestureInputAbstract;
import com.software.verifyoo.verifyooofflinesdk.Objects.CompactGesture;
import com.software.verifyoo.verifyooofflinesdk.Objects.Stroke;
import com.software.verifyoo.verifyooofflinesdk.Objects.Template;
import com.software.verifyoo.verifyooofflinesdk.R;
import com.software.verifyoo.verifyooofflinesdk.ServerAPI.API.ApiMgr;
import com.software.verifyoo.verifyooofflinesdk.ServerAPI.API.ApiMgrStoreDataParams;
import com.software.verifyoo.verifyooofflinesdk.Utils.AESCrypt;
import com.software.verifyoo.verifyooofflinesdk.Utils.Consts;
import com.software.verifyoo.verifyooofflinesdk.Utils.ConstsMessages;
import com.software.verifyoo.verifyooofflinesdk.Utils.Files;
import com.software.verifyoo.verifyooofflinesdk.Utils.UtilsData;
import com.software.verifyoo.verifyooofflinesdk.Utils.UtilsDeviceProperties;
import com.software.verifyoo.verifyooofflinesdk.Utils.UtilsInstructions;
import com.software.verifyoo.verifyooofflinesdk.Utils.VerifyooConsts;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import flexjson.JSONSerializer;

public class VerifyooRegister extends GestureInputAbstract {

    public String mCompanyName;
    public String mUserName;

    private ArrayList<CompactGesture> mListGestures;

    private ArrayList<Stroke> mListStrokes;
    private ArrayList<Stroke> mListStrokesRepeat;

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
        mApiMgr = new ApiMgr();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        UtilsDeviceProperties.Xdpi = dm.xdpi;
        UtilsDeviceProperties.Ydpi = dm.ydpi;

        setTitle(UtilsInstructions.GetInstruction(0));
        mListStrokes = new ArrayList<>();
        mListStrokesRepeat = new ArrayList<>();
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
    }

    private void onClickClear() {
        mIsFirstGestureEntered = false;
        clearOverlay();
        mBtnSave.setEnabled(false);
        mListStrokes = new ArrayList<>();
        mListStrokesRepeat = new ArrayList<>();
        mTextStatus.setText("");
        setGestureStrength(getString(R.string.gestureStrNone));
    }

    private void onClickSave() {
        mBtnSave.setEnabled(false);
        clearOverlay();
        mTextStatus.setText("");

        if (mIsFirstGestureEntered || !mIsRequiredToRepeatGesture) {
            setGestureStrength(getString(R.string.gestureStrNone));
            mIsFirstGestureEntered = false;
            CompactGesture gesture = new CompactGesture(mListStrokes);
            gesture.Instruction = UtilsInstructions.GetInstruction(mListGestures.size());
            mListGestures.add(gesture);
            mListStrokes = new ArrayList<>();
            mListStrokesRepeat = new ArrayList<>();

            if (mListGestures.size() >= Consts.DEFAULT_NUM_REQ_GESTURES_REG) {
                Template template = new Template();
                template.Gestures = mListGestures;

                try {
                    getDPI();
                    WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                    ApiMgrStoreDataParams params = new ApiMgrStoreDataParams(mUserName, mCompanyName, "Register", wm, mXdpi, mYdpi, true);
                    mApiMgr.StoreData(params, template);
                } catch (Exception exc) {
                    handleGeneralError(exc);
                }

                JSONSerializer serializer = new JSONSerializer();
                String jsonTemplate = serializer.deepSerialize(template);

                try {
                    String key = UtilsData.GetUserKey(mUserName);
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
                setTitle(instruction);
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

    public class GestureDrawProcessorRegister extends GestureDrawProcessorAbstract {
        public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
            unRegisterSensors();
            super.onGesture(overlay, event);

            Gesture gesture  = overlay.getGesture();

            int strokesCount = gesture.getStrokes().size();
            if (strokesCount > 1) {
                gesture.getStrokes().remove(0);
            }

            mBtnSave.setEnabled(true);
            if (mIsFirstGestureEntered && mIsRequiredToRepeatGesture) {
                super.InitPrevStroke(mGesturesProcessor.getStroke(), mListStrokesRepeat, gesture.getLength());
                mListStrokesRepeat.add(mGesturesProcessor.getStroke());

                if (mListStrokesRepeat.size() == mListStrokes.size()) {
                    CompactGesture gesture1 = new CompactGesture(mListStrokes);
                    CompactGesture gesture2 = new CompactGesture(mListStrokesRepeat);

//                    GestureComparer gestureComparer = new GestureComparer();
                    double score = 0.9;
                    if (score < 0.85) {
                        mBtnSave.setEnabled(false);
                        mTextStatus.setText(getString(R.string.statusGesturesMismatch));
                    }
                    else {
                        mTextStatus.setText(getString(R.string.statusGesturesMatch));
                    }
                }
                else {
                    mBtnSave.setEnabled(false);
                }
            }
            else {
                super.InitPrevStroke(mGesturesProcessor.getStroke(), mListStrokes, gesture.getLength());
                mListStrokes.add(mGesturesProcessor.getStroke());
            }

            mGesturesProcessor.clearStroke();
        }
    }
}