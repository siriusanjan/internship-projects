package com.example.homay.photext;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FragmentDisplayImage extends Fragment {
    ImageView imageView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_display_image, container, false);
        String filepath = getArguments().getString("IMAGE_PATH");

        Log.i("FragmentDisplay", filepath);

        imageView = v.findViewById(R.id.my_image);

        Bitmap bitmap = BitmapFactory.decodeFile(filepath);
        imageView.setImageBitmap(bitmap);

        return v;
    }
}
