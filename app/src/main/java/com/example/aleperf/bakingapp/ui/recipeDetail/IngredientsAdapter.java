package com.example.aleperf.bakingapp.ui.recipeDetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.model.Recipe;
import com.example.aleperf.bakingapp.model.Recipe.Ingredient;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientHolder> {

  Context context;
  List<Recipe.Ingredient> ingredients;


  public IngredientsAdapter(Context context){

    this.context = context;

  }


  @NonNull
  @Override
  public IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(context);
      View view = inflater.inflate(R.layout.ingredient_item, parent, false);
      return new IngredientHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull IngredientHolder holder, int position) {
      Ingredient ingredient =  ingredients.get(position);
      holder.bindIngredient(ingredient);

  }

  @Override
  public int getItemCount() {
    if(ingredients != null){
        return ingredients.size();
    }

    return 0;
  }

  public void setIngredients(Recipe recipe){
      ingredients = recipe.getIngredients();
      notifyDataSetChanged();
  }

  class  IngredientHolder extends RecyclerView.ViewHolder{

      @BindView(R.id.ingredient_item_title)
      TextView ingredientTitle;
      @BindView(R.id.ingredient_quantity)
      TextView ingredientQuantity;
      @BindView(R.id.ingredient_measure)
      TextView ingredientMeasure;

    public IngredientHolder(View view){
        super(view);
        ButterKnife.bind(this, view);
        }

        public void  bindIngredient(Ingredient ingredient){
            ingredientTitle.setText(ingredient.getIngredient());
            ingredientQuantity.setText(String.valueOf(ingredient.getQuantity()));
            ingredientMeasure.setText(ingredient.getMeasure());

        }



  }
}
