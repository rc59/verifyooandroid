package com.software.cognitho.cognithoapp.Logic;

import android.os.AsyncTask;

import com.software.cognitho.cognithoapp.General.ApiVerbEnum;
import com.software.cognitho.cognithoapp.General.AppData;
import com.software.cognitho.cognithoapp.Tools.Consts;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiHandler extends AsyncTask<String, String, String> {

    private static IApiAction _apiAction;
    private static boolean _isSuccess;
    private InputStream _inputStream = null;
    private OutputStream _outputStream = null;
    private String _strResponse = "";
    private URL _url;
    private HttpURLConnection _urlConnection;
    private byte[] _contents;

    public ApiHandler(IApiAction apiAction) {
        _apiAction = apiAction;
    }

    public static void Execute(String input) {
        new ApiHandler(_apiAction).execute(input);
    }

    public static void Execute() {
        new ApiHandler(_apiAction).execute();
    }

    @Override
    protected void onPostExecute(String result) {
        _apiAction.postAction(result, _isSuccess);
    }

    @Override
    protected String doInBackground(String... params) {

        _isSuccess = true;
        try
        {
            _url = new URL(_apiAction.getUrl());
            _urlConnection = (HttpURLConnection) _url.openConnection();
            _urlConnection.setReadTimeout(Consts.TIMEOUT_READ);
            _urlConnection.setConnectTimeout(Consts.TIMEOUT_CONNECT);

            _urlConnection.setRequestProperty("AppId", AppData.AppId);
            _urlConnection.setRequestProperty("AppKey", AppData.AppKey);

            _contents = new byte[1024];
        }
        catch (Exception e)
        {
            _isSuccess = false;
        }

        if (_apiAction.getVerb() == ApiVerbEnum.GET) {
            _strResponse = getRequest();
        }

        if (_apiAction.getVerb() == ApiVerbEnum.POST) {
            String jsonTemplate = params[0];
            _strResponse = postRequest(jsonTemplate);
        }

        if (_apiAction.getVerb() == ApiVerbEnum.PUT) {
            String jsonTemplate = params[0];
            _strResponse = putRequest(jsonTemplate);
        }

        return _strResponse;
    }

    private String putRequest(String input) {
        return postOrPutRequest(ApiVerbEnum.PUT.toString(), input);
    }

    private String postRequest(String input) {
        return postOrPutRequest(ApiVerbEnum.POST.toString(), input);
    }

    private String postOrPutRequest(String strVerb, String input) {
        try {
            String message = "";
            if (input.length() > 0) {
                try {
                    message = new JSONObject(input).toString();
                } catch (Exception exc) {
                    message = new JSONArray(input).toString();
                }
            }
            _urlConnection.setRequestMethod(strVerb);
            _urlConnection.setDoInput(true);
            _urlConnection.setDoOutput(true);
            _urlConnection.setReadTimeout(15000);
            _urlConnection.setConnectTimeout(15000);
            _urlConnection.setFixedLengthStreamingMode(message.getBytes().length);

            _urlConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            _urlConnection.setRequestProperty("X-Requested-With", "XMLHttpRequest");

            _urlConnection.connect();
            _outputStream = new BufferedOutputStream(_urlConnection.getOutputStream());
            _outputStream.write(message.getBytes());
            _outputStream.flush();

            _inputStream = _urlConnection.getInputStream();
            _strResponse = bytesToResponseString(_contents);
        }
        catch (Exception e ) {
            _isSuccess = false;
            return "";
        }

        return _strResponse;
    }

    private String getRequest() {
        try {
            _urlConnection.setReadTimeout(15000);
            _urlConnection.setConnectTimeout(15000);
            _inputStream = new BufferedInputStream(_urlConnection.getInputStream());
            _strResponse = bytesToResponseString(_contents);
        }
        catch (Exception e)
        {
            _isSuccess = false;
            return "";
        }

        return  _strResponse;
    }

    private String bytesToResponseString(byte[] contents) {
        String strContents = "";

        try {
            int bytesRead = 0;
            while ((bytesRead = _inputStream.read(contents)) != -1) {
                strContents += new String(contents, 0, bytesRead);
            }
        }
        catch (Exception e) {
            _isSuccess = false;
            return "";
        }

        return strContents;
    }
}
