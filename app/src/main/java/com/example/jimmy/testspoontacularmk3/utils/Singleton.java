package com.example.jimmy.testspoontacularmk3.utils;

public class Singleton {
//Not in use as changed approach towards end of app development, but here for future reference sake
    private static Singleton INSTANCE = null;
    private String dataHolder = "";

    private Singleton() {};

    public static Singleton getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Singleton();
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
