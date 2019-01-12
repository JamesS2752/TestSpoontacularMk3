package com.example.jimmy.testspoontacularmk3.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jimmy.testspoontacularmk3.Const;
import com.example.jimmy.testspoontacularmk3.R;
import com.example.jimmy.testspoontacularmk3.model.ApIService;
import com.example.jimmy.testspoontacularmk3.model.api.Recipe;
import com.example.jimmy.testspoontacularmk3.model.api.RecipeInformation;
import com.example.jimmy.testspoontacularmk3.presenter.MainActivityContract;
import com.example.jimmy.testspoontacularmk3.presenter.MainActivityPresenter;
import com.example.jimmy.testspoontacularmk3.presenter.ResultsContract;
import com.example.jimmy.testspoontacularmk3.presenter.ResultsPresenter;
import com.example.jimmy.testspoontacularmk3.view.RecyclerViewAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.GONE;

public class Results extends AppCompatActivity implements ResultsContract.View {
    private ResultsContract.Presenter presenter;

    private ProgressBar progressBar;

    public static String chosenRecipeData = "key0";
    public static String resultsStore = "key0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        progressBar = findViewById(R.id.progressBar);

        presenter = new ResultsPresenter(getApplicationContext(), this);
        presenter.start();
    }


    @Override
    public void prepData() {
        Intent intent = getIntent();
        String ingredientsList = intent.getStringExtra(resultsStore);
        presenter.retrieveData(ingredientsList);
    }




    @Override
    public void initRecycleView(List recipeData, List recipeImageList, List recipeTitle, List recipeLikesList, List recipeIDs) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView2);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, recipeData, recipeImageList, recipeTitle, recipeLikesList, recipeIDs);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.notifyDataSetChanged();


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                1);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(GONE);
    }

    @Override
    public void setPresenter(ResultsContract.Presenter presenter) {

    }
}
