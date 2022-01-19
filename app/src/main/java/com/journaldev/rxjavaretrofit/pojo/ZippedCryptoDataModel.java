package com.journaldev.rxjavaretrofit.pojo;

import java.util.List;

public class ZippedCryptoDataModel {

  public final long timestamp;//the latest response times
  public final List<CoinMarket> coinMarkets;//combined btc and eth.

  public ZippedCryptoDataModel(long timestamp, List<CoinMarket> coinMarkets) {
    this.timestamp = timestamp;
    this.coinMarkets = coinMarkets;
  }
}
