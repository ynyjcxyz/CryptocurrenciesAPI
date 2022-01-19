package com.journaldev.rxjavaretrofit;

import static com.journaldev.rxjavaretrofit.CryptocurrencyService.BASE_URL;
import static com.uber.autodispose.AutoDispose.autoDisposable;
import static com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider.from;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.journaldev.rxjavaretrofit.pojo.CoinMarket;
import com.journaldev.rxjavaretrofit.pojo.CryptoDataModel;
import com.journaldev.rxjavaretrofit.pojo.ServerCoinModel;
import com.journaldev.rxjavaretrofit.pojo.ZippedCryptoDataModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * refresh the coin for btc and etc for every 3 seconds and update it in descending (biggest to smallest) volume order.
 */
public class MainActivity extends AppCompatActivity {

    private  static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    TextView time_stamp;
    RecyclerView recyclerView;
    Retrofit retrofit;
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time_stamp = findViewById(R.id.time_stamp);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        callEndpoints();
    }

    private void callEndpoints() {
        //Single call
        Observable.interval(0,3, TimeUnit.SECONDS)
            .switchMap(this::request)
            .observeOn(AndroidSchedulers.mainThread())
            .as(autoDisposable(from(this)))
            .subscribe(this::handleResults, this::handleError);

    }

    private Observable<ZippedCryptoDataModel> request(Long count) {
        System.out.printf("count:%s\n",count);
        return Observable.zip(coinStream("btc"), coinStream("eth"), this::zip);
    }

    @NonNull
    private ZippedCryptoDataModel zip(CryptoDataModel btc, CryptoDataModel eth) {

        long latest =Math.max(btc.serverCoinModel.timestamp,eth.serverCoinModel.timestamp);
        List<CoinMarket> coinMarkets = mergeAndSort(btc, eth);
        return new ZippedCryptoDataModel(latest,coinMarkets);

    }

    private List<CoinMarket> mergeAndSort(CryptoDataModel btc, CryptoDataModel eth) {
        Observable<CoinMarket> btcStream = coinMarketStream(btc);
        Observable<CoinMarket> ethStream = coinMarketStream(eth);
        return Observable.merge(btcStream, ethStream)
            .sorted(new Comparator<CoinMarket>() {
                @Override
                public int compare(CoinMarket o1, CoinMarket o2) {
                    return Float.compare(o2.market.volume, o1.market.volume);
                }
            }).toList().blockingGet();
    }

    private Observable<CoinMarket> coinMarketStream(CryptoDataModel btc) {
        return Observable.fromIterable(btc.serverCoinModel.markets)
            .map(market -> new CoinMarket(btc.coinName, market));
    }

    private Observable<CryptoDataModel> coinStream(String coinName) {
        return retrofit.create(CryptocurrencyService.class).getCoinData(coinName)
            .map(dto -> new ServerCoinModel(dto.timestamp, dto.ticker.markets))
            .map(serverModel -> new CryptoDataModel(coinName, serverModel))
            .subscribeOn(Schedulers.io());
    }


    //CryptoDataModel -> eth
    //CryptoDataModel -> btc

    private void handleResults(ZippedCryptoDataModel model) {
        System.out.printf("count model:%s",model.timestamp);
        recyclerViewAdapter.setData(model);
        time_stamp.setText(formatTime(model.timestamp));
    }

    private String formatTime(long serverUpdatedTime) {
         return String.format("server updated time:%s\nrefreshed time:%s,",
            serverTime(serverUpdatedTime),
            refreshedTime());
    }

    private String serverTime(long serverUpdatedTime) {
        return SIMPLE_DATE_FORMAT.format(new Date(serverUpdatedTime * 1000));
    }

    private String refreshedTime( ) {
        return SIMPLE_DATE_FORMAT.format(new Date());
    }

    private void handleError(Throwable t) {
        Toast.makeText(this, "ERROR IN FETCHING API RESPONSE. Try again",
                Toast.LENGTH_LONG).show();
    }

}
