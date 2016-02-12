package com.software.exp.expapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.software.exp.expapp.Logic.Consts;
import com.software.exp.expapp.R;


public class UserDetails extends Activity {

    String mInstruction;

    Button mBtnSubmit;
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
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

        mInstruction = getIntent().getStringExtra(Consts.INSTRUCTION);

        setTitle(getString(R.string.enterUserInfo));
    }

    private void onClickOK() {
        String userName = mEditText.getText().toString();
        if (userName.length() > 0) {
            Intent intent = new Intent(getApplication(), SourceShape.class);
            intent.putExtra(Consts.ID, userName);
            intent.putExtra(Consts.INSTRUCTION, mInstruction);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), getString(R.string.enterEmailAddress), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_details, menu);
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
