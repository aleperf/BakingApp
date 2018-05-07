package com.example.aleperf.bakingapp.ui.recipeDetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.model.Recipe;
import com.example.aleperf.bakingapp.model.Recipe.Ingredient;
import com.example.aleperf.bakingapp.utils.RecipeUtilities;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<ViewHolder> {

  Context context;
  List<Recipe.Ingredient> ingredients;
  private final  int TITLE_VIEW_TYPE = 0;
  private final int INGREDIENT_VIEW_TYPE = 1;
  private boolean IS_CLICKABLE = false;
  private String recipeTitle;


  public IngredientsAdapter(Context context, String recipeTitle){

    this.context = context;
    this.recipeTitle = recipeTitle;
    }


  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(context);
      View view;
      switch (viewType) {
          case TITLE_VIEW_TYPE:
              view = inflater.inflate(R.layout.ingredient_title, parent, false);
              return new IngredientTitleHolder(view, context, recipeTitle, IS_CLICKABLE);
          default:
              view = inflater.inflate(R.layout.ingredient_item, parent, false);
              return new IngredientDescriptionHolder(view);


      }
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      switch (holder.getItemViewType()) {
          case TITLE_VIEW_TYPE:
              IngredientTitleHolder ingredientTitleHolder = (IngredientTitleHolder) holder;
              ingredientTitleHolder.bindIngredient();
              break;
          default:
              IngredientDescriptionHolder ingredientDescHolder = (IngredientDescriptionHolder) holder;
              ingredientDescHolder.bindIngredient(ingredients.get(position - 1));
      }

  }

  @Override
  public int getItemCount() {
    if(ingredients != null){
        return ingredients.size()  +  1;
    }

    return 0;
  }

  public void setIngredients(Recipe recipe){
      ingredients = recipe.getIngredients();
      notifyDataSetChanged();
  }

    @Override
    public int getItemViewType(int position) {

      if(position == 0){
          return TITLE_VIEW_TYPE;
      }

      return INGREDIENT_VIEW_TYPE;
    }

    class IngredientDescriptionHolder extends RecyclerView.ViewHolder{

      @BindView(R.id.ingredient_item_title)
      TextView ingredientTitle;
      @BindView(R.id.ingredient_quantity)
      TextView ingredientQuantity;


    public IngredientDescriptionHolder(View view){
        super(view);
        ButterKnife.bind(this, view);
        }

        public void  bindIngredient(Ingredient ingredient){
            ingredientTitle.setText(ingredient.getIngredient());
            String formattedMeasure = RecipeUtilities.getNormalizedMeasure(context, ingredient.getQuantity(), ingredient.getMeasure());
            ingredientQuantity.setText(formattedMeasure);

        }



  }
}
