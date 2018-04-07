package com.example.aleperf.bakingapp.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.example.aleperf.bakingapp.database.RecipeRepository;
import com.example.aleperf.bakingapp.model.Recipe;

import javax.inject.Inject;

public class RecipeDetailViewModel extends ViewModel {

    private final String TAG = RecipeDetailViewModel.class.getSimpleName();

    private LiveData<Recipe> recipe;

    @Inject
    public RecipeDetailViewModel(RecipeRepository repository, int recipeId){
        recipe = repository.getRecipeWithId(recipeId);

    }

    public LiveData<Recipe> getRecipe(){
        return recipe;
    }

}
