package com.example.aleperf.bakingapp.ui.recipeDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.example.aleperf.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Manage the details about the recipe step.
 */

public class RecipeDetailStepActivity extends AppCompatActivity implements RecipeDetailStepFragment.StepDetailSelector {

    private static final String STEP_EXTRA_POSITION = "step extra position";
    private static final String RECIPE_EXTRA_TITLE = "recipe extra title";
    private static final String RECIPE_EXTRA_ID = "recipe extra id";
    private static final String FRAGMENT_TAG = "step detail fragment";
    @BindView(R.id.toolbar_detail_step)
    Toolbar toolbar;


    private String recipeTitle;
    private int recipeId;
    private int stepPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail_step);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Intent callingIntent = getIntent();
        recipeTitle = callingIntent.getStringExtra(RECIPE_EXTRA_TITLE);
        recipeId = callingIntent.getIntExtra(RECIPE_EXTRA_ID, 1);
        stepPosition = callingIntent.getIntExtra(STEP_EXTRA_POSITION, 0);
        getSupportActionBar().setTitle(recipeTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipeDetailStepFragment fragment = (RecipeDetailStepFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            fragment = RecipeDetailStepFragment.getInstance(recipeId, stepPosition);
        }
        fragmentManager.beginTransaction().replace(R.id.detail_step_fragment_container, fragment,
                FRAGMENT_TAG).commit();
    }

    @Override
    public void showStepDetail(int recipeId, int stepPosition) {
        RecipeDetailStepFragment fragment = RecipeDetailStepFragment.getInstance(recipeId, stepPosition);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.detail_step_fragment_container,
                fragment, FRAGMENT_TAG).commit();
    }
}
