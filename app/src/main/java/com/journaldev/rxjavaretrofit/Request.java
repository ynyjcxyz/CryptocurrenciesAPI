package com.journaldev.rxjavaretrofit;

import static com.journaldev.rxjavaretrofit.CoinStreaming.getCoinStream;

import com.journaldev.rxjavaretrofit.DataModel.ZippedCryptoDataModel;

import io.reactivex.Observable;

public class Request {
    public static String btc_AppId = "9ea4863b-e4b1-47fb-970c-1518085f568c";
    public static String eth_AppId = "95b42f8c-7fae-49e0-a85d-0dc2acd1f63e";

    public static Observable<ZippedCryptoDataModel> request(Long count) {
        return Observable
                .zip(getCoinStream(btc_AppId), getCoinStream(eth_AppId), ZipData::zipData);
    }
}