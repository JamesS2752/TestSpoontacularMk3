package com.example.jimmy.testspoontacularmk3.utils;

import java.util.Objects;

public class RecipeInfo {
    String id;
    String title;
    String serves;
    boolean vegetarian;
    boolean vegan;
    boolean glutenFree;
    boolean dairyFree;
    boolean ketogenic;
    String description;
    String instructions;
    String image;
    String servings;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeInfo that = (RecipeInfo) o;
        return isVegetarian() == that.isVegetarian() &&
                isVegan() == that.isVegan() &&
                isGlutenFree() == that.isGlutenFree() &&
                isDairyFree() == that.isDairyFree() &&
                isKetogenic() == that.isKetogenic() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getServes(), that.getServes()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getInstructions(), that.getInstructions()) &&
                Objects.equals(getImage(), that.getImage()) &&
                Objects.equals(getServings(), that.getServings());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getTitle(), getServes(), isVegetarian(), isVegan(), isGlutenFree(), isDairyFree(), isKetogenic(), getDescription(), getInstructions(), getImage(), getServings());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getServes() {
        return serves;
    }

    public void setServes(String serves) {
        this.serves = serves;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(boolean glutenFree) {
        this.glutenFree = glutenFree;
    }

    public boolean isDairyFree() {
        return dairyFree;
    }

    public void setDairyFree(boolean dairyFree) {
        this.dairyFree = dairyFree;
    }

    public boolean isKetogenic() {
        return ketogenic;
    }

    public void setKetogenic(boolean ketogenic) {
        this.ketogenic = ketogenic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

}
