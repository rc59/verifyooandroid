package com.software.verifyoo.expapplicationux;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.gesture.GestureOverlayView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InputActivity1 extends AppCompatActivity {

    GestureOverlayView mOverlay;
    int mCurrentWord;
    String mWord;
    TextView mTextViewWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input1);

        init();
    }

    private void init() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mOverlay = (GestureOverlayView) findViewById(R.id.gestureOverlay);
        mOverlay.setGestureStrokeWidth(8);
        mOverlay.setFadeOffset(50000);

        mCurrentWord = 0;

        mTextViewWord = (TextView) findViewById(R.id.txtWord);

        mWord = Utils.GetRandomWord4();
        String instruction = String.format("%s - %s", mWord, mWord.substring(mCurrentWord, mCurrentWord + 1));

        mTextViewWord.setText(instruction);

        Button btnSave = (Button) findViewById(R.id.btnAuth);
        Button btnClear = (Button) findViewById(R.id.btnClear);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSave();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickClear();
            }
        });
    }

    private void onClickClear() {
        mCurrentWord = 0;
        String instruction = String.format("%s - %s", mWord, mWord.substring(mCurrentWord, mCurrentWord + 1));
        mTextViewWord.setText(instruction);

        mOverlay.setFadeOffset(50);
        mOverlay.clear(true);
        mOverlay.setFadeOffset(50000);
    }

    private void onClickSave() {
        if(mOverlay.getGesture() != null && mOverlay.getGesture().getLength() > 50) {
            mOverlay.setFadeOffset(50);
            mOverlay.clear(true);
            mOverlay.setFadeOffset(50000);

            mCurrentWord++;
            if(mCurrentWord >= 4) {
                Intent i = Utils.GetNextActivity(getApplicationContext());
                startActivity(i);
            }
            else {
                String instruction = String.format("%s - %s", mWord, mWord.substring(mCurrentWord, mCurrentWord + 1));
                mTextViewWord.setText(instruction);
            }
        }
    }
}
