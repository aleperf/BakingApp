package com.example.aleperf.bakingapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.example.aleperf.bakingapp.model.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM Recipe.RECIPES_TABLE")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT * FROM Recipe.RECIPES_TABLE WHERE id = :id LIMIT 1")
    Recipe getRecipeWithId(int id);
}
