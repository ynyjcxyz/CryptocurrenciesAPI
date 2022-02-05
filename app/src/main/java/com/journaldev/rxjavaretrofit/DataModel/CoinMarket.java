package com.journaldev.rxjavaretrofit.DataModel;

public class CoinMarket {
    public final String coinName;
    public final Crypto.Market market;

    public CoinMarket(String coinName, Crypto.Market market) {
        this.coinName = coinName;
        this.market = market;
    }
}
