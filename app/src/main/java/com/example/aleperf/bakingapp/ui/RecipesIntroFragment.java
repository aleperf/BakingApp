package com.example.aleperf.bakingapp.ui;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aleperf.bakingapp.BakingApplication;
import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.model.Recipe;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Shows the intro cards for every recipe
 */
public class RecipesIntroFragment extends Fragment {

    private final String TAG = RecipesIntroFragment.class.getSimpleName();
    RecyclerView recipesGrid;
    ImageView emptyMessageImageView;
    RecipesViewModel model;
    RecipesAdapter adapter;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    LiveData<List<Recipe>> recipes;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BakingApplication) getActivity().getApplication()).getBakingApplicationComponent().inject(this);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this, viewModelFactory).get(RecipesViewModel.class);
        recipes = model.getRecipes();
        subscribe();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_intro_recipes, container, false);
        recipesGrid = root.findViewById(R.id.recipes_grid_intro);
        emptyMessageImageView = root.findViewById(R.id.empty_image_view);
        emptyMessageImageView.setOnClickListener(v -> {
            model.getRecipes();
            Toast.makeText(getContext(), "Fetching Data", Toast.LENGTH_SHORT).show();
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recipesGrid.setLayoutManager(gridLayoutManager);
        adapter = new RecipesAdapter(getActivity());
        recipesGrid.setAdapter(adapter);
        return root;

    }

    /**
     * Subscribe to the recipe observable and change the UI according to its content.
     */

    private void subscribe() {
        Observer observer = (Observer<List<Recipe>>) recipes -> {
            if (recipes != null && recipes.size() != 0) {
                emptyMessageImageView.setVisibility(View.GONE);
                recipesGrid.setVisibility(View.VISIBLE);
                adapter.setRecipes(recipes);
            } else {
                //show here empty message or dialog prompting for connection
                Log.d(TAG, "adapter is null");
                recipesGrid.setVisibility(View.GONE);
                emptyMessageImageView.setVisibility(View.VISIBLE);
            }
        };
        recipes.observe(this, observer);
    }


}
