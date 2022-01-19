package com.journaldev.rxjavaretrofit;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.journaldev.rxjavaretrofit.pojo.Crypto;
import com.journaldev.rxjavaretrofit.pojo.CryptoDataModel;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private CryptoDataModel model =null;

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
        Crypto.Market market = model.serverCoinModel.markets.get(position);
        holder.txtCoin.setText(model.coinName);
        holder.txtMarket.setText(market.market);
        holder.txtPrice.setText("$" + String.format("%.2f", Double.parseDouble(market.price)));
        if ("eth".equalsIgnoreCase(model.coinName)) {
            holder.cardView.setCardBackgroundColor(Color.GRAY);
        } else {
            holder.cardView.setCardBackgroundColor(Color.GREEN);
        }
    }

    @Override
    public int getItemCount() {
        return  model==null?0: model.serverCoinModel.markets.size();
    }

    public void setData(CryptoDataModel model) {
        this.model= model;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtCoin;
        public TextView txtMarket;
        public TextView txtPrice;
        public CardView cardView;

        public ViewHolder(View view) {
            super(view);

            txtCoin = view.findViewById(R.id.txtCoin);
            txtMarket = view.findViewById(R.id.txtMarket);
            txtPrice = view.findViewById(R.id.txtPrice);
            cardView = view.findViewById(R.id.cardView);
        }
    }
}
