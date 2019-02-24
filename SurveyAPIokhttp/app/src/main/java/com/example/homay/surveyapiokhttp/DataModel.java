package com.example.homay.surveyapiokhttp;

import java.util.ArrayList;

public class DataModel {


    String time;
    String app;
    String name;
    String phone;
    String device;
    String location;
    ArrayList<String> files;

    public DataModel() {

    }


    public void setTime(String time) {
        this.time = time;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setFiles(ArrayList<String> files) {
        this.files = files;
    }


    public String getTime() {
        return time;
    }


    public String getApp() {
        return app;
    }


    public String getName() {
        return name;
    }


    public String getPhone() {
        return phone;
    }


    public String getDevice() {
        return device;
    }


    public String getLocation() {
        return location;
    }


    public ArrayList<String> getFiles() {
        return files;
    }


}
