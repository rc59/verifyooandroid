package com.software.exp.expapp2.Logic;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.TextView;

import com.software.exp.expapp2.Activities.ErrorPageActivity;
import com.software.exp.expapp2.Activities.ShapeCreatedActivity;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SaveRequest extends AsyncTask<String, String, String> {

    private Button mBtnSave;
    private Button mBtnRestart;
    private TextView mLblStatus;
    private Context mAppContext;
    private boolean mSuccess;

    public SaveRequest(Context appContext) {
        mAppContext = appContext;
    }

    public SaveRequest(Context appContext, Button btnSave, TextView lblStatus, Button btnRestart) {
        mAppContext = appContext;
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
            mSuccess = true;
            String urlString = Utils.getUrl();
            urlString += "shapes/createTemplate";

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
            mSuccess = false;
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
            Intent intent;

                if (mSuccess && !result.isEmpty()) {
                    intent = new Intent(mAppContext, ShapeCreatedActivity.class);
                } else {
                    intent = new Intent(mAppContext, ErrorPageActivity.class);
                }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mAppContext.startActivity(intent);
    }

    public void run(String jsonShape) {
        if (jsonShape!= null && !jsonShape.isEmpty()) {
            new SaveRequest(mAppContext).execute(jsonShape);
        }
    }

    public void runEx(String jsonShape) {
        if (jsonShape!= null && !jsonShape.isEmpty()) {
            new SaveRequest(mAppContext, mBtnSave, mLblStatus, mBtnRestart).execute(jsonShape);
        }
    }
}
