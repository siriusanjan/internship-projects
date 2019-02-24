package com.example.homay.addtextinimage;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ScaleActivity extends AppCompatActivity implements View.OnClickListener {
    ScaledView scaledView;
    Button buttonSelectImage, buttonAddText;
    ToggleButton toggleButton;
    TextView textViewLog, textViewMessage;
    String myText;
    EditText editTextText;
    float viewX_, viewY_, imageX_, imageY_, imageAspectRatio_, viewAspectRatio_, scaleVal_;

    String message_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale);
        myText = "";

        viewX_ = 0;
        viewY_ = 0;
        imageX_ = 0;
        imageY_ = 0;
        imageAspectRatio_ = 0;
        viewAspectRatio_ = 0;
        message_ = "";
        scaleVal_ = 0;
        textViewMessage = findViewById(R.id.text_view_message);
        buttonAddText = findViewById(R.id.button_add_text);
        editTextText = findViewById(R.id.edit_text_mytext);
        editTextText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                scaledView.setText(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                scaledView.setText(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
                scaledView.setText(s.toString());

            }
        });

        editTextText.setVisibility(View.INVISIBLE);
        scaledView = findViewById(R.id.customScaledView);
        buttonSelectImage = findViewById(R.id.button_select_image);
        toggleButton = findViewById(R.id.toggle_scale);
        textViewLog = findViewById(R.id.text_view_log);

        buttonSelectImage.setOnClickListener(this);
        buttonAddText.setOnClickListener(this);


scaledView.setDataFetcher(new Fetcher() {
    @Override
    public void onFetchImageDimension(float x, float y) {
        imageY_ = y;
        imageX_ = x;
        setLog();

    }

    @Override
    public void onFetchViewDimension(float x, float y) {
        viewX_ = x;
        viewY_ = y;
        setLog();

    }

    @Override
    public void onFetchScaledVal(float scaledValue) {
        scaleVal_ = scaledValue;
        setLog();

    }

    @Override
    public void onFetchAspectRatio(float viewAspectRatio, float imageAspectRatio) {
        viewAspectRatio_ = viewAspectRatio;
        imageAspectRatio_ = imageAspectRatio;
setLog();

    }

    @Override
    public void onMessageReceive(String message) {
        message_= message;
        setLog();

    }


});







//end of on create
    }



    private void setLog() {

        textViewLog.setText("ViewXY " + viewX_ + " " + viewY_ + "\n" + "ImageXY " + imageX_ + " " + imageY_ + "\n"
                + "Aspect Ratios [View & Image] " + viewAspectRatio_ + " " + imageAspectRatio_ + "\n" + "Scale Val " + scaleVal_);

textViewMessage.setText(message_);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_select_image:
                Intent i = new Intent();
                i.setAction(Intent.ACTION_PICK);
                i.setType("image/*");

                startActivityForResult(Intent.createChooser(i, "Select Image"), 500);

                break;

            case R.id.button_add_text:
                textViewLog.setVisibility(View.INVISIBLE);
                textViewMessage.setVisibility(View.INVISIBLE);

                editTextText.setVisibility(View.VISIBLE);



        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 500 && resultCode == RESULT_OK) {
            Uri imageURI = data.getData();
            scaledView.setImageLocation(imageURI);


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                scaledView.saveImage();
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    //end of class
}
