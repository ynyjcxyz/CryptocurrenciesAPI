package com.journaldev.rxjavaretrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    static final String BASE_URL = "https://run.mocky.io/v3/";

    static Retrofit buildRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(HttpClientUtil.getHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonUtil.createGson()))
                .build();
    }
}