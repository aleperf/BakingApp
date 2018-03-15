package com.example.aleperf.bakingapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.aleperf.bakingapp.model.Recipe;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM Recipe.RECIPES_TABLE")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT * FROM Recipe.RECIPES_TABLE WHERE id = :id LIMIT 1")
    Recipe getRecipeWithId(int id);

    @Insert(onConflict = REPLACE)
    void insertRecipes(List<Recipe> recipes);

    @Insert(onConflict = REPLACE)
    void insertRecipe(Recipe recipe);
}
