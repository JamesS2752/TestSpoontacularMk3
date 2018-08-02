package com.example.jimmy.testspoontacularmk3.model.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class IngredientsMapper {

    private List<Recipe> results = null;

    @SerializedName("fillIngredients")
    private boolean fillIngredients;

    @SerializedName("ingredients")
    private List<String> ingredients = new ArrayList<>();

    @SerializedName("limitLicense")
    private boolean limitLicense = false;

    @SerializedName("number")
    private int number = 5;

    @SerializedName("ranking")
    private int ranking = 1;

    @SerializedName("title")
    private String title;

    public IngredientsMapper() {}

    public IngredientsMapper(boolean fillIngredients, List<String> ingredients,
                             boolean limitLicense, int number, int ranking, String title) {

        this.fillIngredients = fillIngredients;
        this.ingredients = ingredients;
        this.limitLicense = limitLicense;
        this.number = number;
        this.ranking = ranking;
        this.title = title;
    }

    public boolean isFillIngredients() {
        return fillIngredients;
    }

    public void setFillIngredients(boolean fillIngredients) {
        this.fillIngredients = fillIngredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public boolean isLimitLicense() {
        return limitLicense;
    }

    public void setLimitLicense(boolean limitLicense) {
        this.limitLicense = limitLicense;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getIngredientsAsString(final String separator) {
        final String currentSeparator = separator == null ||
                separator.trim().isEmpty() ? "," : separator;

        StringBuilder ingredientsBuilder = new StringBuilder();
        try {
            synchronized (ingredients) {
                int ingredientsListSize = getIngredients().size();
                int i;
                for(i = 0; i < ingredientsListSize - 1; i++) {
                    ingredientsBuilder.append(ingredients.get(i));
                    ingredientsBuilder.append(currentSeparator);
                }
                ingredientsBuilder.append(ingredients.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ingredientsBuilder.toString();
    }

    @Override
    public String toString() {
        return "IngredientsMapper{" +
                "fillIngredients=" + fillIngredients +
                ", ingredients=" + ingredients +
                ", limitLicense=" + limitLicense +
                ", number=" + number +
                ", ranking=" + ranking +
                '}';
    }

    public List<Recipe> getResults() {
        return results;
    }

    public void setResults(List<Recipe> results) {
        this.results = results;
    }

}
