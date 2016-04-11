package com.software.cognitho.cognithoapp.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.software.cognitho.cognithoapp.General.AppData;
import com.software.cognitho.cognithoapp.Logic.ApiActionRequestAuthToken;
import com.software.cognitho.cognithoapp.Logic.ApiHandler;
import com.software.cognitho.cognithoapp.R;

import flexjson.JSONSerializer;

public class VerifyTemplate extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_template);

        init();
    }

    private void init() {
        setTitle("");
        ImageView image = (ImageView) findViewById(R.id.image);
        TextView lbl = (TextView)findViewById(R.id.txtView);

        image.setImageResource(R.drawable.wait);
        lbl.setText(getString(R.string.pleaseWaitWhileVerifiyng));

        JSONSerializer serializer = new JSONSerializer();
        String jsonTemplate = serializer.deepSerialize(AppData.template);

        ApiActionRequestAuthToken verifyTemplates = new ApiActionRequestAuthToken(getApplicationContext());
        ApiHandler apiHandler = new ApiHandler(verifyTemplates);
        apiHandler.Execute(jsonTemplate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_verify_template, menu);
        return true;
    }
}
