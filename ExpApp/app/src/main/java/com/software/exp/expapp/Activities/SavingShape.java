package com.software.exp.expapp.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.software.exp.expapp.Logic.ApiSaveAndMatch;
import com.software.exp.expapp.Logic.Tools;
import com.software.exp.expapp.R;

public class SavingShape extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_shape);

        setTitle(getString(R.string.shapeCreatedTitle));
        saveShape();
    }

    private void saveShape() {
        String jsonShape = Tools.jsonShape;
        new ApiSaveAndMatch(getApplicationContext(), "save").run(jsonShape);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_saving_shape, menu);
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
