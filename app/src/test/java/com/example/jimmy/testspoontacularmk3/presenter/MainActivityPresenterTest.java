package com.example.jimmy.testspoontacularmk3.presenter;

import com.example.jimmy.testspoontacularmk3.view.MainActivity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityPresenterTest {

    String ingredient;

    private MainActivity target = null;

    @Before
    public void setup() {
        target = new MainActivity();
    }

    @Test
    public void test_not_null() {
        target.ingredientEngineSubmit();
    }

    @Test
    public void submitIngredients() {

    }



    @Test
    public void start() {
    }




}