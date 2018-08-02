package com.example.jimmy.testspoontacularmk3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.jimmy.testspoontacularmk3.model.ApIService;
import com.example.jimmy.testspoontacularmk3.model.api.Recipe;
import com.example.jimmy.testspoontacularmk3.model.api.RecipeInformation;
import com.example.jimmy.testspoontacularmk3.view.RecyclerViewAdapter;

import java.io.IOException;
import java.util.ArrayList;
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

    private TextView resultTextView;
    private TextView idTextView;

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerview;
    private List<String> recipeData = new ArrayList<>();
    public static final String BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/";
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

        resultTextView = findViewById(R.id.result);
        idTextView = findViewById(R.id.id);

        Intent intent = getIntent();
        String ingredientsList = intent.getStringExtra(resultsStore);

        Call<List<Recipe>> call = spoonacularService.findRecipesByIngredients(Const.MASHAPE_KEY, "application/json", "application/json",
                mustFillIngredients, ingredientsList, mustLimitLicense, numberAsInteger, rankingAsInteger);
        call.enqueue(new Callback<List<Recipe>>() {

            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                String resultText = "";
                List<Recipe> result = response.body();

                for(int i = 0; i < result.size(); i++) {
                    String temp = result.get(i).toString();
                    recipeData.add(temp);
                }

                //CSEND data from Recipe list -> String list (toString())

                initRecycleView();
//                for(Recipe recipe : result) {// List to extract from :
//                    resultText += recipe.toString();
//                }


                for(int i = 0; i < result.size(); i++) {
                    System.out.println("WORK: " + result.get(i));
                } //JUST TO TEST THAT LIST IS FILLED!

                //String foodId = resultText.substring(resultText.indexOf("id=") + 3, resultText.indexOf(","));


//                for(int i = 0; i < result.size(); i++) {
//                    int temp = Integer.parseInt(foodId);
//                    ids.add(temp);
//                }


//                    resultTextView.setText(resultText); //Results printed

//                RecipeItemAdapter recipeItemAdapter = new RecipeItemAdapter(result.getResults());
//                recyclerview.setAdapter(recipeItemAdapter );


            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                System.out.println(call.toString());
                t.printStackTrace();
            }

        });


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        spoonacularService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build().create(ApIService.class);

        boolean includeNutrition = false;

        int id = 556470;

        Call<RecipeInformation> call2 = spoonacularService.getRecipeInformation(Const.MASHAPE_KEY, "application/json", "application/json",
                id, includeNutrition);
        call2.enqueue(new Callback<RecipeInformation>() {

            @Override
            public void onResponse(Call<RecipeInformation> call2, Response<RecipeInformation> response) {
                RecipeInformation result = response.body();
                String resultText = result.toString();
                System.out.println("TEST2:" + resultText);
                resultTextView.setText("text");
//                String summarised = resultText.substring(resultText.indexOf("summary='") + 9, resultText.indexOf(".'"));
//                for(Recipe recipe : result) {
//                    resultText += recipe.toString();
//                } //Prints result to string too simply, need to have it in recycle view
//                    result.setText(resultText); //Results printed



//                WebView wv = (WebView) findViewById(R.id.WebView01);
//                final String mimeType = "text/html";
//                final String encoding = "UTF-8";
//                wv.loadDataWithBaseURL("", summarised, mimeType, encoding, "");

            }

            @Override
            public void onFailure(Call<RecipeInformation> call2, Throwable t) {
                System.out.println(call2.toString());
                t.printStackTrace();
            }

        });



    }
    private void initRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, recipeData);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
