package com.example.jimmy.testspoontacularmk3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jimmy.testspoontacularmk3.model.ApIService;
import com.example.jimmy.testspoontacularmk3.model.api.RecipeInformation;
import com.example.jimmy.testspoontacularmk3.model.api.RecipeSummary;

import java.io.IOException;
import java.util.ArrayList;
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

import static android.view.View.GONE;

public class Frag1 extends Fragment {
    private ApIService spoonacularService;
    private static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    private static final OkHttpClient.Builder client = new OkHttpClient.Builder();

    public String chosenRecipe2 = "key0";

    private ProgressBar progressBar;
    private FrameLayout frameLayout;

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

    TextView recipeTitle;
    TextView servesInt;
    TextView desc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag1_layout, container, false);

        progressBar = rootView.findViewById(R.id.progressBarFrag1);
        frameLayout = rootView.findViewById(R.id.frameLayout);

        vegetarianOption = rootView.findViewById(R.id.vegetarianBoolean);
        veganOption = rootView.findViewById(R.id.veganBoolean);
        glutenFreeOption = rootView.findViewById(R.id.glutenFreeBoolean);
        dairyFreeOption = rootView.findViewById(R.id.dairyFreeBoolean);
        ketogenicOption = rootView.findViewById(R.id.ketogenicBoolean);

        recipeTitle = rootView.findViewById(R.id.recipeTitle);
        servesInt = rootView.findViewById(R.id.servesInt);
        desc = rootView.findViewById(R.id.description);

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

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<RecipeInformation> cal1, Response<RecipeInformation> response) {
                RecipeInformation result = response.body();

                setupRecipeSummary(result);
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

                hideProgressBar();
                desc.setText(summarySanitiser(resultText));
            }

            @Override
            public void onFailure(Call<RecipeSummary> call2, Throwable t) {
                hideProgressBar();
                System.out.println(call2.toString());
                t.printStackTrace();
            }
        });

        return rootView;
    }

    private void setupRecipeSummary(RecipeInformation result) {
        String title = result.getTitle();
        recipeTitle.setText(title);

        int servings = result.getServings();
        servesInt.setText(Integer.toString(servings));

        boolean vegetarianBoolean = result.isVegetarian();
        if (vegetarianBoolean) {
            vegetarianOption.setImageResource(R.drawable.ic_tick_24dp);
        } else {
            vegetarianOption.setImageResource(R.drawable.ic_cross_24dp_red);
        }

        boolean veganBoolean = result.isVegan();
        if (veganBoolean) {
            veganOption.setImageResource(R.drawable.ic_tick_24dp);
        } else {
            veganOption.setImageResource(R.drawable.ic_cross_24dp_red);
        }

        boolean glutenFreeB = result.isGlutenFree();
        if (glutenFreeB) {
            glutenFreeOption.setImageResource(R.drawable.ic_tick_24dp);
        } else {
            glutenFreeOption.setImageResource(R.drawable.ic_cross_24dp_red);
        }

        boolean dairyFreeB = result.isDairyFree();
        if (dairyFreeB) {
            dairyFreeOption.setImageResource(R.drawable.ic_tick_24dp);
        } else {
            dairyFreeOption.setImageResource(R.drawable.ic_cross_24dp_red);
        }

        boolean ketogenic = result.isKetogenic();
        if (ketogenic) {
            ketogenicOption.setImageResource(R.drawable.ic_tick_24dp);
        } else {
            ketogenicOption.setImageResource(R.drawable.ic_cross_24dp_red);
        }
    }

    String summarySanitiser(String resultText) {
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

        return filter3;
    }

    public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }

    public void hideProgressBar() {
        progressBar.setVisibility(GONE);
        frameLayout.setVisibility(GONE);
    }

}
