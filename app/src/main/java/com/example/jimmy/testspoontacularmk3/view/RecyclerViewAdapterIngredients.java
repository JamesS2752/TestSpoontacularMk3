package com.example.jimmy.testspoontacularmk3.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jimmy.testspoontacularmk3.R;
import com.example.jimmy.testspoontacularmk3.RecipeInformationAct;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapterIngredients extends RecyclerView.Adapter<RecyclerViewAdapterIngredients.ViewHolder> {

    private static final String TAG = "RecycleViewAdapter";

    private List<String> ingredients = new ArrayList<>();
    private Context mContext;
    public static String ingredientData = "key0";



    public RecyclerViewAdapterIngredients(Context mContext, List<String> ingredients) {
        this.ingredients = ingredients;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_layout, parent, false);
        final ViewHolder holder = new ViewHolder(itemView);
        //int position = holder.getAdapterPosition();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder (ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: CALLED");

        holder.ingredientTV.setText(ingredients.get(position));



    }


    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ingredientTV;
        RelativeLayout parentLayout;


        public ViewHolder(View itemView) {
             //Holds view to be added into recycle list, hence name
            super(itemView);
            ingredientTV = itemView.findViewById(R.id.recipeIngredient);
            parentLayout = itemView.findViewById(R.id.parent_Layout2);
        }
    }

}


















