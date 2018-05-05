package com.example.aleperf.bakingapp.ui.recipeDetail;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aleperf.bakingapp.BakingApplication;
import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.model.Recipe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Shows ingredients and summary descriptions of selected recipe's steps.
 */

public class RecipeSummaryFragment extends Fragment {

    private static final String RECIPE_EXTRA_ID = "recipe extra id";

    private static final String FIRST_VISIBLE_ITEM_POSITION = "first completely visible item position";
    private int firstVisibleItemPosition;


    @BindView(R.id.summary_recycler_view)
    RecyclerView summaryRecyclerView;
    RecipeDetailViewModel model;
    @Inject
    ViewModelProvider.Factory viewModelProviderFactory;
    LinearLayoutManager summaryManager;
    SummaryAdapter summaryAdapter;
    LiveData<Recipe> recipe;
    private Unbinder unbinder;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BakingApplication) getActivity().getApplication()).getBakingApplicationComponent().inject(this);
        setRetainInstance(true);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipe_summary2, container, false);
        unbinder = ButterKnife.bind(this, root);
        summaryManager = new LinearLayoutManager(getActivity());
        summaryRecyclerView.setLayoutManager(summaryManager);
        summaryAdapter = new SummaryAdapter(getActivity());
        summaryRecyclerView.setAdapter(summaryAdapter);
        if (savedInstanceState != null) {
            firstVisibleItemPosition = savedInstanceState.getInt(FIRST_VISIBLE_ITEM_POSITION);
        }
        return root;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this, viewModelProviderFactory).get(RecipeDetailViewModel.class);
        recipe = model.getRecipe(getActivity().getIntent().getIntExtra(RECIPE_EXTRA_ID, 1));
        subscribe();
        String callingActivityAction = getActivity().getIntent().getAction();
        if(callingActivityAction != null && callingActivityAction.equals(RecipeDetailActivity.SHOW_INGREDIENTS_ACTION ) && savedInstanceState == null  ){
            if(getActivity() instanceof IngredientsDisplay);
            IngredientsDisplay display  = (IngredientsDisplay) getActivity();
            display.displayIngredients();
        }
    }

    private void subscribe() {
        Observer<Recipe> observer = recipe -> {
            if (recipe != null) {
                summaryAdapter.setSummaryContent(recipe.getName(), recipe.getSteps());
                summaryRecyclerView.scrollToPosition(firstVisibleItemPosition);
            }
        };
        recipe.observe(this, observer);
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        int position = summaryManager.findFirstVisibleItemPosition();
        outState.putInt(FIRST_VISIBLE_ITEM_POSITION, position);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
