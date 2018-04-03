package com.example.aleperf.bakingapp;

import android.arch.lifecycle.ViewModel;

import com.example.aleperf.bakingapp.database.RecipeRepository;

import javax.inject.Inject;

public class SelectedRecipeViewModel extends ViewModel {

    private final static String TAG = SelectedRecipeViewModel.class.getSimpleName();

    private RecipeRepository repository;

    @Inject
    public SelectedRecipeViewModel(RecipeRepository repository){
        this.repository = repository;
    }




}
