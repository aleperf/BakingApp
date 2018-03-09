package com.example.aleperf.bakingapp;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RecipesServiceGenerator {

    private static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net";
    private static OkHttpClient httpClient =
            new OkHttpClient.Builder().build();


    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .client(httpClient)
                    .baseUrl(RECIPE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    public static RecipesClient createRecipeService() {
        return retrofit.create(RecipesClient.class);
    }
}
