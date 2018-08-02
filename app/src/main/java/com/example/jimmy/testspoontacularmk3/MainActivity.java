package com.example.jimmy.testspoontacularmk3;
//IN THIS VERSION: [v0.4] Displays relevant recipes in a recycledvIew, but disorganised and not displayed correctly, but RV works
// Next Version: Be able to ingredients for each recipe, list it under each resultand highlight queried results
// Note: Gotta reimplmenet RXjava support + MVP structure soon

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jimmy.testspoontacularmk3.R;
import com.example.jimmy.testspoontacularmk3.model.ApIService;
import com.example.jimmy.testspoontacularmk3.model.api.Recipe;

import java.io.IOException;
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

public class MainActivity extends AppCompatActivity {

    // Trailing slash is needed
    public static final String BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/";
    public static String resultsStore = "key0";


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


    private EditText ingredientsEditText;
    private EditText rankingEditText;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spoonacularService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build().create(ApIService.class);

        ingredientsEditText = (EditText) findViewById(R.id.ingredients_edit_text);


        sendButton = (Button) findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredients = ingredientsEditText.getText().toString();


                submitIngredients(ingredients);
            }
        });

    }



    private void submitIngredients(String ingredientsList) {

        if(ingredientsList != null && !ingredientsList.trim().isEmpty()) {



            String ingredients = ingredientsList.replace(" ", "").trim();

            Intent intent = new Intent(getApplicationContext(), Results.class);
            intent.putExtra(resultsStore, ingredientsList);
            startActivity(intent);



        }
    }

}
