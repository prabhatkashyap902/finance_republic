package com.prodev.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.prodev.myapplication.ui.Main2Activity;
import com.prodev.myapplication.R;
import com.prodev.myapplication.models.model;

import java.util.ArrayList;

public class recycler_adap extends RecyclerView.Adapter<recycler_adap.viewhoolder> {

    ArrayList<model> list=new ArrayList<>();
    Context mContext;

    public recycler_adap(ArrayList<model> list, Context mContext){
        this.list=list;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public viewhoolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_recycle,viewGroup,false);
        viewhoolder vh = new viewhoolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull viewhoolder viewhoolder, final int i) {

        viewhoolder.tx1.setText(list.get(i).getCompany());
        viewhoolder.tx2.setText(list.get(i).getIndusty());
        viewhoolder.tx3.setText(list.get(i).getSymbol());
        viewhoolder.tx4.setText(list.get(i).getIsin());

        viewhoolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(mContext,""+list.get(i).getSymbol(),Toast.LENGTH_SHORT).show();
                String name=list.get(i).getSymbol();
                Intent i=new Intent(mContext,Main2Activity.class);
                i.putExtra("name",name);
                mContext.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class viewhoolder extends RecyclerView.ViewHolder{

        TextView tx1,tx2,tx3,tx4;
        public viewhoolder(@NonNull View itemView) {
            super(itemView);

            tx1=itemView.findViewById(R.id.tx1);
            tx2=itemView.findViewById(R.id.tx2);
            tx3=itemView.findViewById(R.id.tx3);
            tx4 = itemView.findViewById(R.id.tx4);

        }
    }

}
