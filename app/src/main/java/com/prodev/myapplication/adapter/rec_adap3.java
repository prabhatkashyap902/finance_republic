package com.prodev.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.prodev.myapplication.R;
import com.prodev.myapplication.models.model;

import java.util.ArrayList;

public class rec_adap3 extends RecyclerView.Adapter<rec_adap3.viewholder> {

    ArrayList<model> list;
    Context mContext;

    public rec_adap3(ArrayList<model> list, Context mContext){
        this.list=list;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycle3, viewGroup, false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder viewholder, int i) {
        viewholder.tx0.setText(list.get(i).getComp3());
        viewholder.tx1.setText(list.get(i).getDate3());
        viewholder.tx2.setText(list.get(i).getCost3());
        viewholder.tx3.setText(list.get(i).getStock3());
        viewholder.tx4.setText(list.get(i).getUnit3());
        viewholder.tx5.setText(list.get(i).getTotal3());
        viewholder.tx6.setText(list.get(i).getCurrent3());
        viewholder.tx7.setText(list.get(i).getDifference3());
        viewholder.tx8.setText(list.get(i).getPercent3());
        viewholder.tx9.setText(list.get(i).getCurrentP3());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewholder extends RecyclerView.ViewHolder{
        TextView tx1,tx2,tx3,tx4,tx5,tx6,tx7,tx8,tx0,tx9;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            tx0 = itemView.findViewById(R.id.comp);
            tx1 = itemView.findViewById(R.id.date_of_purchase);
            tx2 = itemView.findViewById(R.id.cost_on_purchase_time);
            tx3 = itemView.findViewById(R.id.stocks_purchased);
            tx4 = itemView.findViewById(R.id.unit_purchased);
            tx5 = itemView.findViewById(R.id.total_purchased);
            tx6 = itemView.findViewById(R.id.current_total);
            tx7 = itemView.findViewById(R.id.difference_in_amount);
            tx8 = itemView.findViewById(R.id.change_in_percent);
            tx9=itemView.findViewById(R.id.current_price);


        }
    }
}
