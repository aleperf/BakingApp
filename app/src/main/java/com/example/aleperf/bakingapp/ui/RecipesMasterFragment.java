package com.example.aleperf.bakingapp.ui;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
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

import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.RecipesViewModel;
import com.example.aleperf.bakingapp.model.Recipe;

import java.util.List;

/**
 * Shows the intro cards for every recipe
 */
public class RecipesMasterFragment extends Fragment{
    RecyclerView recipesGrid;
    RecipesViewModel model;
    RecipesAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(RecipesViewModel.class);

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

        model.getRecipes().observe(this, new Observer<List<Recipe>>(){
            @Override
            public void onChanged(@Nullable List<Recipe>listLiveData) {
                if(listLiveData != null){
                    adapter.setRecipes(listLiveData);

                } else {
                    Log.d("TAG", "listLiveData is null");
                }
            }
        });
        return root;

    }
}
