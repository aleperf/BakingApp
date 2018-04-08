package com.example.aleperf.bakingapp.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.example.aleperf.bakingapp.database.RecipeRepository;
import com.example.aleperf.bakingapp.model.Recipe;

import javax.inject.Inject;

public class RecipeDetailViewModel extends ViewModel {

    private final String TAG = RecipeDetailViewModel.class.getSimpleName();
    private RecipeRepository repository;



    @Inject
    public RecipeDetailViewModel(RecipeRepository repository){
        this.repository = repository;
    }

    public LiveData<Recipe> getRecipe(int recipeId){
      return repository.getRecipeWithId(recipeId);
    }


}
