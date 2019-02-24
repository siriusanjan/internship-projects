package com.example.homay.ysurvey;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FragmentLogin extends Fragment {
EditText userID;
Button loginBtn;
String user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_login, container, false);


        userID = v.findViewById(R.id.user_id);

        loginBtn = v.findViewById(R.id.login_button);




        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = userID.getText().toString();
                fetchData(user);

            }
        });







        return v;
        //end of oncreateview
    }


    public void fetchData(String data){

        String userid = data;




    }



  //end of class
}
