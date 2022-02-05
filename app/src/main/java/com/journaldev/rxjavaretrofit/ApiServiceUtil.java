package com.journaldev.rxjavaretrofit;

public class ApiServiceUtil {
    static CryptocurrencyService ApiService(){
        return RetrofitUtil.buildRetrofit().create(CryptocurrencyService.class);
    }
}
