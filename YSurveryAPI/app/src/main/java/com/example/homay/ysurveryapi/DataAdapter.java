package com.example.homay.ysurveryapi;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    ArrayList<DataModel> dataModels;

    OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position);
    }


    public DataAdapter(ArrayList<DataModel> dataModels) {

        this.dataModels = dataModels;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.single_item, viewGroup, false);


        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        String app = dataModels.get(i).getApp();
        String number = dataModels.get(i).getPhone();
        String username = dataModels.get(i).getName();
        String device = dataModels.get(i).getDevice();
        String location = dataModels.get(i).getLocation();
        int image = dataModels.get(i).getFiles().size();


        myViewHolder.appname.setText(app);
        myViewHolder.devicename.setText(device);

        myViewHolder.user.setText(username);
        myViewHolder.location.setText(location);
        myViewHolder.phone.setText(number);
        myViewHolder.image.setText(image +" image(s)");
        myViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView appname, image, user, phone, location, devicename;
        LinearLayout container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            appname = itemView.findViewById(R.id.app_name);
            image = itemView.findViewById(R.id.image);
            user = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            location = itemView.findViewById(R.id.location);
            devicename = itemView.findViewById(R.id.device);
            container = itemView.findViewById(R.id.item_container);
        }
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }

}

