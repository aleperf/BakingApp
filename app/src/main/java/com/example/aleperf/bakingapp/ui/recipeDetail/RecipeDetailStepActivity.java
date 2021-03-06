package com.example.aleperf.bakingapp.ui.recipeDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.utils.RecipeUtilities;
import com.example.aleperf.bakingapp.ui.intro.IdlingResourcesManager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Manage the details about a step of a recipe in a phone screen.
 */

public class RecipeDetailStepActivity extends AppCompatActivity implements StepSelector, IdlingResourcesManager {

    private static final String STEP_EXTRA_POSITION = "step extra position";
    private static final String RECIPE_EXTRA_TITLE = "recipe extra title";
    private static final String RECIPE_EXTRA_ID = "recipe extra id";
    private static final String STEP_POSITION = "recipe step position";

    @BindView(R.id.toolbar_detail_step)
    Toolbar toolbar;


    private String recipeTitle;
    private int recipeId;
    private int stepPosition;

    private CountingIdlingResource countingIdlingResource = new CountingIdlingResource(RecipeDetailActivity.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail_step);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Intent callingIntent = getIntent();
        recipeTitle = callingIntent.getStringExtra(RECIPE_EXTRA_TITLE);
        recipeId = callingIntent.getIntExtra(RECIPE_EXTRA_ID, 1);
        if(savedInstanceState == null){
            stepPosition = callingIntent.getIntExtra(STEP_EXTRA_POSITION, 0);} else {
            stepPosition = savedInstanceState.getInt(STEP_POSITION);
        }
        getSupportActionBar().setTitle(recipeTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        replaceFragment(recipeId, stepPosition);
    }


    @Override
    public void onStepSelected(int position) {
        replaceFragment(recipeId, position);
    }

    private void replaceFragment(int recipeId, int position) {
        String tag = RecipeUtilities.createTagForFragment(position, recipeTitle);
        this.stepPosition = position;
        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipeDetailStepFragment fragment = (RecipeDetailStepFragment) fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = RecipeDetailStepFragment.newInstance(recipeId, position);
        }
        fragmentManager.beginTransaction().replace(R.id.detail_step_fragment_container, fragment,
                tag).commit();
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STEP_POSITION, stepPosition);
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
