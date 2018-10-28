package com.example.jimmy.testspoontacularmk3.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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

public class Results extends AppCompatActivity implements ResultsContract.View {
    private ResultsContract.Presenter presenter;
    ResultsPresenter resultsPresenter;

    private TextView resultTextView;
    private TextView idTextView;

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerview;

    public static List<String> recipeData;// = new ArrayList<>();
    public static List<String> recipeTitle;// = new ArrayList<>();
    public static List<String> recipeImageList;// = new ArrayList<>();
    public static List<String> recipeLikesList;// = new ArrayList<>();//No concrete implementation allowed, this approach works but not recommended. Just pass as variable through M like in prev issue
    public static List<String> recipeIDs;// = new ArrayList<>();

    public static String chosenRecipeData = "key0";
    public static String resultsStore = "key0";

    private List<Integer> ids = new ArrayList<>();

    //public String ingredientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        presenter = new ResultsPresenter(getApplicationContext(), this);
        presenter.start();
    }


    @Override
    public void prepData() {
        Intent intent = getIntent();
        String ingredientsList = intent.getStringExtra(resultsStore);
        System.out.println("JAMES223" + ingredientsList);
        presenter.retrieveData(ingredientsList);
    }

    @Override
    public void initRecycleView(List recipeData, List recipeImageList, List recipeTitle, List recipeLikes, List recipeIDs) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView2);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, recipeData, recipeImageList, recipeTitle, recipeLikes, recipeIDs);
        //Jimmy TODO Atm Recipe likes are passed here


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.notifyDataSetChanged();


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                1);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void setPresenter(ResultsContract.Presenter presenter) {

    }
}
