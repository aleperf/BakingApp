package com.example.aleperf.bakingapp.ui.recipeDetail;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.transition.AutoTransition;

import android.support.transition.TransitionManager;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.ViewTreeObserver;
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
 * Shows ingredients and summary descriptions of selected recipe's steps.
 */

public class RecipeSummaryFragment extends Fragment {

    private static final String RECIPE_EXTRA_ID = "recipe extra id";
    private static final String INGREDIENTS_EXPANSION_STATE = "ingredients expansion state";
    private static final String SCROLL_X = "scrollview x";
    private static final String SCROLL_Y = "scrollview Y";


    @BindView(R.id.ingredients_title_text_view)
    TextView ingredientTitle;
    @BindView(R.id.ingredient_recycler_view)
    RecyclerView ingredientsRecyclerView;
    @BindView(R.id.button_expand)
    ImageButton buttonExpand;
    @BindView(R.id.button_hide)
    ImageButton buttonHide;
    @BindView(R.id.ingredients_section)
    ConstraintLayout ingredientsSection;
    @BindView(R.id.recipe_steps)
    RecyclerView stepsRecyclerView;
    @BindView(R.id.summary_scroll_view)
    NestedScrollView nestedScrollView;


    RecipeDetailViewModel model;
    @Inject
    ViewModelProvider.Factory viewModelProviderFactory;
    IngredientsAdapter ingredientsAdapter;
    LinearLayoutManager ingredientsManager;
    LinearLayoutManager stepsManager;
    StepsSummaryAdapter stepsAdapter;
    LiveData<Recipe> recipe;
    int scrollX = 0;
    int scrollY = -1;

    private Unbinder unbinder;
    private boolean isExpandedIngredientList = false;



    public interface StepSelector {
        void onStepSelected(int stepPosition);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BakingApplication) getActivity().getApplication()).getBakingApplicationComponent().inject(this);
        setRetainInstance(true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipe_summary, container, false);
        unbinder = ButterKnife.bind(this, root);
        if (savedInstanceState != null) {
            isExpandedIngredientList = savedInstanceState.getBoolean(INGREDIENTS_EXPANSION_STATE, false);
            }
        ingredientsManager = new LinearLayoutManager(getActivity());
        ingredientsRecyclerView.setLayoutManager(ingredientsManager);
        ingredientsAdapter = new IngredientsAdapter(getActivity());
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
        addItemDecorator(ingredientsRecyclerView);
        addButtonExpansionHideListener();

        stepsManager = new LinearLayoutManager(getActivity());
        stepsRecyclerView.setLayoutManager(stepsManager);
        stepsAdapter = new StepsSummaryAdapter(getActivity());
        stepsRecyclerView.setAdapter(stepsAdapter);

        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState != null) {
            scrollX = savedInstanceState.getInt(SCROLL_X);
            scrollY = savedInstanceState.getInt(SCROLL_Y);
            ViewTreeObserver viewTreeObserver = nestedScrollView.getViewTreeObserver();
            //wait for the NestedScrollView to be added to the layout and then scroll
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    nestedScrollView.scrollTo(scrollX, scrollY);
                    }
            });
        }
        }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this, viewModelProviderFactory).get(RecipeDetailViewModel.class);
        recipe = model.getRecipe(getActivity().getIntent().getIntExtra(RECIPE_EXTRA_ID, 1));
        subscribe();
    }

    private void subscribe() {
        Observer<Recipe> observer = recipe -> {
            if (recipe != null) {
                ingredientTitle.setText(recipe.getName() + getString(R.string.recipe_ingredient));
                ingredientsAdapter.setIngredients(recipe);
                stepsAdapter.setSteps(recipe.getSteps());
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

    /**
     * Add listeners on the buttons for expanding and hiding the ingredients list
     */

    private void addButtonExpansionHideListener() {

        View.OnClickListener listener = view -> {
            TransitionManager.beginDelayedTransition(ingredientsSection, new AutoTransition().setDuration(300));
            setUpIngredientListAppearance(isExpandedIngredientList);
            isExpandedIngredientList = !isExpandedIngredientList;
            scrollY = 0;

        };
        buttonExpand.setOnClickListener(listener);
        buttonHide.setOnClickListener(listener);
    }

    /**
     * Change the Ingredients List appearance according to its expansion state
     *
     * @param isExpanded a boolean representing the state of ingredient list, true if expanded, false if not
     */

    public void setUpIngredientListAppearance(boolean isExpanded) {

        if (isExpanded) {
            ingredientsRecyclerView.setVisibility(View.GONE);
            buttonHide.setVisibility(View.GONE);
            buttonExpand.setImageResource(R.drawable.ic_keyboard_arrow_down);



        } else {
            ingredientsRecyclerView.setVisibility(View.VISIBLE);
            buttonExpand.setImageResource(R.drawable.ic_keyboard_arrow_up);
            buttonHide.setVisibility(View.VISIBLE);

            }

        }

    @Override
    public void onResume() {
        super.onResume();
        //doesn't change the ingredient list expansion, restore the previous one.
        setUpIngredientListAppearance(!isExpandedIngredientList);
    }

    @Override
    public void onPause() {
        super.onPause();
        }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(INGREDIENTS_EXPANSION_STATE, isExpandedIngredientList);
        int x = nestedScrollView.getScrollX();
        int y = nestedScrollView.getScrollY();
        outState.putInt(SCROLL_X, x);
        outState.putInt(SCROLL_Y, y);


        }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
