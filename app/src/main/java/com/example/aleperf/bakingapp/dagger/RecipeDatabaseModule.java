package com.example.aleperf.bakingapp.dagger;


import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.aleperf.bakingapp.database.RecipeDatabase;

import dagger.Module;

@Module
public class RecipeDatabaseModule {

    private RecipeDatabase database;

    public RecipeDatabaseModule(Application application){
        this.database = Room.databaseBuilder(application, RecipeDatabase.class, "Recipe.db").build();
    }


}
