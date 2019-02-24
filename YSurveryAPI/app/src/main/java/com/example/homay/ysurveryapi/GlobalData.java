package com.example.homay.ysurveryapi;

import java.util.ArrayList;

public   class GlobalData {

    public static ArrayList<DataModel> globaldata;

    public static void setGlobaldata(ArrayList<DataModel> dataModels) {
        globaldata = dataModels;
    }

    public static ArrayList<DataModel> getGlobaldata() {
        return globaldata;
    }



}


