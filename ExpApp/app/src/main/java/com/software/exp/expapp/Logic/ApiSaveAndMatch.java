package com.software.exp.expapp.Logic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.TextView;

import com.software.exp.expapp.Activities.ErrorPageActivity;
import com.software.exp.expapp.Activities.ShapeCreatedActivity;
import com.software.exp.expapp.Activities.ShapeMatchResultActivity;
import com.software.exp.expapp.R;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiSaveAndMatch extends AsyncTask<String, String, String> {

    private Button mBtnSave;
    private Button mBtnRestart;
    private TextView mLblStatus;

    private String mAction;
    private Context mAppContext;
    private boolean mSuccess;

    public ApiSaveAndMatch(Context appContext, String action) {
        mAppContext = appContext;
        mAction = action;
    }

    public ApiSaveAndMatch(Context appContext, String action, Button btnSave, TextView lblStatus, Button btnRestart) {
        mAppContext = appContext;
        mAction = action;
        mBtnSave = btnSave;
        mLblStatus = lblStatus;
        mBtnRestart = btnRestart;
    }

    @Override
    protected String doInBackground(String... params) {
        String shape = params[0];
        InputStream is = null;
        OutputStream os = null;
        HttpURLConnection conn = null;
        String strFileContents = "";

        try {
            mSuccess = false;
            String urlString = Tools.getUrl();
            if (mAction == "save") {
                urlString += "shapes/create";
            }
            else {
                if(mAction == "match") {
                    urlString += "shapes/matchByName";
                }
                else {
                    urlString += "shapes/selfMatch";
                }
            }

            //String urlString = Tools.getUrl() + "shapes/create";
            URL url = new URL(urlString);

            String message = "";
            if (shape.length() > 0) {
                message = new JSONObject(shape).toString();
            }

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(20000);
            conn.setConnectTimeout(20000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(message.getBytes().length);

            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

            conn.connect();
            os = new BufferedOutputStream(conn.getOutputStream());
            os.write(message.getBytes());
            os.flush();

            is = conn.getInputStream();
            byte[] contents = new byte[1024];

            int bytesRead = 0;

            while( (bytesRead = is.read(contents)) != -1){
                strFileContents = new String(contents, 0, bytesRead);
            }

            if (strFileContents.contains("true")) {
                mSuccess = true;
            }
        }
        catch (Exception exc) {
            String msg = exc.getMessage();
        }
        finally {
            try {
                os.close();
                is.close();
                conn.disconnect();
            } catch (Exception exc) {

            }
        }

        return strFileContents;
    }

    @Override
    protected void onPostExecute(String result) {
        if (mAction == "selfMatch") {
            mBtnRestart.setEnabled(true);
            if (mSuccess) {
                mBtnSave.setEnabled(true);
                //mBtnSave.setBackgroundColor(Color.GREEN);
                mLblStatus.setTextColor(Color.BLUE);
                mLblStatus.setText(mAppContext.getString(R.string.shapesMatchVerify));
            }
            else {
                //mBtnRestart.setBackgroundColor(Color.RED);
                mLblStatus.setTextColor(Color.RED);
                mLblStatus.setText(mAppContext.getString(R.string.shapesDontMatchVerify));
            }
        }
        else {
            Intent intent;

            if (mAction == "save") {
                if (mSuccess) {
                    intent = new Intent(mAppContext, ShapeCreatedActivity.class);
                } else {
                    intent = new Intent(mAppContext, ErrorPageActivity.class);
                }
            } else {
                intent = new Intent(mAppContext, ShapeMatchResultActivity.class);
                intent.putExtra(Consts.SHAPES_MATCH, mSuccess);
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mAppContext.startActivity(intent);
        }
    }

    public void run(String jsonShape) {
        if (jsonShape!= null && !jsonShape.isEmpty()) {
            new ApiSaveAndMatch(mAppContext, mAction).execute(jsonShape);
        }
    }

    public void runEx(String jsonShape) {
        if (jsonShape!= null && !jsonShape.isEmpty()) {
            new ApiSaveAndMatch(mAppContext, mAction, mBtnSave, mLblStatus, mBtnRestart).execute(jsonShape);
        }
    }
}
