package com.example.jimmy.testspoontacularmk3.presenter;

import com.example.jimmy.testspoontacularmk3.BasePresenter;
import com.example.jimmy.testspoontacularmk3.BaseView;

public interface MainActivityContract {
    interface Presenter extends BasePresenter{

    }

    interface View extends BaseView<Presenter> { //Handles views.. So like activities to be presented or views within an activity..?
        void ingredientEngineSubmit(); //THIS METHODS ARE IMPLEMENTED IN MAIN ACTIVITY WHICH IS IN VIEW FILE.. THAT WAY IT HAS ACCESS TO R, VIEWS & CONTEXT ETC
    }
}
