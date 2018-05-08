package com.example.aleperf.bakingapp.database;


import android.arch.lifecycle.LiveData;
import com.example.aleperf.bakingapp.model.Recipe;
import java.util.List;


import io.reactivex.Flowable;


public interface RecipeRepository {

    LiveData<List<Recipe>> getAllRecipes();
    Flowable<Recipe> provideRecipeWithId(int id);
    LiveData<Recipe> getRecipeWithId(int id);
    void insertAllRecipes(List<Recipe> recipes);
    void insertRecipe(Recipe recipe);
}
