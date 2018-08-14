package com.example.jimmy.testspoontacularmk3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jimmy.testspoontacularmk3.model.ApIService;
import com.example.jimmy.testspoontacularmk3.model.api.Recipe;
import com.example.jimmy.testspoontacularmk3.model.api.RecipeInformation;
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

public class Results extends AppCompatActivity {
    //    public static String chosenRecipe = "key0";
    private TextView resultTextView;
    private TextView idTextView;

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerview;

    public static List<String> recipeData = new ArrayList<>();
    public static List<String> recipeTitle = new ArrayList<>();
    public static List<String> recipeImageList = new ArrayList<>();
    public static List<String> recipeLikesList = new ArrayList<>();
    public static List<String> recipeIDs = new ArrayList<>();

    public static final String BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/";

    public static String chosenRecipeData = "key0";
    public static String resultsStore = "key0";

    private List<Integer> ids = new ArrayList<>();

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
//                        .header("Accept", "application/json")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        spoonacularService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
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

        Intent intent = getIntent();
        String ingredientsList = intent.getStringExtra(resultsStore);

        Call<List<Recipe>> call = spoonacularService.findRecipesByIngredients(Const.MASHAPE_KEY, "application/json", "application/json",
                mustFillIngredients, ingredientsList, mustLimitLicense, numberAsInteger, rankingAsInteger);
        call.enqueue(new Callback<List<Recipe>>() {

            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                String resultText = "";
                List<Recipe> result = response.body();


                for (int i = 0; i < result.size(); i++) {
                    String temp = result.get(i).toString();
                    recipeData.add(temp);
                }

                for (int i = 0; i < result.size(); i++) {
                    String temp = result.get(i).toString();
                    String recipeImage = temp.substring(temp.indexOf("image='") + 7, temp.indexOf("', used"));
                    recipeImageList.add(recipeImage);
                }

                for (int i = 0; i < result.size(); i++) {
                    String temp = result.get(i).toString();
                    String recipeTitleTemp = temp.substring(temp.indexOf("title=") + 6, temp.indexOf(", ima"));
                    recipeTitle.add(recipeTitleTemp);
                }

                for (int i = 0; i < result.size(); i++) {
                    String temp = result.get(i).toString();
                    String recipeLikes = temp.substring(temp.indexOf("likes=") + 6, temp.indexOf("}"));
                    recipeLikesList.add(recipeLikes);

//                    String likesbyString = recipeLikesList.toString();
//                    List<String> myList = new ArrayList<String>(Arrays.asList(likesbyString.split(",")));
//                    Collections.sort(myList);
//                    if(i == (result.size() - 1)) {
//                        System.out.println("JAMESSAVERY22" + myList.toString());
//                    }
                }

                for (int i = 0; i < result.size(); i++) {
                    String temp = result.get(i).toString();
                    String recipeLikes = temp.substring(temp.indexOf("id=") + 3, temp.indexOf(", title"));
                    recipeIDs.add(recipeLikes);
                }


                initRecycleView();


                for (int i = 0; i < result.size(); i++) {
                    System.out.println("WORK: " + result.get(i));
                }

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                System.out.println(call.toString());
                t.printStackTrace();
            }
        });


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

    }

    private void initRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, recipeData, recipeImageList, recipeTitle, recipeLikesList, recipeIDs);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                1);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }


}
