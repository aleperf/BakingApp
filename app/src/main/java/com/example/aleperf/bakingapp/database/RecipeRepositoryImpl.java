package com.example.aleperf.bakingapp.database;


import android.arch.lifecycle.LiveData;

import com.example.aleperf.bakingapp.model.Recipe;

import java.util.List;

import javax.inject.Inject;

public class RecipeRepositoryImpl implements RecipeRepository{

    RecipeDao recipeDao;
    @Inject
    public RecipeRepositoryImpl(RecipeDao recipeDao){
        this.recipeDao = recipeDao;
    }

    @Override
    public LiveData<List<Recipe>> getAllRecipes() {
        return recipeDao.getAllRecipes();
    }

    @Override
    public Recipe getRecipeWithId(int id) {
        return recipeDao.getRecipeWithId(id);
    }

    @Override
    public void insertAllRecipes(List<Recipe> recipes) {
             recipeDao.insertAllRecipes(recipes);
    }

    @Override
    public void insertRecipe(Recipe recipe) {
         recipeDao.insertRecipe(recipe);
    }
}
