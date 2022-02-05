package com.journaldev.rxjavaretrofit.DataModel;

import java.util.List;

public class ServerCoinModel {
    public final long timestamp;
    public final List<Crypto.Market> markets;

    public ServerCoinModel(long timestamp, List<Crypto.Market> markets) {
        this.timestamp = timestamp;
        this.markets = markets;
    }
}