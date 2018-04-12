package com.example.aleperf.bakingapp.ui.recipeDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.aleperf.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.aleperf.bakingapp.ui.recipeDetail.RecipeSummaryFragment.StepSelector;

public class RecipeDetailActivity extends AppCompatActivity implements StepSelector {

    private static final String STEP_EXTRA_POSITION = "step extra position";
    private static final String RECIPE_EXTRA_TITLE = "recipe extra title";
    private static final String RECIPE_EXTRA_ID = "recipe extra id";

    @BindView(R.id.detail_activity_toolbar)
    Toolbar toolbar;
    private int recipeId;
    private String recipeTitle;
    private boolean isDualPane = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent callingIntent = getIntent();
        recipeTitle = callingIntent.getStringExtra(RECIPE_EXTRA_TITLE);
        recipeId = callingIntent.getIntExtra(RECIPE_EXTRA_ID, 1);
        getSupportActionBar().setTitle(recipeTitle);

        isDualPane = getResources().getBoolean(R.bool.is_dual_pane);


        }

    @Override
    public void onStepSelected(int stepPosition) {

        if(!isDualPane){
            Intent intent = new Intent(this, RecipeDetailStepActivity.class);
            intent.putExtra(STEP_EXTRA_POSITION, stepPosition);
            intent.putExtra(RECIPE_EXTRA_TITLE, recipeTitle);
            intent.putExtra(RECIPE_EXTRA_ID, recipeId);
            startActivity(intent);
        }

    }


}
