package com.software.exp.expapp.Logic;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.software.exp.expapp.Activities.ErrorPage;
import com.software.exp.expapp.Activities.Instructions;
import com.software.exp.expapp.Activities.UserDashboard;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import flexjson.JSONDeserializer;

/**
 * Created by roy on 9/29/2015.
 */
public class ApiUserExistsByName extends AsyncTask<String, String, String> {
    private String mNumGames;
    private String mUsername;
    private String mInstruction;
    private boolean mSuccess;
    private boolean isUserExists;

    private Context mAppContext;

    public ApiUserExistsByName(Context appContext) {
        mAppContext = appContext;
    }

    @Override
    protected String doInBackground(String... params){
        String resultToDisplay;
        String username = params[0];

        try {
            String urlString = Tools.getUrl() + "shapes/isUserExistsByName?username=" + username;

            mSuccess = false;
            isUserExists = false;
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(10000);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            byte[] contents = new byte[1024];

            int bytesRead = 0;
            String strFileContents = "";
            while( (bytesRead = in.read(contents)) != -1){
                strFileContents = new String(contents, 0, bytesRead);
            }

            JSONDeserializer<HashMap<String, Object>> deserializer = new JSONDeserializer<HashMap<String, Object>>();
            HashMap<String, Object> hashResponse = deserializer.deserialize(strFileContents);
            ResponseObj response = new ResponseObj(hashResponse);

            resultToDisplay = strFileContents;
            if (response.message) {
                isUserExists = true;
                mSuccess = true;
                mNumGames = response.games;
                mUsername = response.name;
                mInstruction = response.instruction;
            }
            else {
                mUsername = username;
                isUserExists = false;
                mInstruction = response.instruction;
                mSuccess = true;
            }

        } catch (Exception e ) {
            return "";
        }

        return resultToDisplay;
    }

    @Override
    protected void onPostExecute(String result) {
        Intent intent;
        if (mSuccess) {
            if (isUserExists) {
                Tools.Username = mUsername;
                intent = new Intent(mAppContext, UserDashboard.class);
                intent.putExtra(Consts.NUM_GAMES, mNumGames);
                intent.putExtra(Consts.USERNAME, mUsername);
                intent.putExtra(Consts.INSTRUCTION, mInstruction);
            } else {
                intent = new Intent(mAppContext, Instructions.class);
                intent.putExtra(Consts.USERNAME, mUsername);
                intent.putExtra(Consts.INSTRUCTION, mInstruction);
            }
        }
        else {
            intent = new Intent(mAppContext, ErrorPage.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mAppContext.startActivity(intent);
    }

    public void isUserExistsByName(String deviceId) {
        if (deviceId!= null && !deviceId.isEmpty()) {
            new ApiUserExistsByName(mAppContext).execute(deviceId);
        }
    }
}
