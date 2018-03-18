package com.example.aleperf.bakingapp.database;


import android.arch.lifecycle.LiveData;
import com.example.aleperf.bakingapp.model.Recipe;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

public interface RecipeRepository {

    LiveData<List<Recipe>> getAllRecipes();
    Recipe getRecipeWithId(int id);
    void insertAllRecipes(List<Recipe> recipes);
    void insertRecipe(Recipe recipe);
}
