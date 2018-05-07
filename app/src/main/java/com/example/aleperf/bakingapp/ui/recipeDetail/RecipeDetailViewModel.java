package com.example.aleperf.bakingapp.ui.recipeDetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.aleperf.bakingapp.database.RecipeRepository;
import com.example.aleperf.bakingapp.model.Recipe;

import javax.inject.Inject;

public class RecipeDetailViewModel extends ViewModel {

    private final String TAG = RecipeDetailViewModel.class.getSimpleName();
    private RecipeRepository repository;
    private LiveData<Recipe> recipe;
    private int recipeId = -1;



    @Inject
    public RecipeDetailViewModel(RecipeRepository repository){
        this.repository = repository;
        }

    public LiveData<Recipe> getRecipe(int recipeId){
       if(recipe != null && this.recipeId == recipeId){
           return recipe;
       }
        this.recipeId = recipeId;
        recipe = repository.getRecipeWithId(recipeId);
        return recipe;
    }


}
