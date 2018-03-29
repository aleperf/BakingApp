package com.example.aleperf.bakingapp;


import android.app.Activity;
import android.app.Application;

import com.example.aleperf.bakingapp.dagger.ApplicationModule;
import com.example.aleperf.bakingapp.dagger.BakingApplicationComponent;
import com.example.aleperf.bakingapp.dagger.DaggerBakingApplicationComponent;
import com.example.aleperf.bakingapp.dagger.RecipeDatabaseModule;
import com.example.aleperf.bakingapp.dagger.RecipesServiceModule;
import com.example.aleperf.bakingapp.database.RecipeRepositoryImpl;
import com.example.aleperf.bakingapp.networking.RecipesService;

public class BakingApplication extends Application {

    private BakingApplicationComponent component;



    public static BakingApplication get(Activity activity){
        return (BakingApplication) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerBakingApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .recipeDatabaseModule(new RecipeDatabaseModule(this))
                .recipesServiceModule(new RecipesServiceModule())
                .build();


    }

    public BakingApplicationComponent getBakingApplicationComponent(){
        return component;
    }




}
