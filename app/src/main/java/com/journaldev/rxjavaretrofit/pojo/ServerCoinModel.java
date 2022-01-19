package com.journaldev.rxjavaretrofit.pojo;

import com.journaldev.rxjavaretrofit.pojo.Crypto.Market;
import java.util.List;

public class ServerCoinModel {

  public final long timestamp;
  public final List<Market> markets ;

  public ServerCoinModel(long timestamp,
      List<Market> markets) {
    this.timestamp = timestamp;
    this.markets = markets;
  }
}
