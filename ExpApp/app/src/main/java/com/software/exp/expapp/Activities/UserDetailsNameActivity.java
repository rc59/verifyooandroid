package com.software.exp.expapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.software.exp.expapp.Logic.ApiUserExistsByName;
import com.software.exp.expapp.Logic.Tools;
import com.software.exp.expapp.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class UserDetailsNameActivity extends Activity {

    Button mBtnSubmit;
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_name);
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
        img.setImageResource(R.drawable.user);

        setTitle(getString(R.string.enterUserInfo));

        String user = readFromFile();
        if (user != null & user.length() > 0) {
            mEditText.setText(user);
        }
    }

    private void writeToFile(String data) {
        try {
            //File file = new File(Environment.getExternalStorageDirectory(), "/shapeRecognition/user.txt");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("shapesrec.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile() {

        String ret = "";

        try {
            InputStream inputStream = openFileInput("shapesrec.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        catch (Exception e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }

    private void onClickOK() {
        String userName = mEditText.getText().toString();
        Tools.Username = userName;
        if (userName.length() > 0) {
            Toast.makeText(getApplicationContext(), getString(R.string.pleaseWait), Toast.LENGTH_SHORT).show();

            writeToFile(userName);

            new ApiUserExistsByName(getApplicationContext()).isUserExistsByName(userName);
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
