package com.example.aleperf.bakingapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.aleperf.bakingapp.database.RecipeRepositoryImpl;
import com.example.aleperf.bakingapp.model.Recipe;
import com.example.aleperf.bakingapp.networking.RecipesClient;
import com.example.aleperf.bakingapp.networking.RecipesServiceGenerator;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecipesViewModel extends ViewModel {

    private static final String TAG = RecipesViewModel.class.getSimpleName();

    private RecipeRepositoryImpl repository;

    @Inject
    public RecipesViewModel(RecipeRepositoryImpl repository){
        this.repository = repository;
    }

    public Maybe<List<Recipe>> getRecipes(){

        return repository.getAllRecipes();
    }
}
