package com.software.dalal.signaturecapture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Fail extends AppCompatActivity {

    private TextView mTxtErr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail);

        Intent i = getIntent();
        String msg = i.getStringExtra(Consts.ERROR_MSG);

        mTxtErr = (TextView) findViewById(R.id.txtError);
        mTxtErr.setText("An error has occurred:" + msg);
    }
}
