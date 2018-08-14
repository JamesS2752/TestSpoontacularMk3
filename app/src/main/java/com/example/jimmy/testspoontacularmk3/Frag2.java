package com.example.jimmy.testspoontacularmk3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jimmy.testspoontacularmk3.model.ApIService;
import com.example.jimmy.testspoontacularmk3.model.api.RecipeInformation;
import com.example.jimmy.testspoontacularmk3.view.MainActivity;

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

public class Frag2 extends Fragment {
    private ApIService spoonacularService;
    private static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    private static final OkHttpClient.Builder client = new OkHttpClient.Builder();

    private TextView recipeInformationTextView;
    private List<String> ingredients = new ArrayList<>();
    public String chosenRecipe22 = "key01";
    public String chosenRecipe2 = "key0";
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

    public Frag2() {
        // Required empty public constructor
    }

    //List<String> ingredientsList = Arrays.asList(data2.split("\\s*,\\s*"));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag2_layout, container, false);

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

        final TextView providedIngs = (TextView) rootView.findViewById(R.id.providedIngredients);
        final TextView extraIngs = (TextView) rootView.findViewById(R.id.ExtraIngredients);

        Call<RecipeInformation> cal1 = spoonacularService.getRecipeInformation(Const.MASHAPE_KEY, "application/json", "application/json",
                id, includeNutrition);
        cal1.enqueue(new Callback<RecipeInformation>() {

            String usedIngs = " ";
            String remainingIngs = " ";

            @Override
            public void onResponse(Call<RecipeInformation> cal1, Response<RecipeInformation> response) {
                RecipeInformation result = response.body();
                String data = result.toString();

                System.out.println("Frag2: " + data);

                String pattern1 = "name='";
                String pattern2 = "'";
                String regexString = Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2);
                Pattern pattern = Pattern.compile(regexString);
                Matcher matcher = pattern.matcher(data);
                ArrayList<String> boldedIngredients = new ArrayList<>();
                String ingredient = "";

                ingredientsList = new ArrayList<>();
                while (matcher.find()) {
                    ingredient = matcher.group(1); // Since (.*?) is capturing group 1
                    ingredientsList.add(ingredient);
                }//INGREDIENTS EXTRACTED HERE


                for (int a = 0; a < MainActivity.providedIngredientsList.size(); a++) {
                    for (int b = 0; b < ingredientsList.size(); b++) {
                        if (ingredientsList.get(b).contains(MainActivity.providedIngredientsList.get(a))) {

                            ingredientsList.remove(b);
                            boldedIngredients.add(MainActivity.providedIngredientsList.get(a));
                            String temp = MainActivity.providedIngredientsList.get(a);

                            usedIngs = "";
                            remainingIngs = "";

                            if (a == (MainActivity.providedIngredientsList.size() - 1)) {
                                System.out.println("JAMESSAVERY22" + boldedIngredients.toString());
                                for (String s : boldedIngredients) {
                                    remainingIngs += s + "," + "\t";
                                }
                                providedIngs.setText(remainingIngs);

                                for (String s : ingredientsList) {
                                    usedIngs += s + "," + "\t";
                                }
                                extraIngs.setText(usedIngs);
                            }


                        }
                    }
                }





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
