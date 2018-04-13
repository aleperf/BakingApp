package com.example.aleperf.bakingapp.ui.recipeDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


import com.example.aleperf.bakingapp.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Manage the details about the recipe step.
 */

public class RecipeDetailStepActivity extends AppCompatActivity {

    private static final String STEP_EXTRA_POSITION = "step extra position";
    private static final String RECIPE_EXTRA_TITLE = "recipe extra title";
    private static final String RECIPE_EXTRA_ID = "recipe extra id";

    @BindView(R.id.toolbar_detail_step)
    Toolbar toolbar;
    @BindView(R.id.test_text_view)
    TextView testTextView;

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

        testTextView.setText("Recipe id = " + String.valueOf(recipeId)
                + " Step Position = " + String.valueOf(stepPosition));

        FragmentManager fragmentManager = getSupportFragmentManager();



    }

}
