package com.example.needlework.NetWork;

import com.example.needlework.NetWork.Service.ApiService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHandler {
    private static ApiHandler mInstance;
    private static String URL = "https://needle-work-be.herokuapp.com/api/";

    private Retrofit retrofit;

    public ApiHandler(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder().addInterceptor(interceptor);

        retrofit = new Retrofit.Builder().baseUrl(URL).client(client.build()).addConverterFactory(GsonConverterFactory.create()).build();

        ErrorUtils.retrofit = retrofit;
    }

    public static ApiHandler getmInstance() {
        if(mInstance==null)
            mInstance = new ApiHandler();
        return mInstance;
    }

    public ApiService getService(){
        return retrofit.create(ApiService.class);
    }
}
