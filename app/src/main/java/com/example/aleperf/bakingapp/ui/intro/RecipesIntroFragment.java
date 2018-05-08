package com.example.aleperf.bakingapp.ui.intro;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.aleperf.bakingapp.BakingApplication;
import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.model.Recipe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Shows the intro cards for every recipe
 */
public class RecipesIntroFragment extends Fragment {


    private static final String SCROLL_X_POS = "x scroll position";
    private static final String SCROLL_Y_POS = "y scroll position";

    @BindView(R.id.recipes_grid_intro)
    RecyclerView recipesRecyclerView;
    @BindView(R.id.intro_empty_view_constraint)
    ConstraintLayout emptyView;
    @BindView(R.id.recipes_intro_scroll_view)
    NestedScrollView introScrollView;
    RecipesViewModel model;
    RecipesAdapter adapter;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    LiveData<List<Recipe>> recipes;
    GridLayoutManager gridLayoutManager;
    private Unbinder unbinder;
    private int scrollX;
    private int scrollY;
    private boolean canCount = true;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BakingApplication) getActivity().getApplication()).getBakingApplicationComponent().inject(this);
        setRetainInstance(true);


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
        unbinder = ButterKnife.bind(this, root);
        emptyView.setOnClickListener(v -> {
            recipes = model.getRecipes();
            Toast.makeText(getContext(), getString(R.string.intro_fetching_data), Toast.LENGTH_SHORT).show();
        });
        int spanCount = getResources().getInteger(R.integer.intro_span_count);
        gridLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        recipesRecyclerView.setLayoutManager(gridLayoutManager);
        adapter = new RecipesAdapter(getActivity());
        recipesRecyclerView.setAdapter(adapter);
        if (savedInstanceState != null) {
            scrollX = savedInstanceState.getInt(SCROLL_X_POS);
            scrollY = savedInstanceState.getInt(SCROLL_Y_POS);
        }

        return root;

    }


    private void subscribe() {

        if(canCount){
            incrementIdlingResource();
            }

        Observer<List<Recipe>> observer = recipesData -> {

            if (recipesData != null && recipesData.size() != 0) {
                if(canCount){
                   decrementIdlingResource();
                    canCount = false;
                    }
                emptyView.setVisibility(View.GONE);
                recipesRecyclerView.setVisibility(View.VISIBLE);
                adapter.setRecipes(recipesData);
                introScrollView.post(() -> {
                    introScrollView.scrollTo(scrollX, scrollY);
                });


            } else {
                //show here empty message or dialog prompting for connection
                if (!isNetworkAvailable()) {
                    if(canCount){
                        decrementIdlingResource();
                        canCount = false;
                        }

                    recipesRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    }
            }
        };
        recipes.observe(this, observer);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        scrollX = introScrollView.getScrollX();
        scrollY = introScrollView.getScrollY();
        outState.putInt(SCROLL_X_POS, scrollX);
        outState.putInt(SCROLL_Y_POS, scrollY);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private void incrementIdlingResource(){
        if(getActivity() instanceof IdlingResourcesManager){
            IdlingResourcesManager manager = (IdlingResourcesManager)getActivity();
            manager.incrementIdlingResource();
        }
    }

    private void decrementIdlingResource(){
        if(getActivity() instanceof IdlingResourcesManager){
            IdlingResourcesManager manager = (IdlingResourcesManager)getActivity();
            manager.decrementIdlingResource();
        }
    }


}
