package com.journaldev.rxjavaretrofit;

import android.annotation.SuppressLint;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.journaldev.rxjavaretrofit.DataModel.CoinMarket;
import com.journaldev.rxjavaretrofit.DataModel.ZippedCryptoDataModel;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ZippedCryptoDataModel model = null;

    public RecyclerViewAdapter() {
    }

    @NonNull
    @Override
    public RecyclerViewAdapter
            .ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recyclerview_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        CoinMarket coinMarket = model.coinMarkets.get(position);
        holder.txtCoin.setText(coinMarket.coinName);
        holder.txtMarket.setText(coinMarket.market.market);
        holder.txtPrice.setText("$" + String.format("%.10f", Double.parseDouble(coinMarket.market.price)));
        holder.txtVolume.setText("$" + String.format("%.10f", coinMarket.market.volume));
        if ("eth".equalsIgnoreCase(coinMarket.coinName)) {
            holder.cardView.setCardBackgroundColor(Color.GRAY);
        } else {
            holder.cardView.setCardBackgroundColor(Color.GREEN);
        }
    }

    @Override
    public int getItemCount() {
        return model == null ? 0 : model.coinMarkets.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(ZippedCryptoDataModel model) {
        this.model = model;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
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
