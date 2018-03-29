package com.example.aleperf.bakingapp.dagger;

import com.example.aleperf.bakingapp.networking.RecipesService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RecipesServiceModule {

    private final static String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";


    @Provides
    @BakingApplicationScope
    public RecipesService provideRecipesService(Retrofit retrofit) {
        return retrofit.create(RecipesService.class);
    }

    @Provides
    @BakingApplicationScope
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    @Provides
    @BakingApplicationScope
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

}
