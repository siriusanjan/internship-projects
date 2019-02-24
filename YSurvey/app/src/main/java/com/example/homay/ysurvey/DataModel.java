package com.example.homay.ysurvey;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DataModel {


    String time;
    String app;
    String name;
    String phone;
    String device;
    String location;
    ArrayList<String> files;
DataModel dataModel;
    public void DataModel(DataModel data) {

        this.dataModel = data;
        this.time = dataModel.getTime();
        this.app = dataModel.getApp();
        this.name = dataModel.getName();
        this.phone = dataModel.getPhone();
        this.device = dataModel.getDevice();
        this.location = dataModel.getLocation();
        this.files = dataModel.getFiles();
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<String> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<String> files) {
        this.files = files;
    }








}
