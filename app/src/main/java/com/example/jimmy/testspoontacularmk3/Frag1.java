package com.example.jimmy.testspoontacularmk3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jimmy.testspoontacularmk3.model.ApIService;
import com.example.jimmy.testspoontacularmk3.model.api.RecipeInformation;
import com.example.jimmy.testspoontacularmk3.model.api.RecipeSummary;
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

public class Frag1 extends Fragment {
    private ApIService spoonacularService;
    private static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    private static final OkHttpClient.Builder client = new OkHttpClient.Builder();

    public static String data;
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

    public Frag1() {
        // Required empty public constructor
    }

    private ImageView vegetarianOption;
    private ImageView veganOption;
    private ImageView glutenFreeOption;
    private ImageView dairyFreeOption;
    private ImageView ketogenicOption;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag1_layout, container, false);

        final TextView recipeTitle = rootView.findViewById(R.id.recipeTitle);
        final TextView servesInt = rootView.findViewById(R.id.servesInt);
        final TextView desc = rootView.findViewById(R.id.description);

        vegetarianOption = rootView.findViewById(R.id.vegetarianBoolean);
        veganOption = rootView.findViewById(R.id.veganBoolean);
        glutenFreeOption = rootView.findViewById(R.id.glutenFreeBoolean);
        dairyFreeOption = rootView.findViewById(R.id.dairyFreeBoolean);
        ketogenicOption = rootView.findViewById(R.id.ketogenicBoolean);

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
                data = result.toString();

                String Title = data.substring(data.indexOf("title='") + 7, data.indexOf("', readyIn"));

                recipeTitle.setText(Title);

                String pattern1 = "servings=";
                String pattern2 = ", credit";
                String regexString = Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2);
                Pattern pattern = Pattern.compile(regexString);
                Matcher matcher = pattern.matcher(data);
                while (matcher.find()) {
                    String servings = matcher.group(1);
                    servesInt.setText(servings);
                }

                String vegetarianBoolean = data.substring(data.indexOf("vegetarian=") + 11, data.indexOf("vegetarian=") + 16);
                if (vegetarianBoolean.equals("true,")) {
                    vegetarianOption.setImageResource(R.drawable.ic_tick_24dp);
                } else {
                    vegetarianOption.setImageResource(R.drawable.ic_cross_24dp);
                }

                String veganBoolean = data.substring(data.indexOf("vegan=") + 6, data.indexOf("vegan=") + 11);
                if (veganBoolean.equals("true,")) {
                    veganOption.setImageResource(R.drawable.ic_tick_24dp);
                } else {
                    veganOption.setImageResource(R.drawable.ic_cross_24dp);
                }

                String glutenFreeB = data.substring(data.indexOf("glutenFree=") + 11, data.indexOf("glutenFree=") + 16);
                if (glutenFreeB.equals("true,")) {
                    glutenFreeOption.setImageResource(R.drawable.ic_tick_24dp);
                } else {
                    glutenFreeOption.setImageResource(R.drawable.ic_cross_24dp);
                }

                String dairyFreeB = data.substring(data.indexOf("dairyFree=") + 10, data.indexOf("dairyFree=") + 15);
                if (dairyFreeB.equals("true,")) {
                    dairyFreeOption.setImageResource(R.drawable.ic_tick_24dp);
                } else {
                    dairyFreeOption.setImageResource(R.drawable.ic_cross_24dp);
                }

                String ketogenic = data.substring(data.indexOf("ketogenic=") + 10, data.indexOf("ketogenic=") + 15);
                if (ketogenic.equals("true,")) {
                    ketogenicOption.setImageResource(R.drawable.ic_tick_24dp);
                } else {
                    ketogenicOption.setImageResource(R.drawable.ic_cross_24dp);
                }
            }

            @Override
            public void onFailure(Call<RecipeInformation> call, Throwable t) {
                System.out.println(call.toString());
                t.printStackTrace();
            }
        });

        Call<RecipeSummary> call2 = spoonacularService.getRecipeSummary(Const.MASHAPE_KEY, "application/json", "application/json",
                id);
        call2.enqueue(new Callback<RecipeSummary>() {
            String resultText;

            @Override
            public void onResponse(Call<RecipeSummary> call2, Response<RecipeSummary> response) {
                RecipeSummary result = response.body();
                resultText = result.toString();

                String pattern1 = "summary='";
                String pattern2 = "'}";
                String regexString = Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2);
                Pattern pattern = Pattern.compile(regexString);
                Matcher matcher = pattern.matcher(resultText);
                ArrayList<String> descriptionTemp = new ArrayList<>();
                while (matcher.find()) {
                    String ingredient = matcher.group(1); // Since (.*?) is capturing group 1
                    descriptionTemp.add(ingredient);
                }

                String filter = String.join(",", descriptionTemp);
                String filter1 = filter.replaceAll("<b>", "");
                String filter2 = filter1.replaceAll("</b>", "");


//POINT OF ALL THIS LAST BIT OF CODE IS TO REMOVE EVERYTHING AFTER THE SECOND LAST FULLSTOP!
                int endIndex = ordinalIndexOf(filter2, ".", 4);
                String filter3 = filter2.substring(0, endIndex + 1);
                desc.setText(filter3);
            }

            @Override
            public void onFailure(Call<RecipeSummary> call2, Throwable t) {
                System.out.println(call2.toString());
                t.printStackTrace();
            }
        });

        return rootView;
    }

    public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }

}
