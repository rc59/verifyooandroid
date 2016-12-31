package com.software.dalal.signaturecapture;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Fail extends AppCompatActivity {

    private Button mBtnSaveIp;
    private TextView mTxtErr;
    private EditText mEditTxtIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail);

        Intent i = getIntent();
        String msg = i.getStringExtra(Consts.ERROR_MSG);

        mBtnSaveIp = (Button) findViewById(R.id.btnSave);
        mBtnSaveIp .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSave();
            }
        });

        mTxtErr = (TextView) findViewById(R.id.txtError);
        mTxtErr.setText("An error has occurred:" + msg);

        mEditTxtIp = (EditText) findViewById(R.id.txtBoxIP);
        SharedPreferences prefs = getSharedPreferences("VerifyooPrefs", MODE_PRIVATE);
        String IP = prefs.getString("IPAddress", "");

        if (IP.length() > 0) {
            mEditTxtIp.setText(IP);
        }
        else {
            mEditTxtIp.setText(Consts.DEFAULT_IP);
        }
    }

    private void onClickSave() {
        SharedPreferences prefs = getSharedPreferences("VerifyooPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String IP = mEditTxtIp.getText().toString();
        editor.putString("IPAddress", IP);
        editor.commit();

        Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
        startActivity(intent);
    }
}
