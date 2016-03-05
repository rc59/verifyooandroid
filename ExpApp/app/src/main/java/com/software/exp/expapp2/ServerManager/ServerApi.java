package com.software.exp.expapp2.ServerManager;

import com.software.exp.expapp2.Logic.ResponseObj;
import com.software.exp.expapp2.Logic.Tools;
import com.squareup.okhttp.OkHttpClient;

import java.util.Map;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.POST;
import retrofit.http.QueryMap;

/**
 * Created by work on 16/02/2016.
 */
public class ServerApi {

    public static final String SERVER_BASE_URL = Tools.getUrl();
    public static final String USER_EXIST = "shapes/isUserExistsByName";

    private OkHttpClient mHttpClient;


    public ServerApi(OkHttpClient httpClient) {
        mHttpClient = httpClient == null ? new OkHttpClient() : httpClient;
    }

    private Service create() {
        mHttpClient = new OkHttpClient();

        Retrofit.Builder builder = new Retrofit.Builder()
                .client(mHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_BASE_URL);

        Retrofit restAdapter = builder.build();

        return restAdapter.create(Service.class);
    }


    public interface Service {
        @POST(USER_EXIST)
        Call<ResponseObj> userExistsByName(@QueryMap Map<String, String> query);
    }

}
