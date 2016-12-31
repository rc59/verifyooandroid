package com.software.dalal.signaturecapture.ApiMgr;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;

import com.software.dalal.signaturecapture.Consts;
import com.software.dalal.signaturecapture.Fail;
import com.software.dalal.signaturecapture.MainActivity;

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
    boolean mIsAuth;
    String mErrMsg;
    String mDataReceived;

    public ApiStoreTemplate(Context applicationContext, TextView textView, boolean isAuth) {
        mApplicationContext = applicationContext;
        mTextView = textView;
        mIsAuth = isAuth;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (mIsSuccess) {
            if (mTextView != null) {
                if (mIsAuth) {
                    String score = mDataReceived.split("@")[1];
                    String text = String.format("Score: %s", score);

                    mTextView.setText(text);
                }
                else {
                    mTextView.setText("Success");
                }
            } else {
                Intent i = new Intent(mApplicationContext, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mApplicationContext.startActivity(i);
            }
        }
        else {
            if (mTextView != null) {
                mTextView.setText("Please sign again");
            } else {
                Intent i = new Intent(mApplicationContext, Fail.class);
                i.putExtra(Consts.ERROR_MSG, mErrMsg);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mApplicationContext.startActivity(i);
            }
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
//            Consts.IP = "192.168.1.17:3001";

            String action = "createtemplatedemo";
            if (mIsAuth) {
                action = "createtemplatedemoAuth";
            }

            String urlString = "http://" + Consts.IP + ":3001/shapes/" + action; //192.168.43.113:3001
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
            byte[] contents = new byte[2048];

            if (mIsAuth) {
                int bytesRead = 0;
                bytesRead = is.read(contents);
                mDataReceived = new String(contents, 0, bytesRead);
            }

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

    public void run(String jsonShape, boolean isAuth) {
        if (jsonShape!= null && !jsonShape.isEmpty()) {
            new ApiStoreTemplate(mApplicationContext, mTextView, isAuth).execute(jsonShape);
        }
    }


    public void checkConnection() {
        new ApiStoreTemplate(mApplicationContext, mTextView, true).execute("");
    }
}
