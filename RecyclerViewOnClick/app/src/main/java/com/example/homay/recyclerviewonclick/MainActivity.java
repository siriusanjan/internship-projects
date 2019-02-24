package com.example.homay.recyclerviewonclick;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements  CustomClickListener{
    RecyclerView recyclerView;
    DataAdapter dataAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview_container);
        DataAdapter dataAdapter = new DataAdapter();
dataAdapter.setCustomClickListener(this);
        recyclerView.setAdapter(dataAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


    @Override
    public void onClickCallback(int position) {
        Toast.makeText(MainActivity.this,"Clicked "+position,Toast.LENGTH_SHORT).show();
    }
}
