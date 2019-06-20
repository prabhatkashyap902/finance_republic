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
import java.util.ConcurrentModificationException;

public class rec_adap2 extends RecyclerView.Adapter<rec_adap2.viewholder> {
    ArrayList<model> list = new ArrayList<>();
    Context mContext;

    public rec_adap2(ArrayList<model> list, Context mContext){
        this.list=list;
        this.mContext=mContext;
    }


    public void updateList(ArrayList<model> list){
        this.list=list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycle2, viewGroup, false);
        viewholder  vh = new viewholder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder viewholder, int i) {
        viewholder.date.setText(list.get(i).getDate());
        viewholder.open.setText(list.get(i).getOpen());
        viewholder.close.setText(list.get(i).getClose());

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class viewholder extends RecyclerView.ViewHolder{
        TextView date,open,close;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date_tx);
            open=itemView.findViewById(R.id.open_tx);
            close=itemView.findViewById(R.id.close_tx);

        }
    }
}
