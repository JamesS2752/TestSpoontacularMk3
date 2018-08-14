package com.example.jimmy.testspoontacularmk3.presenter;

import com.example.jimmy.testspoontacularmk3.BasePresenter;
import com.example.jimmy.testspoontacularmk3.BaseView;
import com.jakewharton.retrofit2.adapter.rxjava2.Result;

import java.util.List;

import retrofit2.Response;

public interface ResultsContract {
    interface Presenter extends BasePresenter {
        void retrieveData(String ingredients);
        void initData(List response);
    }

    interface View extends BaseView<ResultsContract.Presenter> {
        void prepData();
        void initRecycleView(List recipeData, List recipeImageList, List recipeTitle, List recipeLikesList, List recipeIDs); //Pass as reference to fix recyclerview issues
    }
}
