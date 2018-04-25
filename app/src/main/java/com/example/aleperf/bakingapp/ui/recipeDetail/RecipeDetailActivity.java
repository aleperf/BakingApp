package com.example.aleperf.bakingapp.ui.recipeDetail;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.example.aleperf.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;



public class RecipeDetailActivity extends AppCompatActivity implements StepSelector {

    private static final String STEP_EXTRA_POSITION = "step extra position";
    private static final String RECIPE_EXTRA_TITLE = "recipe extra title";
    private static final String RECIPE_EXTRA_ID = "recipe extra id";
    private static final String STEP_POSITION = "step position";
    private static final String RECIPE_ID = "recipe id";
    private static final String RECIPE_TITLE = "recipe title";


    @BindView(R.id.detail_activity_toolbar)
    Toolbar toolbar;
    private int recipeId;
    private String recipeTitle;
    private boolean isDualPane = false;
    private int stepPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            Intent callingIntent = getIntent();
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
    public void onStepSelected(int stepPosition) {
        this.stepPosition = stepPosition;
        if (!isDualPane) {
            Intent intent = new Intent(this, RecipeDetailStepActivity.class);
            intent.putExtra(STEP_EXTRA_POSITION, stepPosition);
            intent.putExtra(RECIPE_EXTRA_TITLE, recipeTitle);
            intent.putExtra(RECIPE_EXTRA_ID, recipeId);
            startActivity(intent);
        } else {
            replaceFragment(recipeId, stepPosition);

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STEP_POSITION, stepPosition);
        outState.putInt(RECIPE_ID, recipeId);
        outState.putString(RECIPE_TITLE, recipeTitle);
    }

    private void replaceFragment(int recipeId, int stepPosition) {
        RecipeDetailStepFragment fragment = RecipeDetailStepFragment.getInstance(recipeId, stepPosition);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.detail_steps_container,
                fragment).commit();
    }


}
