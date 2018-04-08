package com.example.aleperf.bakingapp.ui.intro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.model.Recipe;
import com.example.aleperf.bakingapp.ui.recipeDetail.RecipeDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Launcher for the app, coordinates fragments.
 */
public class RecipesMainActivity extends AppCompatActivity implements RecipesAdapter.RecipeCallback {

    private static String TAG = RecipesMainActivity.class.getSimpleName();
    private static final String RECIPE_EXTRA_ID = "recipe extra id";
    private static final String RECIPE_EXTRA_TITLE = "recipe extra title";

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recipes);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }


    @Override
    public void onClickRecipe(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RECIPE_EXTRA_ID, recipe.getId());
        intent.putExtra(RECIPE_EXTRA_TITLE, recipe.getName());
        startActivity(intent);
    }
}