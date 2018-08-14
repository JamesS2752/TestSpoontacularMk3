package com.example.jimmy.testspoontacularmk3.model.api;

import com.google.gson.annotations.SerializedName;
import com.jakewharton.retrofit2.adapter.rxjava2.Result;

import java.util.List;

public class RecipeSummaryMapper {

    @SerializedName("id")
    private int id;

    public RecipeSummaryMapper() {}

    public RecipeSummaryMapper(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private List<RecipeSummary> result = null;

    public List<RecipeSummary> getResult() {
        return result;
    }

    public void setResult(List<RecipeSummary> results) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "RecipeSummaryMapper{" +
                "id=" + id +
                '}';
    }

}
