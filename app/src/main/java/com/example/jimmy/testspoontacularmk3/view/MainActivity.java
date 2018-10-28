package com.example.jimmy.testspoontacularmk3.view;
//IN THIS VERSION: [v0.6] Clicking on a Recycleview will take user to tabbed page succesfully + sends data over
// in which each fragment can use with ease.
// Make look prettier//appropiate..

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jimmy.testspoontacularmk3.R;
import com.example.jimmy.testspoontacularmk3.presenter.MainActivityContract;
import com.example.jimmy.testspoontacularmk3.presenter.MainActivityPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {
    Dialog myDialog;

    private MainActivityContract.Presenter presenter;
    MainActivityPresenter mainActivityPresenter;

    private EditText ingredientsEditText;
    private Button sendButton;

    public static String resultsStore = "key0";
    public static List<String> providedIngredientsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityPresenter = new MainActivityPresenter(getApplicationContext(), this);
        myDialog = new Dialog(this);
    }

    @Override
    public void ingredientEngineSubmit() {
        ingredientsEditText = (EditText) findViewById(R.id.ingredients_edit_text);
        sendButton = (Button) findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredients = ingredientsEditText.getText().toString();
                providedIngredientsList = Arrays.asList(ingredients.split("\\s*,\\s*"));
                System.out.println("JAMES2: " + providedIngredientsList.toString());

                Intent intent = new Intent(getApplicationContext(), Results.class);
                intent.putExtra(resultsStore, ingredients);
                startActivity(intent);
            }
        });
    }

    public void instructionsDialog() {
        TextView txtClose;
        myDialog.setContentView(R.layout.instructions_popup);
        txtClose = (TextView) myDialog.findViewById(R.id.txtclose);

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    @Override
    protected void onStart() {
        instructionsDialog();
        super.onStart(); //Could go presenter.onStart(), but this is sorta fine (To make it purely MVP)
    }

    @Override
    public void setPresenter(MainActivityContract.Presenter presenter) {

    }
}


