package com.example.aleperf.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeActivity extends AppCompatActivity {

    private static String TAG = RecipeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_recipe);
        RecipesClient client = RecipesServiceGenerator.createRecipeService();
        Call<List<Recipe>> call = client.getAllRecipes();
        Log.d(TAG, call.request().url().toString());
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.d(TAG, "success!");
                List<Recipe> recipes = response.body();
                Recipe nutellaPie = recipes.get(0);
                String name = nutellaPie.getName();
                int servings = nutellaPie.getServings();
                List<Recipe.Ingredient> nutellaPieIngr = nutellaPie.getIngredients();
                Log.d(TAG, "name " + name + " servings " + servings + " n° rec = " + recipes.size());
                Recipe.Ingredient ingredient1 = nutellaPieIngr.get(0);
                String nameIngr = ingredient1.getIngredient();
                String measure = ingredient1.getMeasure();
                float quantity = ingredient1.getQuantity();
                Log.d(TAG, "primo ingrediente " + nameIngr + " " + measure + " " + quantity);
                Recipe recipe2 = recipes.get(1);



            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d(TAG, "failure!" + t.getMessage());


            }
        });
    }

}