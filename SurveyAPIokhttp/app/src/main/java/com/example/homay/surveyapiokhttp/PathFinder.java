package com.example.homay.surveyapiokhttp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;

import java.net.URISyntaxException;

public class PathFinder {

    public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
        String seletion = null;
        String[] selectionArgs = null;

        //Relolving URI conflict after KITKAT
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {

        }
        return null;
    }


}
