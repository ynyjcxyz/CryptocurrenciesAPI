package com.journaldev.rxjavaretrofit.pojo;

import java.util.List;

public class ZippedCryptoDataModel {
    public final long timestamp;
    public final List<CoinMarket> coinMarkets;

    public ZippedCryptoDataModel(long timestamp, List<CoinMarket> coinMarkets) {
        this.timestamp = timestamp;
        this.coinMarkets = coinMarkets;
    }
}
