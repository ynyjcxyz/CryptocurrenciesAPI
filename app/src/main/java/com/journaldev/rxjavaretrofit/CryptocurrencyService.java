package com.journaldev.rxjavaretrofit;

import com.journaldev.rxjavaretrofit.DataModel.Crypto;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CryptocurrencyService {
    @GET("{coin}-usd")
    Observable<Crypto> getCoinData(@Path("coin") String coin);
}