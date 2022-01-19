package com.journaldev.rxjavaretrofit.pojo;

import com.journaldev.rxjavaretrofit.pojo.Crypto.Market;

public class CoinMarket {

  public final String coinName;
  public final Market market;

  public CoinMarket(String coinName, Market market) {
    this.coinName = coinName;
    this.market = market;
  }
}
