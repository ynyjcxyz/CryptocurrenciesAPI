package com.journaldev.rxjavaretrofit.DataModel;

import com.journaldev.rxjavaretrofit.DataModel.CoinMarket;
import java.util.List;

public class ZippedCryptoDataModel {
    public final long timestamp;
    public final List<CoinMarket> coinMarkets;

    public ZippedCryptoDataModel(long timestamp, List<CoinMarket> coinMarkets) {
        this.timestamp = timestamp;
        this.coinMarkets = coinMarkets;
    }
}
