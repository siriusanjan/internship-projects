package com.example.homay.ysurveryapi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DataAdapter.OnItemClicked {



Button fetchBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchBtn = findViewById(R.id.fetch_button);
        fetchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchData f = new FetchData();
                f.execute();

            }
        });

    }

    @Override
    public void onItemClick(int position) {

        Intent i = new Intent(MainActivity.this, DisplayActivity.class);
        i.putExtra("position", position);
        startActivity(i);

    }


    class FetchData extends AsyncTask<Void, Void, ArrayList<DataModel>> {

        ArrayList<DataModel> dataModels = new ArrayList<>();


        @Override
        protected ArrayList<DataModel> doInBackground(Void... voids) {

            try {

                URL survey = new URL("https://apptracker.yarsa.io/install-survey/users/bijay_test.txt");
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(survey.openStream())
                );
                String inputline;

                while ((inputline = in.readLine()) != null) {
                    int counter = 0;
                    System.out.println(inputline);

                    DataModel d = new Gson().fromJson(inputline, DataModel.class);

                    for (int j = 0; j < d.getFiles().size(); j++) {

                        Log.i("Image Loop", d.getFiles().get(j));
                    }
                    dataModels.add(d);


                }




                in.close();




            } catch (Exception e) {
                e.printStackTrace();
            }

        GlobalData.setGlobaldata(dataModels);
            return dataModels;


        }

        @Override
        protected void onPostExecute(ArrayList<DataModel> data) {


            RecyclerView rv;
            DataAdapter d = new DataAdapter(data);
            rv = findViewById(R.id.recyclerview_container);

            rv.setAdapter(d);
            d.setOnClick(MainActivity.this);

            rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



        }

    }
}
