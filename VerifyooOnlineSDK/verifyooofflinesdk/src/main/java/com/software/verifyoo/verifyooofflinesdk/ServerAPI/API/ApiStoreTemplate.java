package com.software.verifyoo.verifyooofflinesdk.ServerAPI.API;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by roy on 2/24/2016.
 */
public class ApiStoreTemplate extends AsyncTask<String, String, String> {

    private boolean mSuccess;

    @Override
    protected String doInBackground(String... params) {
        String shape = params[0];
        InputStream is = null;
        OutputStream os = null;
        HttpURLConnection conn = null;
        String strFileContents = "";

        try {
            mSuccess = false;
            String urlString = "http://52.26.178.48/shapes/createtemplateDemo";
            //String urlString = "http://localhost:3001/shapes/createTemplateDemo";
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

    public void run(String jsonShape) {
        if (jsonShape!= null && !jsonShape.isEmpty()) {
            new ApiStoreTemplate().execute(jsonShape);
        }
    }
}
