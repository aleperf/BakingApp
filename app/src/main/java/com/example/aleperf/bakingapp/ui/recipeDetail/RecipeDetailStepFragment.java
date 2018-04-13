package com.example.aleperf.bakingapp.ui.recipeDetail;

import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aleperf.bakingapp.BakingApplication;
import com.example.aleperf.bakingapp.model.Recipe.Step;
import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.model.Recipe;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Shows details of a single step of a recipe: long description and video, if present.
 */

public class RecipeDetailStepFragment extends Fragment {

    private static final String STEP_EXTRA_POSITION = "step extra position";
    private static final String RECIPE_EXTRA_ID = "recipe extra id";

    private int recipeId;
    private int stepPosition;
    private List<Step> steps;
    private LiveData<Recipe> recipe;
    private Unbinder unbinder;

    private SimpleExoPlayer exoPlayer;

    @BindView(R.id.step_video)
    PlayerView playerView;
    @BindView(R.id.step_title)
    TextView stepTitle;
    @BindView(R.id.steps_number)
    TextView stepNumber;
    @BindView(R.id.step_long_description)
    TextView stepDescription;
    @BindView(R.id.arrowLeft)
    ImageButton arrowLeft;
    @BindView(R.id.arrowRight)
    ImageButton arrowRight;
    @BindView(R.id.recipeThumbnail)
    ImageView thumbnail;


    public RecipeDetailStepFragment getInstance(int recipeId, int stepPosition) {
        Bundle bundle = new Bundle();
        bundle.putInt(STEP_EXTRA_POSITION, stepPosition);
        bundle.putInt(RECIPE_EXTRA_ID, recipeId);
        RecipeDetailStepFragment fragment = new RecipeDetailStepFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BakingApplication) getActivity().getApplication()).getBakingApplicationComponent().inject(this);
        if (savedInstanceState == null) {
            Bundle bundle = this.getArguments();
            recipeId = bundle.getInt(RECIPE_EXTRA_ID);
            stepPosition = bundle.getInt(STEP_EXTRA_POSITION);
        } else {
            recipeId = savedInstanceState.getInt(RECIPE_EXTRA_ID);
            stepPosition = savedInstanceState.getInt(STEP_EXTRA_POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_step_detail, container, false);
        unbinder = ButterKnife.bind(this, root);


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //TODO: for SDK >= 23 initialize player here
    }

    @Override
    public void onStart() {
        super.onStart();
        //TODO: for SDK < 23 initialize player here
    }

    @Override
    public void onPause() {
        super.onPause();
        //TODO for SDK >= 23 release player here
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        //TODO: for SDK < 23 release player here
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(RECIPE_EXTRA_ID, recipeId);
        outState.putInt(STEP_EXTRA_POSITION, stepPosition);
    }
}
