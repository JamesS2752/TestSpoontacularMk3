package com.example.jimmy.testspoontacularmk3.model;
//4? Create this (Interface, go to create new class but in a dropdown in there, change class to interface!

import com.example.jimmy.testspoontacularmk3.model.api.Recipe;
import com.example.jimmy.testspoontacularmk3.model.api.RecipeInformation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApIService { //Declare what kind of calls we are able to make. Every call has the same structure as here
    @GET("recipes/findByIngredients")
    Call<List<Recipe>> findRecipesByIngredients(
            @Header("X-Mashape-Key") String mashapeKey,
            @Header("Content-Type") String contentType,
            @Header("Accept") String accept,
            @Query("fillIngredients") boolean fillIngredients,
            @Query("ingredients") String ingredients,
            @Query("limitLicense") boolean limitLicense,
            @Query("number") Integer number,
            @Query("ranking") Integer ranking);

    @GET("recipes/{id}/information")
    Call<RecipeInformation> getRecipeInformation(
            @Header("X-Mashape-Key") String mashapeKey,
            @Header("Content-Type") String contentType,
            @Header("Accept") String accept,
            @Path("id") int id,
            @Query("includeNutrition") boolean includeNutrition);
    }
