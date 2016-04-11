package com.software.cognitho.cognithoapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.software.cognitho.cognithoapp.General.Consts;
import com.software.cognitho.cognithoapp.MainActivity;
import com.software.cognitho.cognithoapp.R;

public class AuthResult extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_result);

        init();
    }

    private void init() {
        setTitle("");
        String authResult = getIntent().getStringExtra(Consts.EXTRA_AUTH_RESULT);

        ImageView image = (ImageView) findViewById(R.id.image);

        TextView lbl = (TextView)findViewById(R.id.txtView);

        if (authResult.toLowerCase().compareTo("false") != 0) {
            image.setImageResource(R.drawable.success);
            lbl.setText(getString(R.string.userAuthorized));
        }
        else {
            image.setImageResource(R.drawable.failed);
            lbl.setText(getString(R.string.userNotAuthorized));
        }

        Button btn = (Button) findViewById(R.id.btnGoHome);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoHome();
            }
        });
    }

    private void onClickGoHome() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_auth_result, menu);
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
