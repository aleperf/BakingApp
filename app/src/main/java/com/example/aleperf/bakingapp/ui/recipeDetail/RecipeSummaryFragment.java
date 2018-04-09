package com.example.aleperf.bakingapp.ui.recipeDetail;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.transition.AutoTransition;

import android.support.transition.TransitionManager;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.TextView;

import com.example.aleperf.bakingapp.BakingApplication;
import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.model.Recipe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Shows ingredients and summary of descriptions of selected recipe's steps.
 */

public class RecipeSummaryFragment extends Fragment {

    private static final String RECIPE_EXTRA_ID = "recipe extra id";
    private static final String INGREDIENTS_EXPANSION_STATE = "ingredients expansion state";

    @BindView(R.id.ingredients_title_text_view)
    TextView ingredientTitle;
    @BindView(R.id.ingredient_recycler_view)
    RecyclerView ingredientList;
    @BindView(R.id.button_expand)
    ImageButton buttonExpand;
    @BindView(R.id.button_hide)
    ImageButton buttonHide;
    @BindView(R.id.ingredients_section)
    ConstraintLayout ingredientsSection;

    RecipeDetailViewModel model;
    @Inject
    ViewModelProvider.Factory viewModelProviderFactory;
    IngredientsAdapter ingredientsAdapter;
    LiveData<Recipe> recipe;

    private Unbinder unbinder;
    private boolean isExpandedIngredientList = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BakingApplication) getActivity().getApplication()).getBakingApplicationComponent().inject(this);

        }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipe_summary, container, false);
        unbinder = ButterKnife.bind(this, root);
        if(savedInstanceState != null){
            isExpandedIngredientList = savedInstanceState.getBoolean(INGREDIENTS_EXPANSION_STATE, false);
        }
        LinearLayoutManager ingredientManager = new LinearLayoutManager(getActivity());
        ingredientList.setLayoutManager(ingredientManager);
        ingredientsAdapter = new IngredientsAdapter(getActivity());
        ingredientList.setAdapter(ingredientsAdapter);
        addItemDecorator(ingredientList);
        setIngredientsExpansionListener();

        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this, viewModelProviderFactory).get(RecipeDetailViewModel.class);
        recipe = model.getRecipe(getActivity().getIntent().getIntExtra(RECIPE_EXTRA_ID, 1));
        subscribe();
    }

    private void subscribe() {
        Observer<Recipe> observer = new Observer<Recipe>() {

            @Override
            public void onChanged(@Nullable Recipe recipe) {
                if (recipe != null) {
                    ingredientTitle.setText(recipe.getName() + getString(R.string.recipe_ingredient));
                    ingredientsAdapter.setIngredients(recipe);
                    }
            }
        };
        recipe.observe(this, observer);
    }



    /**
     * Add a divider decoration to a RecyclerView
     */

    private void addItemDecorator(RecyclerView recyclerView) {
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(itemDecorator);
    }

    private void setIngredientsExpansionListener(){

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(ingredientsSection, new AutoTransition().setDuration(400));
                setUpIngredientListAppearance(isExpandedIngredientList);
                isExpandedIngredientList = !isExpandedIngredientList;
                }
        };
        buttonExpand.setOnClickListener(listener);
        buttonHide.setOnClickListener(listener);
    }

    /**
     * Change the Ingredients List appearance according to its expansion state
     * @param isExpanded a boolean representing the state of ingredient list, true if expanded, false if not
     */

    public void setUpIngredientListAppearance(boolean isExpanded){

        if(isExpanded){
            ingredientList.setVisibility(View.GONE);
            buttonHide.setVisibility(View.GONE);
            buttonExpand.setVisibility(View.VISIBLE);


        } else {
            ingredientList.setVisibility(View.VISIBLE);
            buttonExpand.setVisibility(View.GONE);
            buttonHide.setVisibility(View.VISIBLE);


        }

    }

    @Override
    public void onResume() {
        super.onResume();
        setUpIngredientListAppearance(!isExpandedIngredientList);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(INGREDIENTS_EXPANSION_STATE, isExpandedIngredientList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
