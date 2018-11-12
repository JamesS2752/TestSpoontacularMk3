package com.example.jimmy.testspoontacularmk3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jimmy.testspoontacularmk3.model.ApIService;
import com.example.jimmy.testspoontacularmk3.model.api.RecipeInformation;
import com.example.jimmy.testspoontacularmk3.utils.Singleton;
import com.example.jimmy.testspoontacularmk3.utils.SingletonTwo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

public class Frag3 extends Fragment {
    private ApIService spoonacularService;
    private static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    private static final OkHttpClient.Builder client = new OkHttpClient.Builder();

    private List<String> instructionsList = new ArrayList<>();
    public String chosenRecipe2 = "key0";

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

    public Frag3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag3_layout, container, false);

        final TextView instructionsTV = rootView.findViewById(R.id.instructions);

        Bundle bundle = this.getArguments();
        String chosenRecipe = bundle.getString(chosenRecipe2, "22");
        String parsedID = chosenRecipe.substring(chosenRecipe.indexOf("id=") + 3, chosenRecipe.indexOf(","));
        int id = Integer.parseInt(parsedID);
        boolean includeNutrition = false;

        final String BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/";

        spoonacularService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build().create(ApIService.class);

        Call<RecipeInformation> cal1 = spoonacularService.getRecipeInformation(Const.MASHAPE_KEY, "application/json", "application/json",
                id, includeNutrition);
        cal1.enqueue(new Callback<RecipeInformation>() {

            @Override
            public void onResponse(Call<RecipeInformation> cal1, Response<RecipeInformation> response) {
                RecipeInformation result = response.body();
                String data = result.toString();

                String pattern1 = "<li>";
                String pattern2 = "</li>";
                String regexString = Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2);
                Pattern pattern = Pattern.compile(regexString);
                Matcher matcher = pattern.matcher(data);
                while (matcher.find()) {
                    String instruction = matcher.group(1);
                    instructionsList.add(instruction);
                }

                int i = 1;
                StringBuilder builder = new StringBuilder();
                for (String instructs : instructionsList) {
                    builder.append("(" + i + ")" + instructs + "\n" + "\n");
                    i++;
                }
                instructionsTV.setText(builder.toString());

            }

            @Override
            public void onFailure(Call<RecipeInformation> call, Throwable t) {
                System.out.println(call.toString());
                t.printStackTrace();
            }
        });

        return rootView;
    }

}
