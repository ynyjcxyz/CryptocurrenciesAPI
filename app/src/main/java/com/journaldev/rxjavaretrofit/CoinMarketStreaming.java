package com.journaldev.rxjavaretrofit;

import com.journaldev.rxjavaretrofit.DataModel.CoinMarket;
import com.journaldev.rxjavaretrofit.DataModel.CryptoDataModel;
import io.reactivex.Observable;

public class CoinMarketStreaming {
    public static Observable<CoinMarket> coinMarketStream(CryptoDataModel model) {
        return Observable
                .fromIterable(model.serverCoinModel.markets)
                .map(market -> new CoinMarket(model.coinName, market));
    }
}
