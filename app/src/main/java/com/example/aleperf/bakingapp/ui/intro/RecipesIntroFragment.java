package com.example.aleperf.bakingapp.ui.intro;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
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

    private static final String TAG = RecipesIntroFragment.class.getSimpleName();
    private static final String SCROLL_X_POS = "x scroll position";
    private static final String SCROLL_Y_POS = "y scroll position";

    @BindView(R.id.recipes_grid_intro)
    RecyclerView recipesRecyclerView;
    @BindView(R.id.empty_image_view)
    ImageView emptyMessageImageView;
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
        emptyMessageImageView.setOnClickListener(v -> {
            model.getRecipes();
            Toast.makeText(getContext(), "Fetching Data", Toast.LENGTH_SHORT).show();
        });
        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recipesRecyclerView.setLayoutManager(gridLayoutManager);
        adapter = new RecipesAdapter(getActivity());
        recipesRecyclerView.setAdapter(adapter);
        if(savedInstanceState != null){
            scrollX = savedInstanceState.getInt(SCROLL_X_POS);
            scrollY = savedInstanceState.getInt(SCROLL_Y_POS);
        }

        return root;

    }


    private void subscribe() {
        Observer<List<Recipe>> observer = recipes -> {
            if (recipes != null && recipes.size() != 0) {
                emptyMessageImageView.setVisibility(View.GONE);
                recipesRecyclerView.setVisibility(View.VISIBLE);
                adapter.setRecipes(recipes);
                introScrollView.post(() -> {
                    introScrollView.scrollTo(scrollX, scrollY);
                    });
            } else {
                //show here empty message or dialog prompting for connection
                recipesRecyclerView.setVisibility(View.GONE);
                emptyMessageImageView.setVisibility(View.VISIBLE);
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
}
