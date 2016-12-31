package com.software.verifyoo.expapplicationux;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InputActivity4 extends AppCompatActivity {

    GestureOverlayView mOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input4);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        init();
    }

    private void init() {
        mOverlay = (GestureOverlayView) findViewById(R.id.gestureOverlay);
        mOverlay.setGestureStrokeWidth(8);
        mOverlay.setFadeOffset(50000);

        mOverlay.setBackgroundResource(R.drawable.back4);

        TextView textViewWord = (TextView) findViewById(R.id.txtWord);
        textViewWord.setText(Utils.GetRandomWord4());

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
        mOverlay.setFadeOffset(50);
        mOverlay.clear(true);
        mOverlay.setFadeOffset(50000);
    }

    private void onClickSave() {
        if(mOverlay.getGesture() != null && mOverlay.getGesture().getLength() > 50) {
            Intent i = Utils.GetNextActivity(getApplicationContext());
            startActivity(i);
        }
    }
}
