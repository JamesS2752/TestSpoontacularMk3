package com.example.jimmy.testspoontacularmk3.presenter;

import android.content.Context;
import android.content.Intent;

import com.example.jimmy.testspoontacularmk3.Const;
import com.example.jimmy.testspoontacularmk3.model.ApIService;
import com.example.jimmy.testspoontacularmk3.model.api.Recipe;
import com.jakewharton.retrofit2.adapter.rxjava2.Result;

import java.io.IOException;
import java.util.ArrayList;
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

public class ResultsPresenter implements ResultsContract.Presenter {

    private Context context;
    private ResultsContract.View view;

    public static List<String> recipeData = new ArrayList<>();
    public static List<String> recipeTitle = new ArrayList<>();
    public static List<String> recipeImageList = new ArrayList<>();
    public static List<String> recipeLikesList = new ArrayList<>();
    public static List<String> recipeIDs = new ArrayList<>();
    public static List<Integer> recipeLikes = new ArrayList<>();

    private ApIService spoonacularService;
    private static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    private static final OkHttpClient.Builder client = new OkHttpClient.Builder();

    static {
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(logging);
        client.readTimeout(20, TimeUnit.SECONDS);
        client.connectTimeout(20, TimeUnit.SECONDS);
        client.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });
    }

    public ResultsPresenter(Context context, ResultsContract.View view) { //Spawn constructor
        this.context = context;
        this.view = view;
    }

    @Override
    public void start() {
        view.prepData();
    }

    @Override
    public void retrieveData(String ingredients) {
        spoonacularService = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build().create(ApIService.class);

        boolean mustFillIngredients;
        boolean mustLimitLicense;
        int numberAsInteger;
        int rankingAsInteger;
        try {
            mustFillIngredients = true;
            mustLimitLicense = true;
            numberAsInteger = 100;
            rankingAsInteger = 1;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }


        Call<List<Recipe>> call = spoonacularService.findRecipesByIngredients(Const.MASHAPE_KEY, "application/json", "application/json",
                mustFillIngredients, ingredients, mustLimitLicense, numberAsInteger, rankingAsInteger);
        call.enqueue(new Callback<List<Recipe>>() {

            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                String resultText = "";
                List<Recipe> result = response.body();

                initData(result);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                System.out.println(call.toString());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void initData(List response) {
        List result = response;

        recipeData.clear();
        for (int i = 0; i < result.size(); i++) {
            String temp = result.get(i).toString();
            recipeData.add(temp);
        }

        recipeImageList.clear();
        for (int i = 0; i < result.size(); i++) {
            String temp = result.get(i).toString();
            String recipeImage = temp.substring(temp.indexOf("image='") + 7, temp.indexOf("', used"));
            recipeImageList.add(recipeImage);
        }


        recipeTitle.clear();
        for (int i = 0; i < result.size(); i++) {
            String temp = result.get(i).toString();
            String recipeTitleTemp = temp.substring(temp.indexOf("title=") + 6, temp.indexOf(", ima"));
            recipeTitle.add(recipeTitleTemp);
        }

        recipeLikesList.clear();
        for (int i = 0; i < result.size(); i++) {
            String temp = result.get(i).toString();
            String recipeLikes = temp.substring(temp.indexOf("likes=") + 6, temp.indexOf("}"));
            recipeLikesList.add(recipeLikes);
        }

        recipeIDs.clear();
        for (int i = 0; i < result.size(); i++) {
            String temp = result.get(i).toString();
            String recipeLikes = temp.substring(temp.indexOf("id=") + 3, temp.indexOf(", title"));
            recipeIDs.add(recipeLikes);
        }

        for (Object indexValue : recipeLikesList) {
            for (int j = 0; j < recipeLikesList.size(); j++) {
                if (recipeImageList.get(j) instanceof String) {
                    String temp = (String) recipeLikesList.get(j);
                    int pass = Integer.parseInt(temp);
                    recipeLikes.add(pass);
                }
            }
        }
        Collections.sort(recipeLikes, Collections.<Integer>reverseOrder());

        view.initRecycleView(recipeData, recipeImageList, recipeTitle, recipeLikes, recipeIDs);
    }

    @Override
    public void organiseByLikes(List recipeData, List recipeImageList, List recipeTitle, List recipeLikesList, List recipeIDs) {
        System.out.println("JIMMY: " + recipeLikesList);

        for (Object indexValue : recipeLikesList) {
            for (int j = 0; j < recipeLikesList.size(); j++) {
                if (recipeImageList.get(j) instanceof String) {
                    String temp = (String) recipeLikesList.get(j);
                    int pass = Integer.parseInt(temp);
                    recipeLikes.add(pass);
                }
            }
        }
        Collections.sort(recipeLikes, Collections.<Integer>reverseOrder());

        System.out.println("JIMMY: " + recipeLikes);
    }
}
