package com.example.aleperf.bakingapp.ui.recipeDetail;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.aleperf.bakingapp.ui.intro.IdlingResourcesManager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.ui.intro.RecipesMainActivity;
import com.example.aleperf.bakingapp.utils.RecipeUtilities;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Manage the detailed info for a single recipe (phone and tablet)
 */

public class RecipeDetailActivity extends AppCompatActivity implements StepSelector, IngredientsDisplay, IdlingResourcesManager {

    private static final String STEP_EXTRA_POSITION = "step extra position";
    private static final String RECIPE_EXTRA_TITLE = "recipe extra title";
    private static final String RECIPE_EXTRA_ID = "recipe extra id";
    private static final String STEP_POSITION = "step position";
    private static final String RECIPE_ID = "recipe id";
    private static final String RECIPE_TITLE = "recipe title";
    public final static String SHOW_INGREDIENTS_ACTION = "com.example.aleperf.bakingapp.SHOW_INGREDIENT_ACTION";
    private static final String TAG = RecipeDetailActivity.class.getSimpleName();

    @BindView(R.id.detail_activity_toolbar)
    Toolbar toolbar;
    private int recipeId;
    private String recipeTitle;
    private boolean isDualPane = false;
    private int stepPosition = 0;

    private CountingIdlingResource countingIdlingResource = new CountingIdlingResource(RecipeDetailActivity.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent callingIntent = getIntent();
        if (savedInstanceState == null || callingIntent.getAction() != null && callingIntent.getAction().equals(SHOW_INGREDIENTS_ACTION)) {
            recipeTitle = callingIntent.getStringExtra(RECIPE_EXTRA_TITLE);
            recipeId = callingIntent.getIntExtra(RECIPE_EXTRA_ID, 1);
        } else {
            recipeTitle = savedInstanceState.getString(RECIPE_TITLE);
            recipeId = savedInstanceState.getInt(RECIPE_ID);
            stepPosition = savedInstanceState.getInt(STEP_POSITION, 0);

        }
        getSupportActionBar().setTitle(recipeTitle);
        isDualPane = getResources().getBoolean(R.bool.is_dual_pane);
        if (isDualPane) {
            replaceFragment(recipeId, stepPosition);
        }

    }

    @Override
    public void onStepSelected(int position) {
        this.stepPosition = position;
        if (!isDualPane) {
            Intent intent = new Intent(this, RecipeDetailStepActivity.class);
            intent.putExtra(STEP_EXTRA_POSITION, position);
            intent.putExtra(RECIPE_EXTRA_TITLE, recipeTitle);
            intent.putExtra(RECIPE_EXTRA_ID, recipeId);
            startActivity(intent);
        } else {
            replaceFragment(recipeId, position);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STEP_POSITION, stepPosition);
        outState.putInt(RECIPE_ID, recipeId);
        outState.putString(RECIPE_TITLE, recipeTitle);
    }

    private void replaceFragment(int recipeId, int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        String tag = RecipeUtilities.createTagForFragment(position, recipeTitle);
        RecipeDetailStepFragment fragment = (RecipeDetailStepFragment) fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = RecipeDetailStepFragment.newInstance(recipeId, position);
        }
        fragmentManager.beginTransaction().replace(R.id.detail_steps_container,
                fragment, tag).commit();
    }


    @Override
    public void displayIngredients() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        IngredientsDialogFragment ingredientsDialogFragment = IngredientsDialogFragment.newInstance(recipeId, recipeTitle);
        ingredientsDialogFragment.show(fragmentManager, IngredientsDialogFragment.class.getSimpleName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            navigateToParent();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        navigateToParent();
    }

    private void navigateToParent() {
        Intent navigateUpToParentIntent = new Intent(this, RecipesMainActivity.class);
        navigateUpToParentIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (getParent() != null) {
            NavUtils.navigateUpTo(this, navigateUpToParentIntent);
        } else {
            startActivity(navigateUpToParentIntent);
        }
        finish();
    }

    @Override
    public void incrementIdlingResource() {
        countingIdlingResource.increment();
    }

    @Override
    public void decrementIdlingResource() {
        countingIdlingResource.decrement();
    }

    @Override
    public CountingIdlingResource getCountIdlingResource() {
        return countingIdlingResource;
    }


}
