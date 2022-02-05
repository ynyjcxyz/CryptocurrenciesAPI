package com.journaldev.rxjavaretrofit;

import com.journaldev.rxjavaretrofit.DataModel.CoinMarket;
import com.journaldev.rxjavaretrofit.DataModel.CryptoDataModel;
import java.util.List;
import io.reactivex.Observable;

public class MergeAndSortCryptoData {
    public static List<CoinMarket> mergeAndSort(CryptoDataModel btc, CryptoDataModel eth) {
        Observable<CoinMarket> btcStream = CoinMarketStreaming.coinMarketStream(btc);
        Observable<CoinMarket> ethStream = CoinMarketStreaming.coinMarketStream(eth);
        return Observable
                .merge(btcStream, ethStream)
                .sorted((t1, t2) -> Float.compare(t1.market.volume, t2.market.volume))
                .toList()
                .blockingGet();
    }
}
