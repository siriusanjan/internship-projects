package com.example.homay.ysurveryapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

ArrayList<String> imageIDs;




    String url = "https://apptracker.yarsa.io/install-survey/uploads/bijay_test/";
    Context context;

    public ImageAdapter(ArrayList<String> images, Context ctx) {
        this.context = ctx;
this.imageIDs = images;
    }



    @NonNull
    @Override

    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.single_image, viewGroup, false);

        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {


        Glide.with(context).load(url+imageIDs.get(i)).into(imageViewHolder.imageView);


    }

    @Override
    public int getItemCount() {
        return imageIDs.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_container);
        }
    }
}

