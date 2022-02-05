package com.journaldev.rxjavaretrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
    static Gson createGson() {
        return new GsonBuilder().setLenient().create();
    }
}
