package com.journaldev.rxjavaretrofit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;
import io.reactivex.android.schedulers.AndroidSchedulers;
import static com.journaldev.rxjavaretrofit.FormatTime.formatTime;
import static com.uber.autodispose.AutoDispose.autoDisposable;
import static com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider.from;

import com.journaldev.rxjavaretrofit.DataModel.ZippedCryptoDataModel;

public class MainActivity extends AppCompatActivity {
    TextView time_stamp;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setRecycleView();
        callEndpoints();
    }

    private void initView() {
        time_stamp = findViewById(R.id.time_stamp);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void callEndpoints() {
        InfoRepository.dtoStreaming(ApiServiceUtil.ApiService())
                .observeOn(AndroidSchedulers.mainThread())
                .as(autoDisposable(from(this)))
                .subscribe(this::handleResults, this::handleError);
    }

    private void handleResults(ZippedCryptoDataModel model) {
        System.out.printf("count model:%s", model.timestamp + "\n");
        recyclerViewAdapter.setData(model);
        time_stamp.setText(formatTime(model.timestamp));
    }

    private void handleError(Throwable t) {
        Toast.makeText(this, "ERROR IN FETCHING API RESPONSE. Try again",
                Toast.LENGTH_LONG).show();
    }
}