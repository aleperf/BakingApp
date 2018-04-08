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
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.aleperf.bakingapp.BakingApplication;
import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.model.Recipe;
import com.example.aleperf.bakingapp.ui.recipeDetail.IngredientsAdapter;
import com.example.aleperf.bakingapp.ui.recipeDetail.RecipeDetailViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Shows, briefly, ingredients and summary of descriptions of selected recipe's steps.
 */

public class RecipeSummaryFragment extends Fragment {

    private static final String RECIPE_EXTRA_ID = "recipe extra id";

    @BindView(R.id.ingredients_title_text_view)
    TextView ingredientTitle;
    @BindView(R.id.ingredient_recycler_view)
    RecyclerView ingredientList;
    @BindView(R.id.button_expand)
    ImageButton buttonExpand;
    @BindView(R.id.button_hide)
    ImageButton buttonHide;

    RecipeDetailViewModel model;
    @Inject
    ViewModelProvider.Factory viewModelProviderFactory;
    IngredientsAdapter ingredientsAdapter;
    LiveData<Recipe> recipe;

    private Unbinder unbinder;


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
        LinearLayoutManager ingredientManager = new LinearLayoutManager(getActivity());
        ingredientList.setLayoutManager(ingredientManager);
        ingredientsAdapter = new IngredientsAdapter(getActivity());
        ingredientList.setAdapter(ingredientsAdapter);
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
                    Log.d("uffa", "recipe non è null");
                    ingredientTitle.setText(recipe.getName());
                    ingredientsAdapter.setIngredients(recipe);
                    List<Recipe.Ingredient> ingredients = recipe.getIngredients();
                    Log.d("uffa", "ingredients size = " + ingredients.size());
                    Log.d("uffa", "primo ingrediente " + ingredients.get(0).getIngredient());

                } else {
                    Log.d("uffa", "recipe è null");
                }
            }
        };

        recipe.observe(this, observer);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
