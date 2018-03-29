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

import com.example.aleperf.bakingapp.BakingApplication;
import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.RecipesViewModel;
import com.example.aleperf.bakingapp.model.Recipe;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;

/**
 * Shows the intro cards for every recipe
 */
public class RecipesMasterFragment extends Fragment{

    private final String TAG = RecipesMasterFragment.class.getSimpleName();
    RecyclerView recipesGrid;
    RecipesViewModel model;
    RecipesAdapter adapter;
    @Inject
    ViewModelProvider.Factory viewModelFactory;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BakingApplication)getActivity().getApplication()).getBakingApplicationComponent().inject(this);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_master_recipes, container, false);
        recipesGrid = root.findViewById(R.id.recipes_grid_intro);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1);
        recipesGrid.setLayoutManager(gridLayoutManager);
        adapter = new RecipesAdapter(getActivity());
        recipesGrid.setAdapter(adapter);
        model = ViewModelProviders.of(this, viewModelFactory).get(RecipesViewModel.class);
        model.getRecipes().subscribe(new MaybeObserver<List<Recipe>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<Recipe> recipes) {
                Log.d(TAG, "Recipes loaded, recipes.size() = " + String.valueOf(recipes.size()));
                adapter.setRecipes(recipes);

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "error: " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
        return root;

    }


}
