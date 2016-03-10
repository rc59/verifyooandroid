package com.software.exp.expapp2.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.software.exp.expapp2.Logic.Consts;
import com.software.exp.expapp2.Logic.Utils;
import com.software.exp.expapp2.R;

public class LogInActivity extends Activity {

    Button mBtnSubmit;
    EditText mEditText;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private int tapCounter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {

        mEditText = (EditText) findViewById(R.id.txtUserName);

        mBtnSubmit = (Button) findViewById(R.id.btnOK);
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOK();
            }
        });

        ImageView img = (ImageView) findViewById(R.id.imgUserDetails);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setApplicationMode();
            }
        });
        img.setImageResource(R.drawable.user);

        setTitle(getString(R.string.enterEmailAddress));

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String user = prefs.getString(Consts.USERNAME, "");

        if (user != null & user.length() > 0) {
            mEditText.setText(user);
        }
    }

    private void setApplicationMode(){
        tapCounter++;

        if (tapCounter == 5){

        }
    }

    private void onClickOK() {
        String userName = mEditText.getText().toString();
        Utils.Username = userName;
        if (userName.length() > 0) {
            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(Consts.USERNAME, userName);
            editor.commit(); //important, otherwise it wouldn't save.
            Intent intent = new Intent(this, InstructionsActivity.class);
            startActivity(intent);

        }
        else {
            Toast.makeText(getApplicationContext(), getString(R.string.enterEmailAddress), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_details_name, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
