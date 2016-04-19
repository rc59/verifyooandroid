package com.software.exp.expapp2.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.software.exp.expapp2.Logic.Utils;
import com.software.exp.expapp2.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by work on 08/03/2016.
 */
public class ChooseLanguageActivity  extends Activity {

    @Bind(R.id.chooseEnglishButton)
    RadioButton mBtnEnglish;
    @Bind(R.id.chooseHebrewButton)
    RadioButton mBtnHebrew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);

        ButterKnife.bind(this);
    }



    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.chooseEnglishButton:
                if (checked)
                    Utils.changeAppLangauge(this, "en");
                    startNewActivity();
                    break;
            case R.id.chooseHebrewButton:
                if (checked)
                    Utils.changeAppLangauge(this, "iw");
                    startNewActivity();
                    break;
        }
    }

    private void startNewActivity(){
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
        finish();
    }
}
