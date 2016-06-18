package com.software.verifyoo.verifyoosdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.software.verifyoo.verifyooofflinesdk.Utils.UtilsGeneral;

public class Analysis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        init();
    }

    private void init() {
        EditText txtAnalysis = (EditText) findViewById(R.id.txtAnalysis);
        txtAnalysis.setText(UtilsGeneral.ResultAnalysis);
    }
}
