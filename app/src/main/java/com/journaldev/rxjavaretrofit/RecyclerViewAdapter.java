package com.journaldev.rxjavaretrofit;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.journaldev.rxjavaretrofit.pojo.CoinMarket;
import com.journaldev.rxjavaretrofit.pojo.Crypto;
import com.journaldev.rxjavaretrofit.pojo.CryptoDataModel;
import com.journaldev.rxjavaretrofit.pojo.ZippedCryptoDataModel;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ZippedCryptoDataModel model =null;

    public RecyclerViewAdapter() {
    }

    @Override
    public RecyclerViewAdapter
            .ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.recyclerview_item_layout, parent, false);

        RecyclerViewAdapter.ViewHolder viewHolder = new RecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        CoinMarket coinMarket = model.coinMarkets.get(position);
        holder.txtCoin.setText(coinMarket.coinName);
        holder.txtMarket.setText(coinMarket.market.market);
        holder.txtPrice.setText("$" + String.format("%.2f", Double.parseDouble(coinMarket.market.price)));
        holder.txtVolume.setText(String.valueOf(coinMarket.market.volume));
        if ("eth".equalsIgnoreCase(coinMarket.coinName)) {
            holder.cardView.setCardBackgroundColor(Color.GRAY);
        } else {
            holder.cardView.setCardBackgroundColor(Color.GREEN);
        }
    }

    @Override
    public int getItemCount() {
        return  model==null?0: model.coinMarkets.size();
    }

    public void setData(ZippedCryptoDataModel model) {
        this.model= model;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtCoin;
        public TextView txtMarket;
        public TextView txtPrice;
        public TextView txtVolume;
        public CardView cardView;

        public ViewHolder(View view) {
            super(view);

            txtCoin = view.findViewById(R.id.txtCoin);
            txtMarket = view.findViewById(R.id.txtMarket);
            txtPrice = view.findViewById(R.id.txtPrice);
            txtVolume = view.findViewById(R.id.txtVolume);
            cardView = view.findViewById(R.id.cardView);
        }
    }
}
