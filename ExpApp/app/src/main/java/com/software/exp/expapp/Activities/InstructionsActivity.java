package com.software.exp.expapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.software.exp.expapp.Logic.Consts;
import com.software.exp.expapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InstructionsActivity extends Activity {

    @Bind(R.id.btnOK)
    Button btnOk;
    @Bind(R.id.lblInstructionsReg)
    TextView lblInstructionsReg;
    @Bind(R.id.lblInstructionsMatch)
    TextView lblInstructionsMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        ButterKnife.bind(this);

        setTitle(getString(R.string.instructionsTitle));

        lblInstructionsReg.setPaintFlags(lblInstructionsReg.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        lblInstructionsMatch.setPaintFlags(lblInstructionsMatch.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOK();
            }
        });
    }

    private void onClickOK() {
        Toast.makeText(getApplicationContext(), getString(R.string.pleaseWait), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), SourceShapeActivity.class);
        String instruction = getIntent().getStringExtra(Consts.INSTRUCTION);
        String username = getIntent().getStringExtra(Consts.USERNAME);
        intent.putExtra(Consts.INSTRUCTION, instruction);
        intent.putExtra(Consts.USERNAME, username);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_instructions, menu);
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
