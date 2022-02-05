package com.journaldev.rxjavaretrofit;

import static com.journaldev.rxjavaretrofit.CoinStreaming.getCoinStream;

import com.journaldev.rxjavaretrofit.DataModel.ZippedCryptoDataModel;

import io.reactivex.Observable;

public class Request {
    public static Observable<ZippedCryptoDataModel> request(Long count) {
        return Observable
                .zip(getCoinStream("btc"), getCoinStream("eth"), ZipData::zipData);
    }
}