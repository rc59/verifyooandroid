package com.software.exp.expapp2.ServerManager;

import android.content.Context;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by work on 16/02/2016.
 */
public class ObjectGraph {

    private static final long DISK_CACHE_SIZE = 5000000; // 5mbX
    private static final long CONNECT_TIMEOUT_MILLIS = 30000;
    private static final long READ_TIMEOUT_MILLIS = 40000;
    protected final Context app;
    private ServerApi mServerApi;
    private OkHttpClient mHttpClient;

    public ObjectGraph(Context applicationContext) {
        this.app = applicationContext;
    }



    public ServerApi serverApi() {
        if (mServerApi == null) {
            mServerApi = new ServerApi(apiHttpClient());
        }
        return mServerApi;
    }

    private OkHttpClient apiHttpClient() {
        if (mHttpClient == null) {
            mHttpClient = new DefaultHttpClient(this.app);
            File directory = new File(this.app.getCacheDir(), "responses");

            mHttpClient.setCache(new Cache(directory, DISK_CACHE_SIZE));
            mHttpClient.setConnectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            mHttpClient.setReadTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);

        }
        return mHttpClient;
    }
}
