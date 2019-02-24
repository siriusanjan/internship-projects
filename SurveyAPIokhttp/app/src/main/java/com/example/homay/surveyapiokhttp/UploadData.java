package com.example.homay.surveyapiokhttp;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadData extends AppCompatActivity {


    String TAG = "Inside UploadData.java";
    Uri fileUri;
    int REQUEST_CODE_IMGAEPICKER = 1000;
    ArrayList<Uri> imageBundle;
    String app, username, device, phone, location;
    EditText appET, usernameET, deviceET, phoneET, locationET;
    Button btnUploadData, btnAddImage;
    ImageView imageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_data);

        //Initialization
        imageBundle = new ArrayList<>();
        imageContainer = findViewById(R.id.image_iv);
        appET = findViewById(R.id.app_et);
        usernameET = findViewById(R.id.username_et);
        deviceET = findViewById(R.id.device_et);
        phoneET = findViewById(R.id.phone_et);
        locationET = findViewById(R.id.location_et);

        btnUploadData = findViewById(R.id.btn_upload_data);
        btnAddImage = findViewById(R.id.btn_add_image);

        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "Select Image To Upload"), REQUEST_CODE_IMGAEPICKER);

            }
        });

//upload the data to server Data
        btnUploadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = appET.getText().toString();
                username = usernameET.getText().toString();
                device = deviceET.getText().toString();
                phone = phoneET.getText().toString();
                location = locationET.getText().toString();
                final DataModel uploadData = new DataModel();
                uploadData.setApp(app);
                uploadData.setName(username);
                uploadData.setDevice(device);
                uploadData.setLocation(location);
                uploadData.setPhone(phone);

                new DoUpload().execute();


            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK && data != null) {

            Uri image = data.getData();
            fileUri = image;
            imageBundle.add(image);

            Glide.with(this).load(image).into(imageContainer);

        }
    }

    class DoUpload extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            Log.i(TAG, "Inside do in background");
            OkHttpClient client = new OkHttpClient();
            final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpeg");
            File myimage = null;
            try {
                myimage = new File(getFilePath(UploadData.this, fileUri));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            Response result;
                                /* .addFormDataPart("app", uploadData.getApp())
                                 .addFormDataPart("username", uploadData.getName())
                                 .addFormDataPart("phone", uploadData.getPhone())
                                 .addFormDataPart("device",uploadData.getDevice())
                                 .addFormDataPart("location",uploadData.getLocation())*/
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)


                    .addFormDataPart("name", "Another user")
                    .addFormDataPart("app", "cards_game")
                    .addFormDataPart("username", "bijay_test")
                    .addFormDataPart("phone", "Anther user")
                    .addFormDataPart("device", "984040404")
                    .addFormDataPart("location", "123,4")
                    .addFormDataPart("files", "test11.jpeg",
                            RequestBody.create(MEDIA_TYPE_JPG, myimage))
                    .build();

            //  .addFormDataPart("files",)


            Request request = new Request.Builder()
                    .url("https://apptracker.yarsa.io/install-survey/v1/add/")
                    .post(requestBody)
                    .build();

            try {

                Response response = client.newCall(request).execute();
                Log.i("RESPONSSE", response.body().string());

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        ///////////////------------------------------------------------------------------------------------------------------------------------
        @SuppressLint("NewApi")
        public String getFilePath(Context context, Uri uri) throws URISyntaxException {
            String selection = null;
            String[] selectionArgs = null;
            // Uri is different in versions after KITKAT (Android 4.4), we need to
            if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                } else if (isDownloadsDocument(uri)) {
                    final String id = DocumentsContract.getDocumentId(uri);
                    uri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                } else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("image".equals(type)) {
                        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    selection = "_id=?";
                    selectionArgs = new String[]{
                            split[1]
                    };
                }
            }
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                String[] projection = {
                        MediaStore.Images.Media.DATA
                };
                Cursor cursor = null;
                try {
                    cursor = context.getContentResolver()
                            .query(uri, projection, selection, selectionArgs, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (cursor.moveToFirst()) {
                        return cursor.getString(column_index);
                    }
                } catch (Exception e) {
                }
            } else if ("fileUri".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
            return null;
        }

        public boolean isExternalStorageDocument(Uri uri) {
            return "com.android.externalstorage.documents".equals(uri.getAuthority());
        }

        public boolean isDownloadsDocument(Uri uri) {
            return "com.android.providers.downloads.documents".equals(uri.getAuthority());
        }

        public boolean isMediaDocument(Uri uri) {
            return "com.android.providers.media.documents".equals(uri.getAuthority());
        }
        /////////////////////////////----------------------------------------------------------------------------------------------
    }

}
