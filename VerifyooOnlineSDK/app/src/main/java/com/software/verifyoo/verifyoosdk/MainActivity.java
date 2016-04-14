package com.software.verifyoo.verifyoosdk;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.software.verifyoo.verifyooofflinesdk.Activities.VerifyooAuthenticate;
import com.software.verifyoo.verifyooofflinesdk.Activities.VerifyooRegister;
import com.software.verifyoo.verifyooofflinesdk.Utils.Consts;
import com.software.verifyoo.verifyooofflinesdk.Utils.VerifyooConsts;


public class MainActivity extends ActionBarActivity {

    TextView mTxtScore;
    TextView mTxtStatus;

    ImageView mResultImage;

    String mUserName;
    String mCompany;

    TextView mTxtError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserName = "user";
        mCompany = "Verifyoo";

        init();
    }

    private void init() {

        setTitle("Verifyoo Demo");
        Resources res = getResources();
        int color = Color.parseColor(Consts.VERIFYOO_BLUE);

        TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setTextColor(color);

        mTxtError = (TextView) findViewById(R.id.txtErrorMsg);

        ImageView image = (ImageView) findViewById(R.id.welcomeImage);
        image.setImageResource(R.drawable.logo);

        mResultImage = (ImageView) findViewById(R.id.resultImage);

        mTxtScore = (TextView) findViewById(R.id.txtScore);
        mTxtStatus = (TextView) findViewById(R.id.txtStatus);

        InitScore();

        mTxtScore.setTextColor(color);
        mTxtStatus.setTextColor(color);

        Button btnAuth = (Button) findViewById(R.id.btnAuth);
        Button btnReg = (Button) findViewById(R.id.btnReg);

        btnAuth.setBackgroundColor(color);
        btnReg.setBackgroundColor(color);

        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAuth();
            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickReg();
            }
        });
    }

    private void InitScore() {
        mTxtScore.setText("No Score");
        mTxtStatus.setText("");
        mTxtError.setText("");
    }

    private void onClickReg() {
        InitScore();
        Intent i = new Intent(getApplicationContext(), VerifyooRegister.class);
        i.putExtra(VerifyooConsts.EXTRA_STRING_USER_NAME, mUserName);
        i.putExtra(VerifyooConsts.EXTRA_STRING_COMPANY_NAME, mCompany);
        startActivityForResult(i, 1);
    }

    private void onClickAuth() {
        InitScore();
        Intent i = new Intent(getApplicationContext(), VerifyooAuthenticate.class);
        i.putExtra(VerifyooConsts.EXTRA_STRING_USER_NAME, mUserName);
        i.putExtra(VerifyooConsts.EXTRA_STRING_COMPANY_NAME, mCompany);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mResultImage.setVisibility(View.GONE);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (data.getExtras() != null &&
                        data.getExtras().containsKey(VerifyooConsts.EXTRA_BOOLEAN_IS_AUTHORIZED)) {

                    boolean isAuthenticated = data.getExtras().getBoolean(VerifyooConsts.EXTRA_BOOLEAN_IS_AUTHORIZED);

                    mResultImage.setVisibility(View.VISIBLE);
                    if (isAuthenticated) {
                        mResultImage.setImageResource(R.drawable.success);
                        mTxtStatus.setText("Authorized");
                        mTxtStatus.setTextColor(Color.GREEN);
                    } else {
                        mResultImage.setImageResource(R.drawable.failed);
                        mTxtStatus.setText("Not Authorized");
                        mTxtStatus.setTextColor(Color.RED);
                    }
                }
                else {
                    if (data.getExtras() != null && data.getExtras().containsKey(VerifyooConsts.EXTRA_STRING_ERROR_MESSAGE)) {
                        String errorMessage = data.getExtras().getString(VerifyooConsts.EXTRA_STRING_ERROR_MESSAGE);
                        mTxtError.setText(errorMessage);
                    }
                }
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_register) {
            onClickReg();
        }

        return super.onOptionsItemSelected(item);
    }
}
