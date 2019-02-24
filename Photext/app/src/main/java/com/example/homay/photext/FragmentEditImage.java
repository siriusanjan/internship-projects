package com.example.homay.photext;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

public class FragmentEditImage extends Fragment implements ViewCallback {
    Uri imageUri;
    private String TAG = "FragmentEditImage";

    FragmentCallBack fragmentCallBack;

    Button buttonAddText;
    EditText editText;
    SeekBar seekBar;
    DrawingPaper drawingPaper;

    String stringMyData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_image, container, false);
        stringMyData = "";


        buttonAddText = view.findViewById(R.id.button_add_text);
        editText = view.findViewById(R.id.edit_text_add_text);
        seekBar = view.findViewById(R.id.seekbar_global);
        drawingPaper = view.findViewById(R.id.drawing_paper);
        drawingPaper.setViewCallBack(this);

        editText.setVisibility(View.INVISIBLE);
        seekBar.setVisibility(View.INVISIBLE);


        buttonAddText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setVisibility(View.VISIBLE);
                seekBar.setVisibility(View.VISIBLE);

            }
        });

        seekBar.setMax(80);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                drawingPaper.setTextSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                drawingPaper.setTextSize(seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                drawingPaper.setTextSize(seekBar.getProgress());

            }
        });


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                drawingPaper.setText(s.toString());

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                drawingPaper.setText(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
                drawingPaper.setText(s.toString());

            }
        });


        String imageString = getArguments().getString("IMAGE_DATA");

        drawingPaper.setImageDimension(imageString);

        return view;

    }

    public void saveEditedImage() {

        drawingPaper.saveBitmap();
    }


    @Override
    public void OnImageSave(String filepath) {
        fragmentCallBack.onImageSave(filepath);
        editText.setText(null);
        seekBar.setProgress(30);


    }

    public void setFragmentCallBack(FragmentCallBack fragmentCallBack) {

        this.fragmentCallBack = fragmentCallBack;
    }

}
