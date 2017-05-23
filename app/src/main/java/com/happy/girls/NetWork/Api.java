package com.happy.girls.NetWork;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ChangQin on 2017/5/22 0022.
 */

public class Api{
    private final String GIRLS_BASE_URL = "http://gank.io/api/";
    private static Api api;
    private Retrofit mRetrofit;

    private Api(){
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(GIRLS_BASE_URL)
                .build();
    }
    public static Api getInstance(){
        if (api==null){
            synchronized (Api.class){
                if (api ==null){
                    api = new Api();
                }
            }
        }
        return api;
    }
    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
