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
import com.software.exp.expapp.Logic.Tools;
import com.software.exp.expapp.R;

public class UserDashboardActivity extends Activity {

    String numGames;
    String mInstruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        init();
    }

    private void init() {
        Button mBtnPlay = (Button) findViewById(R.id.btnPlay);
        TextView lbl = (TextView) findViewById(R.id.lblStatus);

        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPlay();
            }
        });

        numGames = getIntent().getStringExtra(Consts.NUM_GAMES);
        mInstruction = getIntent().getStringExtra(Consts.INSTRUCTION);

        int num = Integer.parseInt(numGames);

        if (num >= 5) {
            mBtnPlay.setEnabled(false);
            TextView lblDoneForToday = (TextView) findViewById(R.id.lblDoneForToday);
            lblDoneForToday.setVisibility(View.VISIBLE);
        }

        //String lblStr1 = String.format(getString(R.string.youHaveCompleted), numGames);
        String lblStr1 = getString(R.string.youHaveCompleted) + String.format(" %s ",numGames) + getString(R.string.outOfGame);
        lbl.setText(lblStr1);

        ImageView img = (ImageView) findViewById(R.id.imgView);
        img.setImageResource(R.drawable.list);

        setTitle(getString(R.string.dashboardTitle));
    }

    private void onClickPlay() {
        String username = Tools.Username;

        Intent intent = new Intent(getApplication(), MatchShapeActivity.class);
        intent.putExtra(Consts.NUM_GAMES, numGames);
        intent.putExtra(Consts.INSTRUCTION, mInstruction);
        intent.putExtra(Consts.USERNAME, username);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_dashboard, menu);
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
