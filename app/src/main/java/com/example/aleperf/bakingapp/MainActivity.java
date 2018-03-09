package com.example.aleperf.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecipesClient client = RecipesServiceGenerator.createRecipeService();
        Call<List<Recipe>> call = client.getAllRecipes();
        Log.d(TAG, call.request().url().toString());
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.d(TAG, "success!");
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d(TAG, "failure!" + t.getMessage());


            }
        });
    }

}