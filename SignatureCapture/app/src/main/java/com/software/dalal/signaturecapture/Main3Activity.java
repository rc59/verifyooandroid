package com.software.dalal.signaturecapture;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main3Activity extends AppCompatActivity {

    private Button mBtnStart;
    private EditText mTxtBoxIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        int colorBlue = Color.parseColor(Consts.VERIFYOO_BLUE);

        mBtnStart = (Button) findViewById(R.id.btnStart);
        mBtnStart.setBackgroundColor(colorBlue);

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickStart();
            }
        });

        mTxtBoxIp = (EditText) findViewById(R.id.txtBoxIP);

        SharedPreferences prefs = getSharedPreferences("VerifyooPrefs", MODE_PRIVATE);
        String IP = prefs.getString("IPAddress", "");

        if (IP.length() > 0) {
            mTxtBoxIp.setText(IP);
        }
    }

    private void onClickStart() {
            SharedPreferences prefs = getSharedPreferences("VerifyooPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String IP = mTxtBoxIp.getText().toString();
        editor.putString("IPAddress", IP);
        editor.commit();

        Consts.IP = IP;

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}
