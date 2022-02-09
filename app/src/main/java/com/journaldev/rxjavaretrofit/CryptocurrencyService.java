package com.journaldev.rxjavaretrofit;

import com.journaldev.rxjavaretrofit.DataModel.Crypto;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CryptocurrencyService {
    @GET("{appID}")
    Observable<Crypto> getCoinData(@Path("appID") String coin);
}