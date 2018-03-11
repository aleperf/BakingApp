package com.example.aleperf.bakingapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.aleperf.bakingapp.model.Recipe;
import com.example.aleperf.bakingapp.networking.RecipesClient;
import com.example.aleperf.bakingapp.networking.RecipesServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecipesViewModel extends ViewModel {

    private static final String TAG = RecipesViewModel.class.getSimpleName();

    private MutableLiveData<List<Recipe>> recipes;

    public LiveData<List<Recipe>> getRecipes(){
        if(recipes == null){
            recipes = new MutableLiveData<List<Recipe>>();
            loadRecipes();
        }
        return recipes;
    }

    private void loadRecipes(){
        RecipesClient client = RecipesServiceGenerator.createRecipeService();
        Call<List<Recipe>> call = client.getAllRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> responseRecipes = response.body();
                recipes.setValue(responseRecipes);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d(TAG, "error in loading recipes " + t.getMessage());

            }
        });
    }
}
