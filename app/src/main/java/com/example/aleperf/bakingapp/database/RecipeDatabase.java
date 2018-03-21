package com.example.aleperf.bakingapp.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.aleperf.bakingapp.model.Recipe;

@Database(entities = {Recipe.class}, version = 1)
@TypeConverters(RecipeTypeConverter.class)
public abstract class RecipeDatabase extends RoomDatabase {

    public abstract RecipeDao recipeDao();
}
