package com.example.aleperf.bakingapp.ui.recipeDetail;

import android.app.Dialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.aleperf.bakingapp.BakingApplication;
import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.model.Recipe;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Shows the a detailed list of ingredients
 */
public class IngredientsDialogFragment extends DialogFragment {

    private final static String RECIPE_ID = "ingredients recipe id";
    private final static String RECIPE_TITLE = "ingredients recipe title";
    private Unbinder unbinder;

    private int recipeId;
    private String recipeTitle;
    @BindView(R.id.ingredients_dialog_rv)
    RecyclerView ingredientsRecyclerView;
    @BindView(R.id.back_button)
    ImageButton clearButton;
    @Inject
    ViewModelProvider.Factory viewModelProviderFactory;
    private RecipeDetailViewModel model;
    private LiveData<Recipe> recipe;
    private IngredientsAdapter adapter;
    private LinearLayoutManager layoutManager;

    public IngredientsDialogFragment() {
        //empty constructor;
    }


    public static IngredientsDialogFragment newInstance(int recipeId, String recipeTitle) {
        Bundle bundle = new Bundle();
        bundle.putInt(RECIPE_ID, recipeId);
        bundle.putString(RECIPE_TITLE, recipeTitle);
        IngredientsDialogFragment fragment = new IngredientsDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
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
        View view = inflater.inflate(R.layout.dialog_fragment_ingredients, container, false);
        unbinder = ButterKnife.bind(this, view);
        recipeId = getArguments().getInt(RECIPE_ID, 1);
        recipeTitle = getArguments().getString(RECIPE_TITLE, "");
        layoutManager = new LinearLayoutManager(getActivity());
        ingredientsRecyclerView.setLayoutManager(layoutManager);
        adapter = new IngredientsAdapter(getActivity(), recipeTitle);
        ingredientsRecyclerView.setAdapter(adapter);
        addItemDecorator(ingredientsRecyclerView);
        clearButton.setOnClickListener(buttonView -> getDialog().dismiss());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(getActivity(), viewModelProviderFactory).get(RecipeDetailViewModel.class);
        recipe = model.getRecipe(recipeId);
        subscribe();
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    @Override
    public void onDestroyView() {
        //retain dialog fragment on rotation
        Dialog dialog = getDialog();
        if (dialog != null && getRetainInstance()) {
            dialog.setDismissMessage(null);
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void subscribe() {

        Observer<Recipe> observer = recipe -> {
            if (recipe != null) {
                adapter.setIngredients(recipe);
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
}
