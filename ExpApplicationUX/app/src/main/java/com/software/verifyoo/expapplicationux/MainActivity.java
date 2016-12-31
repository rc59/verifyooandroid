package com.software.verifyoo.expapplicationux;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Button button1 = (Button) findViewById(R.id.btnType1);
        Button button2 = (Button) findViewById(R.id.btnType2);
        Button button3 = (Button) findViewById(R.id.btnType3);
        Button button4 = (Button) findViewById(R.id.btnType4);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButton1();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButton2();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButton3();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButton4();
            }
        });
    }

    private void onClickButton1() {
        Intent i = Utils.GetNextActivity(getApplicationContext());
        startActivity(i);
    }

    private void onClickButton2() {
        Intent i = new Intent(getApplicationContext(), InputActivity2.class);
        startActivity(i);
    }

    private void onClickButton3() {
        Intent i = new Intent(getApplicationContext(), InputActivity3.class);
        startActivity(i);
    }

    private void onClickButton4() {
        Intent i = new Intent(getApplicationContext(), InputActivity4.class);
        startActivity(i);
    }
}
