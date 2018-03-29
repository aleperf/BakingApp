package com.example.aleperf.bakingapp;

import android.arch.lifecycle.ViewModel;
import android.support.v7.widget.RecyclerView;

import com.example.aleperf.bakingapp.database.RecipeRepositoryImpl;
import com.example.aleperf.bakingapp.model.Recipe;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;


public class RecipesViewModel extends ViewModel {

    private static final String TAG = RecipesViewModel.class.getSimpleName();

    private Maybe<List<Recipe>> recipes;


    @Inject
    public RecipesViewModel(RecipeRepositoryImpl repository){

        recipes = repository.getAllRecipes();
    }

    public Maybe<List<Recipe>> getRecipes(){

        return recipes;
    }
}
