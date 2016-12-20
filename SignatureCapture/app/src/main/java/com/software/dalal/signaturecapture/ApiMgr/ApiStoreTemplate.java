package com.software.dalal.signaturecapture.ApiMgr;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;

import com.software.dalal.signaturecapture.Consts;
import com.software.dalal.signaturecapture.Fail;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by roy on 12/12/2016.
 */
public class ApiStoreTemplate extends AsyncTask<String, String, String> {
    Context mApplicationContext;
    TextView mTextView;
    boolean mIsSuccess;
    String mErrMsg;

    public ApiStoreTemplate(Context applicationContext, TextView textView) {
        mApplicationContext = applicationContext;
        mTextView = textView;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (mIsSuccess) {
            mTextView.setText("Success");
        }
        else {
            Intent i = new Intent(mApplicationContext, Fail.class);
            i.putExtra(Consts.ERROR_MSG, mErrMsg);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mApplicationContext.startActivity(i);
        }
    }

    @Override
    protected String doInBackground(String... params) {
        mIsSuccess = false;
        mErrMsg = "";

        String shape = params[0];
        InputStream is = null;
        OutputStream os = null;
        HttpURLConnection conn = null;
        String strFileContents = "";

        try {
            Consts.IP = "10.0.0.7:3001";
            String urlString = "http://" + Consts.IP + "/shapes/createtemplateDemo"; //192.168.43.113:3001
            URL url = new URL(urlString);

            String message = "";
            if (shape.length() > 0) {
                message = new JSONObject(shape).toString();
            }

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
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

            mIsSuccess = true;
        }
        catch (Exception exc) {
            String msg = exc.getMessage();
            mIsSuccess = false;
            mErrMsg = exc.getMessage();
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

    public void run(String jsonShape) {
        if (jsonShape!= null && !jsonShape.isEmpty()) {
            new ApiStoreTemplate(mApplicationContext, mTextView).execute(jsonShape);
        }
    }
}
