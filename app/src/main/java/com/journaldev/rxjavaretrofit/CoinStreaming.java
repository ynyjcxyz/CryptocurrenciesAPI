package com.journaldev.rxjavaretrofit;

import com.journaldev.rxjavaretrofit.DataModel.CryptoDataModel;
import com.journaldev.rxjavaretrofit.DataModel.ServerCoinModel;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class CoinStreaming {
    public static Observable<CryptoDataModel> getCoinStream(String coinName) {
        return RetrofitUtil
                .buildRetrofit()
                .create(CryptocurrencyService.class)
                .getCoinData(coinName)
                .map(dto -> new ServerCoinModel(dto.timestamp, dto.ticker.markets))
                .map(serverModel -> new CryptoDataModel(coinName, serverModel))
                .subscribeOn(Schedulers.io());
    }
}
