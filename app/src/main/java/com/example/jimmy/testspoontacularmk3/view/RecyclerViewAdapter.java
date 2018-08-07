package com.example.jimmy.testspoontacularmk3.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jimmy.testspoontacularmk3.R;
import com.example.jimmy.testspoontacularmk3.RecipeInformationAct;
import com.example.jimmy.testspoontacularmk3.Results;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
   
    private static final String TAG = "RecycleViewAdapter";
    
    private List<String> titles = new ArrayList<>();
    private Context mContext;
    public static String recipeData = "key0";
    private Button viewRecipe;

    public RecyclerViewAdapter(Context mContext, List<String> titles) {
        this.titles = titles;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_layout, parent, false);
        final ViewHolder holder = new ViewHolder(itemView);
        //int position = holder.getAdapterPosition();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder (ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: CALLED");

        holder.title.setText(titles.get(position));
//        Glide.with(mContext)
//                .asBitmap()
//                .load(mImages.get(position))
//                .into(holder.image);      //Reference image widget (In xml file!!)


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + titles.get(position));

                Toast.makeText(mContext, titles.get(position), Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(mContext, RecipeInformationAct.class);
                intent.putExtra(recipeData, titles.get(position));
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        RelativeLayout parentLayout;
        Button viewRecipe;


        public ViewHolder(View itemView) {
             //Holds view to be added into recycle list, hence name
            super(itemView);
            title = itemView.findViewById(R.id.recipeTitle);
            parentLayout = itemView.findViewById(R.id.parent_Layout);
        }
    }

}


















