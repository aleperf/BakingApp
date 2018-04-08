package com.example.aleperf.bakingapp.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.aleperf.bakingapp.model.Recipe;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientHolder> {


  @NonNull
  @Override
  public IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull IngredientHolder holder, int position) {

  }

  @Override
  public int getItemCount() {
    return 0;
  }

  class  IngredientHolder extends RecyclerView.ViewHolder{

    public IngredientHolder(View view){
        super(view);

    }

  }
}
