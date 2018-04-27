package com.example.aleperf.bakingapp.ui.recipeDetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aleperf.bakingapp.R;
import com.google.android.exoplayer2.util.Util;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Shows the a detailed list of ingredients
 */
public class IngredientsDialogFragment extends DialogFragment {

    private final static String RECIPE_ID = "ingredients recipe id";
    private Unbinder unbinder;

    public IngredientsDialogFragment getInstance(int recipeId){
        Bundle bundle = new Bundle();
        bundle.putInt(RECIPE_ID, recipeId);
        IngredientsDialogFragment fragment = new IngredientsDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_ingredients, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();


    }
}
