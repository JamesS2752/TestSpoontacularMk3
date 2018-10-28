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

import com.bumptech.glide.Glide;
import com.example.jimmy.testspoontacularmk3.Const;
import com.example.jimmy.testspoontacularmk3.R;
import com.example.jimmy.testspoontacularmk3.RecipeInformationAct;
import com.example.jimmy.testspoontacularmk3.model.ApIService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecycleViewAdapter";

    private List<String> titles;
    private List<String> images;
    private List<String> recipeTitle;
    private List<String> recipeLikes;
    private List<String> recipeIDs;
    private Context mContext;
    public static String recipeData = "key0";

    private static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    private static final OkHttpClient.Builder client = new OkHttpClient.Builder();

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
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });
    }

    public RecyclerViewAdapter(Context mContext, List<String> titles, List<String> images, List<String> recipeTitle, List<String> recipeLikes, List<String> recipeIDs) {
        this.titles = titles;
        this.mContext = mContext;
        this.images = images;
        this.recipeTitle = recipeTitle;
        this.recipeLikes = recipeLikes;
        this.recipeIDs = recipeIDs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_layout, parent, false);
        final ViewHolder holder = new ViewHolder(itemView);

        ApIService spoonacularService = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build().create(ApIService.class);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: CALLED");


        holder.title.setText(recipeTitle.get(position));
        Glide.with(mContext)
                .asBitmap()
                .load(images.get(position))
                .into(holder.image);


        holder.likes.setText(recipeLikes.get(position));


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + titles.get(position));
                String idString = recipeIDs.get(position);

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        RelativeLayout parentLayout;
        ImageView image;
        TextView likes;


        public ViewHolder(View itemView) {
            //Holds view to be added into recycle list, hence name
            super(itemView);


            title = itemView.findViewById(R.id.recipeTitle);
            parentLayout = itemView.findViewById(R.id.parent_Layout);
            image = itemView.findViewById(R.id.recipeListImage);
            likes = itemView.findViewById(R.id.recipeLikes);
        }
    }
}


















