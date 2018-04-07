package com.example.aleperf.bakingapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.aleperf.bakingapp.R;

/**
 * Launcher for the app, coordinates fragments.
 */
public class RecipesMainActivity extends AppCompatActivity implements RecipesAdapter.RecipeCallback {

    private static String TAG = RecipesMainActivity.class.getSimpleName();
    private static final String RECIPE_EXTRA_ID = "recipe extra id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recipes);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    public void onClickRecipe(int id) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RECIPE_EXTRA_ID, id);
        startActivity(intent);
    }
}