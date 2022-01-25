package com.journaldev.rxjavaretrofit.pojo;

public class CryptoDataModel {
    public final String coinName;
    public final ServerCoinModel serverCoinModel;

    public CryptoDataModel(String coinName, ServerCoinModel serverCoinModel) {
        this.coinName = coinName;
        this.serverCoinModel = serverCoinModel;
    }
}