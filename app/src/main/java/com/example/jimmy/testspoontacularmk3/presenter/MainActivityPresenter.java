package com.example.jimmy.testspoontacularmk3.presenter;

import android.content.Context;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private Context context;
    private MainActivityContract.View view;

    public MainActivityPresenter(Context context, MainActivityContract.View view) { //Spawn constructor
        this.context = context;
        this.view = view;
        //view.setPresenter(this);
        view.ingredientEngineSubmit();
    }

    @Override
    public void start() {

    }


}
