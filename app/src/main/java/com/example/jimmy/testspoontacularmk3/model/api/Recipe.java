package com.example.jimmy.testspoontacularmk3.model.api;

import com.google.gson.annotations.SerializedName;

public class Recipe {

    @SerializedName("id")
    private Integer id;

    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String image;

    @SerializedName("usedIngredientCount")
    private Integer usedIngredientCount;

    @SerializedName("missedIngredientCount")
    private Integer missedIngredientCount;

    @SerializedName("likes")
    private Integer likes;

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title=" + title +
                ", image='" + image + '\'' +
                ", usedIngredientCount=" + usedIngredientCount +
                ", missedIngredientCount=" + missedIngredientCount +
                ", likes=" + likes +
                '}';
    }
}
