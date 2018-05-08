package com.example.aleperf.bakingapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.example.aleperf.bakingapp.model.Recipe;

import java.util.List;


import io.reactivex.Flowable;
import io.reactivex.Maybe;


import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM RECIPES_TABLE")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT COUNT (ID) FROM RECIPES_TABLE")
    Maybe<Integer> getNumberOfRecipes();

    @Query("SELECT * FROM RECIPES_TABLE WHERE id = :id LIMIT 1")
    LiveData<Recipe> getRecipeWithId(int id);

    @Query("SELECT * FROM RECIPES_TABLE WHERE id = :id LIMIT 1")
    Flowable<Recipe> provideRecipeWithId(int id);


    @Insert(onConflict = REPLACE)
    void insertAllRecipes(List<Recipe> recipes);

    @Insert(onConflict = REPLACE)
    void insertRecipe(Recipe recipe);



}
