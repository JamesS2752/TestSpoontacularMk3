package com.example.jimmy.testspoontacularmk3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jimmy.testspoontacularmk3.model.ApIService;
import com.example.jimmy.testspoontacularmk3.model.api.Recipe;
import com.example.jimmy.testspoontacularmk3.model.api.RecipeInformation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeInformationAct extends AppCompatActivity {

    private ApIService spoonacularService;
    private static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    private static final OkHttpClient.Builder client = new OkHttpClient.Builder();
    private TextView recipeInformation;
    public static List<String> ingredientsList = new ArrayList<>();

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
                .baseUrl(Const.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build().create(ApIService.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_information);
        Intent intent = getIntent();
        String chosenRecipe = intent.getStringExtra(Results.chosenRecipeData);
        recipeInformation = findViewById(R.id.recipeInformation);
        String parsedID = chosenRecipe.substring(chosenRecipe.indexOf("id=") + 3, chosenRecipe.indexOf(","));
        int id = Integer.parseInt(parsedID);


        boolean includeNutrition = false;

        Call<RecipeInformation> cal1 = spoonacularService.getRecipeInformation(Const.MASHAPE_KEY, "application/json", "application/json",
                id, includeNutrition);
        cal1.enqueue(new Callback<RecipeInformation>() {

            @Override
            public void onResponse(Call<RecipeInformation> cal1, Response<RecipeInformation> response) {
                RecipeInformation result = response.body();
                String resultText = result.toString();


                String pattern1 = "name='";
                String pattern2 = "'";
                String regexString = Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2);
                Pattern pattern = Pattern.compile(regexString);
                Matcher matcher = pattern.matcher(resultText);
                while (matcher.find()) {
                    String ingredient = matcher.group(1); // Since (.*?) is capturing group 1
                    ingredientsList.add(ingredient);
                }

               // System.out.println("1James provided Ing: " + MainActivity.providedIngredientsList);
             //   System.out.println("2James ing list: " + ingredientsList.toString());

                for(int a = 0; a < (MainActivity.providedIngredientsList.size() - 1); a++) {
                    if(ingredientsList.contains(MainActivity.providedIngredientsList.get(a))){
                        if(a == 0) {
                            String prefix = "<b>";
                            int prefixIndex = ingredientsList.indexOf(MainActivity.providedIngredientsList.get(a));
                            System.out.println("3James PreAdd Prefix Position: " + ingredientsList.toString());
                            ingredientsList.add(prefixIndex, prefix);
                          //  System.out.println("3James PostAdd Prefix Position: " + ingredientsList.toString());

                            String suffix = "</b>";
                            int suffixIndex = ingredientsList.indexOf(MainActivity.providedIngredientsList.get(a)) + 1;
                          //  System.out.println("4James PreAdd Suffix Position: " + ingredientsList.toString() + "VALUE: " + suffixIndex);
                            ingredientsList.add(suffixIndex, suffix);
                       //     System.out.println("4James PostAdd Suffix Position: " + ingredientsList.toString());
                        } else {
                            String prefix = "<b>";
                            int prefixIndex = ingredientsList.indexOf(MainActivity.providedIngredientsList.get(a)) - 1;
                            ingredientsList.add(prefixIndex, prefix);

                            String suffix = "</b>";
                            int suffixIndex = ingredientsList.indexOf(MainActivity.providedIngredientsList.get(a)) + 1;
                            ingredientsList.add(suffixIndex, suffix);
                        }

                    }
                }

                String ingredientsListString = String.join(" ", ingredientsList); //MAYBE MAKE INTO STRING AND THEN APPEND BOLD SIGNS?
          //      System.out.println("5James String ing list " + ingredientsListString);
                String ingredientsListFormatted = ingredientsListString.replaceAll(" ","<br>");
                recipeInformation.setText(Html.fromHtml(ingredientsListFormatted,1));
            }

            @Override
            public void onFailure(Call<RecipeInformation> call, Throwable t) {
                System.out.println(call.toString());
                t.printStackTrace();
            }
        });


    }
}
