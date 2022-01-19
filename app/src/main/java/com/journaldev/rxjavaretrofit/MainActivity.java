package com.journaldev.rxjavaretrofit;

import static com.journaldev.rxjavaretrofit.CryptocurrencyService.BASE_URL;
import static com.uber.autodispose.AutoDispose.autoDisposable;
import static com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider.from;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.journaldev.rxjavaretrofit.pojo.Crypto;
import com.journaldev.rxjavaretrofit.pojo.CryptoDataModel;
import com.journaldev.rxjavaretrofit.pojo.ServerCoinModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
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
        String coinName = "btc";
        //Single call
        coinStream(coinName)
            .observeOn(AndroidSchedulers.mainThread())
                .as(autoDisposable(from(this)))
                .subscribe(this::handleResults, this::handleError);

    }

    private Observable<CryptoDataModel> coinStream(String coinName) {
        return retrofit.create(CryptocurrencyService.class).getCoinData(coinName)
            .map(dto -> new ServerCoinModel(dto.timestamp, dto.ticker.markets))
            .map(serverModel -> new CryptoDataModel(coinName, serverModel))
            .subscribeOn(Schedulers.io());
    }


    private void handleResults(CryptoDataModel model) {
        recyclerViewAdapter.setData(model);
        time_stamp.setText(String.valueOf(model.serverCoinModel.timestamp));
    }


    private void handleError(Throwable t) {
        Toast.makeText(this, "ERROR IN FETCHING API RESPONSE. Try again",
                Toast.LENGTH_LONG).show();
    }

}
