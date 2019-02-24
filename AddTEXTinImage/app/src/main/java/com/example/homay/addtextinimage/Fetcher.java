package com.example.homay.addtextinimage;

public interface  Fetcher  {

    void onFetchImageDimension(float x, float y);

    void onFetchViewDimension(float x, float y);

    void onFetchScaledVal(float scaledValue);

    void onFetchAspectRatio(float viewAspectRatio, float imageAspectRatio);

    void onMessageReceive(String message);


}
