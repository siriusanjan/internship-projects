package com.example.homay.photext;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements FragmentCallBack {

    FragmentMainMenu fragmentMainMenu;
    FrameLayout frameLayout;
    FragmentDisplayImage fragmentDisplayImage;

    FragmentManager fragmentManager;
    Menu menu;
    private String TAG = "MainActivity";
    //flag
    boolean insideMainMenu = true;
    boolean insideImageEdit = false;
    boolean insideDisplayImage = false;
    FragmentEditImage fragmentEditImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //main activity hosts all fragment
        //first FragmentMainMenu, second fragmentEditImage


        //this loads main menu fragment by default

        fragmentMainMenu = new FragmentMainMenu();
        fragmentMainMenu.setFragmentCallBack(this);
        frameLayout = findViewById(R.id.frame_layout_container);


        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_layout_container, fragmentMainMenu)
                .commit();


        fragmentEditImage = new FragmentEditImage();

        fragmentDisplayImage = new FragmentDisplayImage();

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        return true;
    }

    public void startPage() {
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout_container, fragmentMainMenu)
                .commit();
        insideMainMenu = true;
        insideDisplayImage = false;
        insideImageEdit = false;
        invalidateOptionsMenu();
    }

    //this loads menu based on fragment
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (insideMainMenu) {

            getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        }
        if (insideImageEdit) {

            getMenuInflater().inflate(R.menu.menu_edit, menu);
        }

        if (insideDisplayImage) {
            getMenuInflater().inflate(R.menu.menu_display, menu);
        }
        return true;

    }

    //performs action based on menu item selected from action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_about:


                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Developed during the winter of 2018. \nNo animals were harmed in the making.")
                        .setTitle("About")
                        .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                AlertDialog alertDialogAbout = builder.create();
                alertDialogAbout.show();

                return true;

            case R.id.menu_exit:
                //Show exit dialog box
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setMessage("Are your sure?")
                        .setPositiveButton("Yep", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("Nah", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialogExit = dialogBuilder.create();
                alertDialogExit.show();
                return true;


            case R.id.menu_save_image:
                fragmentEditImage.saveEditedImage();
                return true;
            case R.id.menu_done:
                startPage();
                return true;

            case R.id.menu_cancel_image:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("Cancel")
                        .setMessage("Made another wrong life decision, didn't you?")


                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                   .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startPage();
                }
            });
AlertDialog alertDialog = builder1.create();
alertDialog.show();
                return true;
        }


        return false;

    }


    //call back for image selection, done by first fragment i.e  FragmentMainMenu

    @Override
    public void onImageSelect(Uri image) {
        insideMainMenu = false;
        insideImageEdit = true;
        insideDisplayImage = false;
        invalidateOptionsMenu();


        Bundle bundle = new Bundle();
        bundle.putString("IMAGE_DATA", image.toString());
        fragmentEditImage.setArguments(bundle);
        fragmentEditImage.setFragmentCallBack(this);


        fragmentManager.beginTransaction().replace(R.id.frame_layout_container, fragmentEditImage)
                .commit();


    }

    @Override
    public void onImageSave(String filepath) {
        Log.i("callback data", filepath);
        Bundle bundle = new Bundle();
        bundle.putString("IMAGE_PATH", filepath);
        fragmentDisplayImage.setArguments(bundle);

        fragmentManager.beginTransaction().replace(R.id.frame_layout_container, fragmentDisplayImage).commit();
        insideDisplayImage = true;
        insideImageEdit = false;
        insideMainMenu = false;
        invalidateOptionsMenu();

    }


}
