package com.example.jimmy.testspoontacularmk3.utils;

import java.util.ArrayList;
import java.util.List;
//Not in use as changed approach towards end of app development, but here for future reference sake
public class SingletonTwo {

    private static SingletonTwo INSTANCE = null;
    private String dataHolder = "";

    private SingletonTwo() {};

    public static SingletonTwo getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new SingletonTwo();
        }
        return INSTANCE;
    }

    public String getDataHolder() {
        return dataHolder;
    }

    public void setDataHolder(String dataHolder) {
        this.dataHolder = dataHolder;
    }
}
