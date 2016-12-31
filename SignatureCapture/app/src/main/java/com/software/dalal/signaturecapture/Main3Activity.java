package com.software.dalal.signaturecapture;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.software.dalal.signaturecapture.ApiMgr.ApiMgr;

public class Main3Activity extends AppCompatActivity {

    private Button mBtnStart;
    private EditText mTxtBoxIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        SharedPreferences prefs = getSharedPreferences("VerifyooPrefs", MODE_PRIVATE);
        String IP = prefs.getString("IPAddress", "");

        if (IP == null || IP.length() == 0) {
            IP = Consts.DEFAULT_IP;
        }

        Consts.IP = IP;

        ApiMgr apiMgr = new ApiMgr(getApplicationContext(), null);
        apiMgr.checkConnection();
    }
}
