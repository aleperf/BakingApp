package com.example.aleperf.bakingapp;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.aleperf.bakingapp.database.RecipeRepositoryImpl;

import javax.inject.Inject;

public class BakingViewModelProviderFactory implements ViewModelProvider.Factory {

    private final RecipeRepositoryImpl repository;

    @Inject
    public BakingViewModelProviderFactory(RecipeRepositoryImpl repository){
        this.repository = repository;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(RecipesViewModel.class)){
            return (T) new RecipesViewModel(repository);
        } else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }

    }
}