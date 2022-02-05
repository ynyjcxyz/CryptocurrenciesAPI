package com.journaldev.rxjavaretrofit;

import com.journaldev.rxjavaretrofit.DataModel.ZippedCryptoDataModel;

import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;

public class InfoRepository {
    public static Observable<ZippedCryptoDataModel> dtoStreaming(CryptocurrencyService cryptoCurrencyService){
        return Observable
                .interval(0, 300, TimeUnit.SECONDS)
                .switchMap(Request::request);
    }
}