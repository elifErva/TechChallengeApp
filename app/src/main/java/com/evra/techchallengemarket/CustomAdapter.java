package com.evra.techchallengemarket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MarketViewHolder> {
    ArrayList<Market> marketList = new ArrayList<>();
    Context context;
    LayoutInflater inflater;
    int countClick=0;

    public CustomAdapter(ArrayList<Market> markets, Context context) {
        this.marketList = markets;
        this.context = context;
    }

    @NonNull
    @Override
    public MarketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        MarketViewHolder vh = new MarketViewHolder(view);//cast -->view  to viewHolder
        return vh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MarketViewHolder holder, int position) {
        Market market = marketList.get(position);

        holder.item_title.setText(market.getMarketName());
        holder.item_date.setText(market.getDate());
        holder.item_month.setText(market.getMonth());

        holder.productState.setText("Durum:   " + market.getState());
        holder.orderName.setText("Ürün:   " + market.getOrderName());
        holder.itemPrice.setText("Fiyat:   " + market.getPrice());

        holder.orderDetail.setText(market.getOrderDetail());
        holder.sumPrice.setText(market.getSummaryPrice());

        //detay bilgileri için clickListener
        holder.layout_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countClick = countClick +1;
                if (countClick % 2 == 0){
                    holder.layoutDetail.setVisibility(View.INVISIBLE);
                }else if (countClick % 2 == 1) {
                    holder.layoutDetail.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return marketList.size();
    }

    public class MarketViewHolder extends RecyclerView.ViewHolder {
        TextView item_title;
        TextView item_date, item_month;
        TextView productState, orderName, itemPrice;
        LinearLayout layoutDetail;
        RelativeLayout layout_market;
        TextView orderDetail, sumPrice;

        public MarketViewHolder(@NonNull View itemView) {
            super(itemView);
            item_title = itemView.findViewById(R.id.item_title);
            item_date = itemView.findViewById(R.id.item_date);
            item_month = itemView.findViewById(R.id.item_month);

            productState = itemView.findViewById(R.id.item_productState);
            orderName = itemView.findViewById(R.id.item_orderName);
            itemPrice = itemView.findViewById(R.id.item_price);

            //details' grid
            layoutDetail = itemView.findViewById(R.id.layout_detail);
            //Market details
            orderDetail = itemView.findViewById(R.id.orderDetail);
            sumPrice = itemView.findViewById(R.id.summaryPrice);

            layout_market = itemView.findViewById(R.id.layout_market);
        }
    }
}
