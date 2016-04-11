package com.software.cognitho.cognithoapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.software.cognitho.cognithoapp.General.AppData;
import com.software.cognitho.cognithoapp.Logic.ApiActionVerifyTemplates;
import com.software.cognitho.cognithoapp.Logic.ApiHandler;
import com.software.cognitho.cognithoapp.Logic.GestureDrawProcessorAbstract;
import com.software.cognitho.cognithoapp.MainActivity;
import com.software.cognitho.cognithoapp.Objects.GestureObj;
import com.software.cognitho.cognithoapp.Objects.Stroke;
import com.software.cognitho.cognithoapp.Objects.Template;
import com.software.cognitho.cognithoapp.R;
import com.software.cognitho.cognithoapp.Tools.Consts;

import java.util.ArrayList;

import flexjson.JSONSerializer;

public class InstructionDraw extends Activity {

    private static GestureDrawProcessorAbstract mGesturesProcessor;
    private static GestureOverlayView mOverlay;

    private Button mBtnSave;
    private Button mBtnClear;
    private Button mBtnConfirm;

    private TextView mLabel;
    private TextView mStatus;

    private Stroke mTempStroke;
    private GestureObj mTempGesture;

    private Template mTemplate = null;
    private Template mTemplateVerify = null;

    private boolean mIsSignInMode = false;
    private double mXdpi;
    private double mYdpi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_draw);

        init();
    }

    private void init() {
        setTitle("Instruction " + String.valueOf(AppData.instructionIdx + 1));
        String instruction = "Please draw " + AppData.listInstructions.get(AppData.instructionIdx).Text;

        getDPI();

        mTempStroke = new Stroke();
        mTempGesture = new GestureObj();

        mLabel = (TextView) findViewById(R.id.txtInstruction);
        mLabel.setText(instruction);

        mStatus = (TextView) findViewById(R.id.lblStatus);

        mIsSignInMode = getIntent().getBooleanExtra(Consts.EXTRA_IS_SIGN_IN, false);

        if(mIsSignInMode) {
            mGesturesProcessor = new GestureDrawProcessorSignIn();
        }
        else {
            mGesturesProcessor = new GestureDrawProcessor();
        }
        mGesturesProcessor.init(getApplicationContext());

        mOverlay = (GestureOverlayView) findViewById(R.id.gestures_overlay);
        mOverlay.addOnGestureListener(mGesturesProcessor);
        mOverlay.setFadeOffset(Consts.FADE_INTERVAL);
        mOverlay.setBackgroundColor(Color.rgb(33, 33, 33));
        //mOverlay.setBackgroundResource(R.drawable.paypal3);

        int color = Color.parseColor("#0474B2");

        mBtnClear = (Button) findViewById(R.id.btnClear);
        mBtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickClear();
            }
        });
        mBtnClear.setBackgroundColor(color);

        mBtnSave = (Button) findViewById(R.id.btnSave);
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSave();
            }
        });
        mBtnSave.setBackgroundColor(color);

        mBtnConfirm = (Button) findViewById(R.id.btnConfirm);
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickConfirm();
            }
        });
        mBtnConfirm.setBackgroundColor(color);
    }

    private void getDPI() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mXdpi = metrics.xdpi;
        mYdpi = metrics.ydpi;
    }

    private void onClickConfirm() {
        if (!mIsSignInMode) {
            mStatus.setText(getString(R.string.repeatShape));
        }

        mBtnConfirm.setEnabled(false);

        String serviceName = Context.TELEPHONY_SERVICE;
        TelephonyManager telephonyMgr = (TelephonyManager) getSystemService(serviceName);
        WindowManager windowMgr = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mTemplate = new Template(getApplicationContext(), telephonyMgr, windowMgr, locationManager);
        mTemplate.Xdpi = mXdpi;
        mTemplate.Ydpi = mYdpi;

        mTemplate.Gestures = new ArrayList<GestureObj>();
        mTempGesture.InstructionIdx = AppData.listInstructions.get(AppData.instructionIdx).InstructionIdx;
        mTemplate.Gestures.add(mTempGesture);

        if (mIsSignInMode) {
            AppData.instructionIdx++;
            AppData.template.Gestures.add(mTempGesture);

            Intent intent = new Intent(getApplicationContext(), SignInInputSelection.class);
            startActivity(intent);
        } else {
            clearOverlay();
        }

        mTempGesture = new GestureObj();
        mTempStroke = new Stroke();
    }

    private void onClickClear() {
        clearOverlay();
        mStatus.setText("");
        mTempStroke = new Stroke();
        mTempGesture = new GestureObj();
        mTemplate = null;
        mBtnConfirm.setVisibility(View.VISIBLE);
        mBtnConfirm.setEnabled(false);
        mBtnSave.setVisibility(View.INVISIBLE);
    }

    private void clearOverlay() {
        mOverlay.setFadeOffset(Consts.FADE_INTERVAL_CLEAR);
        mOverlay.clear(true);
        mOverlay.setFadeOffset(Consts.FADE_INTERVAL);
    }

    private void onClickSave() {
        mTempGesture.InstructionIdx = AppData.listInstructions.get(AppData.instructionIdx).InstructionIdx;
        AppData.instructionIdx++;
        AppData.template.Gestures.add(mTempGesture);

        Intent intent = new Intent(getApplicationContext(), InstructionInputSelection.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_instruction, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    public class GestureDrawProcessorSignIn extends GestureDrawProcessorAbstract {
        public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
            unRegisterSensors();
            Gesture gesture  = overlay.getGesture();

            int strokesCount = gesture.getStrokes().size();
            if (strokesCount > 1) {
                gesture.getStrokes().remove(0);
            }

            //if (gesture.getLength() > Consts.LENGTH_THRESHOLD) {
                mBtnConfirm.setEnabled(true);
                mTempStroke = this.getStroke();
                this.clearStroke();

                mTempStroke.Length = gesture.getLength();
                mTempGesture.Strokes.add(mTempStroke);
                mTempStroke = new Stroke();
            //}
        }
    }

    public class GestureDrawProcessor extends GestureDrawProcessorAbstract {
        public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
            unRegisterSensors();
            Gesture gesture  = overlay.getGesture();

            int strokesCount = gesture.getStrokes().size();
            if (strokesCount > 1) {
                gesture.getStrokes().remove(0);
            }

            //if (gesture.getLength() > Consts.LENGTH_THRESHOLD) {
                mTempStroke = this.getStroke();
                this.clearStroke();

                mTempStroke.Length = gesture.getLength();
                mTempGesture.Strokes.add(mTempStroke);
                mTempStroke = new Stroke();

                if (mTemplate != null) {
                    mTemplateVerify = new Template();
                    mTemplateVerify.Xdpi = mXdpi;
                    mTemplateVerify.Ydpi = mYdpi;

                    mTemplateVerify.Gestures = new ArrayList<GestureObj>();
                    mTempGesture.InstructionIdx = AppData.listInstructions.get(AppData.instructionIdx).InstructionIdx;
                    mTemplateVerify.Gestures.add(mTempGesture);

                    if (mTemplate.Gestures.get(0).Strokes.size() == mTemplateVerify.Gestures.get(0).Strokes.size()) {
                        mBtnConfirm.setVisibility(View.GONE);
                        mBtnClear.setVisibility(View.GONE);

                        ArrayList<Template> list = new ArrayList<Template>();
                        list.add(mTemplate);
                        list.add(mTemplateVerify);

                        JSONSerializer serializer = new JSONSerializer();
                        String jsonTemplate = serializer.deepSerialize(list);

                        mStatus.setText(getString(R.string.plsWaitWhileVerifying));

                        ApiActionVerifyTemplates verifyTemplates = new ApiActionVerifyTemplates(mBtnSave, mBtnConfirm, mStatus);
                        ApiHandler apiHandler = new ApiHandler(verifyTemplates);
                        apiHandler.Execute(jsonTemplate);
                    }
                }
                else {
                    mBtnConfirm.setEnabled(true);
                }
            //}
        }
    }
}
