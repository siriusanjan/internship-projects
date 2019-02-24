package com.example.homay.photext;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//first fragment to be loaded by default
//performs image selection action

public class FragmentMainMenu extends Fragment {
    CardView cardViewFlower, cardViewMountain;
    private String TAG = "FragmentMainMenu";
    FragmentCallBack fragmentCallBack;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_menu, container, false);


        cardViewFlower = v.findViewById(R.id.cardview_flower);
        cardViewMountain = v.findViewById(R.id.cardview_mountain);

        cardViewFlower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_PICK);
                i.setType("image/*");


                startActivityForResult(Intent.createChooser(i,"Select Image"), 500);


            }
        });




        return v;

    }

    public void setFragmentCallBack(FragmentCallBack fragmentCallBack_) {
        this.fragmentCallBack = fragmentCallBack_;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 500) {
            Uri uriImage = data.getData();
            Log.i(TAG, uriImage.toString());
            fragmentCallBack.onImageSelect(uriImage);

        }
    }
}
