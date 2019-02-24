package com.example.homay.surveyapiokhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.textclassifier.TextLinks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    String TAG = "Inside MainActivity.java";
    public static final String NEW_LINE = System.getProperty("line.separatpr");


    String API_BASE_URL = "https://apptracker.yarsa.io/install-survey/";
    String API_ADD_URL = "https://apptracker.yarsa.io/install-survey/v1/add";
    String API_GET_URL = "https://apptracker.yarsa.io/install-survey/users/bijay_test.txt";
    String API_VIEW_IMAGE_URL = "https://apptracker.yarsa.io/install-survey/uploads/bijay_test/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(API_GET_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String data = response.body().string();
                Log.i(TAG, data);
                ArrayList<DataModel> dataModels = new ArrayList<>();
                ArrayList<DataModel> anotherDataModels = new ArrayList<>();

                //get data splitting string
                String lines[] = data.split(System.getProperty("line.separator"));
                for (int i = 0; i < lines.length; i++) {
                    DataModel d = new Gson().fromJson(lines[i], DataModel.class);
                    Log.i("Inside loop 1 data", d.getLocation());

                    anotherDataModels.add(d);
                }
                String checkdata = anotherDataModels.get(3).getFiles().get(2);
                Log.i(TAG, checkdata);

//get data scanning for next line
                Scanner scanner = new Scanner(data);


//                Scanner scanner = new Scanner(response.body().byteStream());
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    DataModel d = new Gson().fromJson(line, DataModel.class);

                    Log.i("Inside loop 2", d.getDevice());
                    dataModels.add(d);

                }
                String checkseconddata = dataModels.get(3).getFiles().get(1);
                Log.i(TAG, checkseconddata);
            }


        });
    }


}

