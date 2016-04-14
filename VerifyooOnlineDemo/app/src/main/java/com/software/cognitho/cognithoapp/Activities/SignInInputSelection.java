package com.software.cognitho.cognithoapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.software.cognitho.cognithoapp.General.AppData;
import com.software.cognitho.cognithoapp.MainActivity;
import com.software.cognitho.cognithoapp.R;
import com.software.cognitho.cognithoapp.Tools.Consts;

public class SignInInputSelection extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_sign_in_input_selection);

        if (AppData.instructionIdx >= AppData.listInstructions.size()) {
            Intent intent = new Intent(getApplicationContext(), VerifyTemplate.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), InstructionDraw.class);
            intent.putExtra(Consts.EXTRA_IS_SIGN_IN, true);
            startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in_input_selection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
