package com.example.homay.addtextinimage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CustomCanvas customCanvas;
    String TAG = "MainActivity";
    EditText editTextMyText;
    String textToDisplay;
    SeekBar seekBar;
    //Bottom Menu
    int bottomMenuFlag = 0;
    TextView textColor, highlightColor, textSize, width, height, cornerRadius;
    int valTextColor, valHighLightColor, valTextSize = 30, valWidth, valHeight, valCornerRadius;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customCanvas = findViewById(R.id.custom_canvas);
        editTextMyText = findViewById(R.id.edit_text_add);
        seekBar = findViewById(R.id.seekbar);

        textColor = findViewById(R.id.text_color);
        highlightColor = findViewById(R.id.text_highlight_color);
        textSize = findViewById(R.id.text_size);
        width = findViewById(R.id.text_width);
        height = findViewById(R.id.text_height);
        cornerRadius = findViewById(R.id.text_corner);


        //Menu OnClickListernets

        textColor.setOnClickListener(this);
        highlightColor.setOnClickListener(this);
        textSize.setOnClickListener(this);
        width.setOnClickListener(this);
        height.setOnClickListener(this);
        cornerRadius.setOnClickListener(this);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                switch (bottomMenuFlag) {
                    case 2: //text size2
                        customCanvas.setTextSize(progress);

                        break;

                    case 3: //width
                        break;
                    case 4: //height
                        break;

                    case 5: //corner

                        break;
                }


            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                customCanvas.setChangedValues(seekBar.getProgress());
                switch (bottomMenuFlag) {
                    case 2: //text size2
                        customCanvas.setTextSize(seekBar.getProgress());

                        break;

                    case 3: //width
                        break;
                    case 4: //height
                        break;

                    case 5: //corner

                        break;
                }

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                customCanvas.setChangedValues(seekBar.getProgress());
                switch (bottomMenuFlag) {
                    case 2: //text size2
                        customCanvas.setTextSize(seekBar.getProgress());
                        setDefaultValue(2, seekBar.getProgress());
                        break;

                    case 3: //width
                        break;
                    case 4: //height
                        break;

                    case 5: //corner

                        break;
                }

            }
        });

        //Add text
        editTextMyText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                textToDisplay = editTextMyText.getText().toString().trim();
                customCanvas.setText(textToDisplay);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textToDisplay = editTextMyText.getText().toString().trim();
                customCanvas.setText(textToDisplay);

            }

            @Override
            public void afterTextChanged(Editable s) {
                textToDisplay = editTextMyText.getText().toString().trim();
                customCanvas.setText(textToDisplay);

            }
        });


//End of oncreate
    }


    //Menu save
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_save:
                Log.i(TAG, "Option Menu pressed");
                boolean result = customCanvas.saveImage();
                if (result) {

                    Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Saving Failed", Toast.LENGTH_SHORT).show();
                }

            case R.id.menu_reset:
                customCanvas.reset();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_color:

                break;

            case R.id.text_highlight_color:

                break;

            case R.id.text_height:

                break;

            case R.id.text_corner:

                break;

            case R.id.text_size:
                seekBar.setVisibility(View.VISIBLE);
                bottomMenuFlag = 2;
                seekBar.setMax(200);
                seekBar.setProgress(getDefaultMenuValue(2));


                break;

            case R.id.text_width:

                break;
        }
    }

    private int getDefaultMenuValue(int i) {

        switch (i) {
            case 0:
                return valTextColor;


            case 1:
                return valHighLightColor;


            case 2:
                return valTextSize;
            case 3:
                return valWidth;
            case 4:
                return valHeight;
            case 5:
                return valCornerRadius;
        }

        return 0;
    }

    private int setDefaultValue(int menuItem, int value) {
        switch (menuItem) {
            case 0: //text color default

            case 1: //highlight color default

            case 2: // text size default
                valTextSize = value;

            case 3: // text width default
            case 4: // text height default

            case 5: //text corner default

        }

        return 0;

    }


//end of class
}
