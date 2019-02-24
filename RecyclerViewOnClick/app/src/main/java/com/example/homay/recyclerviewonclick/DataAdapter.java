package com.example.homay.recyclerviewonclick;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {
    CustomClickListener customClickListener;

    public DataAdapter() {
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.single_item_view, viewGroup, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customClickListener.onClickCallback(i);
            }
        });

    }



    @Override
    public int getItemCount() {
        return 50;
    }





    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.my_text);
            linearLayout = itemView.findViewById(R.id.linear_layout_container);
        }
    }

public void    setCustomClickListener(CustomClickListener customClickListener) {

        this.customClickListener = customClickListener;
    }




}
