package com.example.aleperf.bakingapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.aleperf.bakingapp.model.Recipe;

import java.util.List;


import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM RECIPES_TABLE")
    Maybe<List<Recipe>> getAllRecipes();

    @Query("SELECT COUNT ID FROM RECIPES_TABLE")
    Single<Integer> getNumberOfRecipes();

    @Query("SELECT * FROM RECIPES_TABLE WHERE id = :id LIMIT 1")
    Single<Recipe> getRecipeWithId(int id);

    @Insert(onConflict = REPLACE)
    void insertAllRecipes(List<Recipe> recipes);

    @Insert(onConflict = REPLACE)
    void insertRecipe(Recipe recipe);

    @Delete
    void deleteRecipe(Recipe recipe);

    @Delete
    void deleteAllRecipes(List<Recipe> recipes);

    @Update(onConflict = REPLACE)
    void updateRecipe(Recipe recipe);


}
