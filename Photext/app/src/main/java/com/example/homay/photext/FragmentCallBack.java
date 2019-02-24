package com.example.homay.photext;

import android.net.Uri;

public interface FragmentCallBack {

    void onImageSelect(Uri image);

    void onImageSave(String filepath);

}
