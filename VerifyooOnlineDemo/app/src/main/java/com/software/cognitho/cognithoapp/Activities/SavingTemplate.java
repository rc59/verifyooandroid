package com.software.cognitho.cognithoapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.widget.ImageView;

import com.software.cognitho.cognithoapp.ErrorPage;
import com.software.cognitho.cognithoapp.General.ActionTypeEnum;
import com.software.cognitho.cognithoapp.General.AppData;
import com.software.cognitho.cognithoapp.General.Consts;
import com.software.cognitho.cognithoapp.Logic.ApiActionCreateTemplate;
import com.software.cognitho.cognithoapp.Logic.ApiActionUpdateTemplate;
import com.software.cognitho.cognithoapp.Logic.ApiHandler;
import com.software.cognitho.cognithoapp.Logic.IApiAction;
import com.software.cognitho.cognithoapp.R;

import flexjson.JSONSerializer;

public class SavingTemplate extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_saving_template);

        init();
    }

    private void init() {
        setTitle("");

        ImageView image = (ImageView) findViewById(R.id.image);
        image.setImageResource(R.drawable.wait);

        IApiAction action = null;
        if(AppData.actionType == ActionTypeEnum.CREATE) {
            action = new ApiActionCreateTemplate(getApplicationContext());
        }

        if(AppData.actionType == ActionTypeEnum.UPDATE) {
            action = new ApiActionUpdateTemplate(getApplicationContext());
        }

        if(action != null) {
            if (AppData.CurrentNumInstructionsInTemplate >= AppData.NumInstructionsInTemplate) {
                ApiHandler apiHandler = new ApiHandler(action);

                for(int idx = 0; idx < AppData.listInstructions.size(); idx++) {
                    if (AppData.listInstructions.get(idx).IsInTemplate) {
                        AppData.template.Gestures.get(idx).IsInTemplate = true;
                    }
                }

                JSONSerializer serializer = new JSONSerializer();
                String jsonTemplate = serializer.deepSerialize(AppData.template);

                apiHandler.Execute(jsonTemplate);
            }
            else {
                Intent intent = new Intent(getApplicationContext(), ErrorPage.class);
                intent.putExtra(Consts.EXTRA_ERROR_MESSAGE, "Error: The gestures you entered were not properly validated");
                startActivity(intent);
            }
        }
        else {
            Intent intent = new Intent(getApplicationContext(), ErrorPage.class);
            intent.putExtra(Consts.EXTRA_ERROR_MESSAGE, "Error: Action type is invalid");
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_saving_template, menu);
        return true;
    }
}
