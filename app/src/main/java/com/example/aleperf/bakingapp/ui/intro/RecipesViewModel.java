package com.example.aleperf.bakingapp.ui.intro;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.aleperf.bakingapp.database.RecipeRepository;
import com.example.aleperf.bakingapp.model.Recipe;

import java.util.List;

import javax.inject.Inject;


public class RecipesViewModel extends ViewModel {

    private static final String TAG = RecipesViewModel.class.getSimpleName();

    private RecipeRepository repository;

    @Inject
    public RecipesViewModel(RecipeRepository repository){

        this.repository = repository;

    }

    public LiveData<List<Recipe>> getRecipes(){

        return repository.getAllRecipes();
    }
}
