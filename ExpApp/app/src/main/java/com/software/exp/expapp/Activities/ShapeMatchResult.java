package com.software.exp.expapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.software.exp.expapp.Logic.Consts;
import com.software.exp.expapp.MainActivity;
import com.software.exp.expapp.R;

public class ShapeMatchResult extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_match_result);
        init();
    }

    private void init() {
        boolean isShapesMatch = getIntent().getBooleanExtra(Consts.SHAPES_MATCH, false);


        ImageView img = (ImageView) findViewById(R.id.imgView);
        img.setImageResource(R.drawable.greencheck);

        String str = getString(R.string.shapesMatch);
        if (!isShapesMatch) {
            str = getString(R.string.shapesDontMatch);
            img.setImageResource(R.drawable.redx);
        }

        TextView lblResult = (TextView) findViewById(R.id.lblResult);
        lblResult.setText(str);

        Button btnPlayAgain = (Button) findViewById(R.id.btnPlayAgain);
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPlayAgain();
            }
        });

        setTitle(getString(R.string.matchResultTitle));
    }

    private void onClickPlayAgain() {
        Intent intent = new Intent(getApplication(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shape_match_result, menu);
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

        if (id == R.id.action_exit) {
            int p = android.os.Process.myPid();
            android.os.Process.killProcess(p);
        }

        return super.onOptionsItemSelected(item);
    }
}
