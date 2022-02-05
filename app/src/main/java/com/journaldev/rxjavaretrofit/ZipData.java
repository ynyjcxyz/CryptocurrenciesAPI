package com.journaldev.rxjavaretrofit;

import static com.journaldev.rxjavaretrofit.MergeAndSortCryptoData.mergeAndSort;
import com.journaldev.rxjavaretrofit.DataModel.CoinMarket;
import com.journaldev.rxjavaretrofit.DataModel.CryptoDataModel;
import com.journaldev.rxjavaretrofit.DataModel.ZippedCryptoDataModel;

import java.util.List;

public class ZipData {
    public static ZippedCryptoDataModel zipData(CryptoDataModel btc, CryptoDataModel eth) {
        long latest = Math.max(btc.serverCoinModel.timestamp, eth.serverCoinModel.timestamp);
        List<CoinMarket> coinMarkets = mergeAndSort(btc, eth);
        return new ZippedCryptoDataModel(latest, coinMarkets);
    }
}
