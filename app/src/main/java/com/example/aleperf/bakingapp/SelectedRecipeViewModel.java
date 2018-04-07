package com.example.aleperf.bakingapp;

import android.arch.lifecycle.ViewModel;

import com.example.aleperf.bakingapp.database.RecipeRepository;
import com.example.aleperf.bakingapp.model.Recipe;

import javax.inject.Inject;

import io.reactivex.Single;

public class SelectedRecipeViewModel extends ViewModel {

    private final static String TAG = SelectedRecipeViewModel.class.getSimpleName();

    private RecipeRepository repository;

    @Inject
    public SelectedRecipeViewModel(RecipeRepository repository){
        this.repository = repository;
    }

    public Single<Recipe> getSelectedRecipe(int recipeId){
       return  repository.getRecipeWithId(recipeId);
    }




}
