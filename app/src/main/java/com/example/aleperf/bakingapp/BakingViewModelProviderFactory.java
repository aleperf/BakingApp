package com.example.aleperf.bakingapp;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.aleperf.bakingapp.database.RecipeRepository;
import com.example.aleperf.bakingapp.ui.recipeDetail.RecipeDetailViewModel;
import com.example.aleperf.bakingapp.ui.intro.RecipesViewModel;

import javax.inject.Inject;

public class BakingViewModelProviderFactory implements ViewModelProvider.Factory {

    private final RecipeRepository repository;



    @Inject
    public BakingViewModelProviderFactory(RecipeRepository repository){
        this.repository = repository;
        }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(RecipesViewModel.class)){
            return (T) new RecipesViewModel(repository);
        } else if(modelClass.isAssignableFrom(RecipeDetailViewModel.class)){
            return (T) new RecipeDetailViewModel(repository);
        }

        else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }

    }
}
