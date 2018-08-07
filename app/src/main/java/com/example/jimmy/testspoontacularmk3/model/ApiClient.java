package com.example.jimmy.testspoontacularmk3.model;
//2 CREATE 'network'  package,  then create this class, paste over

import com.example.jimmy.testspoontacularmk3.Const;
import com.example.jimmy.testspoontacularmk3.model.api.IngredientsMapper;
import com.example.jimmy.testspoontacularmk3.model.api.Recipe;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private static Retrofit retrofit = null;  //First create retrofit object
    private static int REQUEST_TIMEOUT = 60;
    private static OkHttpClient okHttpClient;

    private ApIService apIService;
    private static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    static {
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        httpClient.readTimeout(20, TimeUnit.SECONDS);
        httpClient.connectTimeout(20, TimeUnit.SECONDS);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Content-Type", Const.APP_JSON_CONTENT_TYPE_HEADER)
                        .header("Accept", Const.JSON_ACCEPT_HEADER)
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });
    }

    public ApiClient() {
        createRetrofitService();
    }

    private void createRetrofitService() {
        apIService = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build().create(ApIService.class);
    }

    public void findRecipesByIngredients(final IngredientsMapper ingredientsMapper, final Callback<List<Recipe>> callback) {

        Call<List<Recipe>> call = apIService.findRecipesByIngredients(Const.MASHAPE_KEY,
                Const.APP_JSON_CONTENT_TYPE_HEADER, Const.JSON_ACCEPT_HEADER, ingredientsMapper.isFillIngredients(),
                ingredientsMapper.getIngredientsAsString(","),
                ingredientsMapper.isLimitLicense(),
                ingredientsMapper.getNumber(),
                ingredientsMapper.getRanking());

        call.enqueue(callback);
    }

//    public static Retrofit getClient(Context context) {  //Populates retrofit
//
//        if (okHttpClient == null) //1 HTTPclient instnace
//            initOkHttp(context); //initiates it
//
//        if (retrofit == null) { //Initialise if null, same as OHHttpClient
//            retrofit = new Retrofit.Builder() //Pass parameters as method calls, is a builder pattern
//                    //If they ask about design patterns, this is one ofit, is a builder pattern
//                    .baseUrl(Const.BASE_URL) //In this case is "https://swapi.co/api/", as it is the base url where everything is located
//                    .client(okHttpClient)
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //Comment for now as not in use, Retrofit can be used in conjuction with RXJava, used to manage threads
//                    .addConverterFactory(GsonConverterFactory.create()) //Where link between GSON and retofit happens, maps all parameters in link above to Pojos for this project to use
//                    .build();
//        }
//        return retrofit; // We dont have to constantly redo it, is a singleton class ensuring 1 instance of Retroit on app
//    }

//    private static void initOkHttp(final Context context) { //Tells app how to handle connect through http
//        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
//                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
//                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
//                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);
//
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY); //Can be body, headers, in http call
//
//        httpClient.addInterceptor(interceptor);
//
//        httpClient.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request original = chain.request();
//                Request.Builder requestBuilder = original.newBuilder()
//                        .addHeader("Accept", "application/json")
//                        .addHeader("Content-Type", "application/json");
//
//                // Adding Authorization token (API Key)
//                // Requests will be denied without API key
//                if (!TextUtils.isEmpty(PrefUtils.getApiKey(context))) {
//                    requestBuilder.addHeader("Authorization", PrefUtils.getApiKey(context));
//                }
//
//                Request request = requestBuilder.build();
//                return chain.proceed(request);
//            }
//        });
//
//        okHttpClient = httpClient.build();
//    }
}