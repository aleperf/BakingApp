package com.example.aleperf.bakingapp.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WidgetConfigAdapter extends RecyclerView.Adapter<WidgetConfigAdapter.RecipeWidgetItem> {

    List<Recipe> recipes;
    Context context;

    interface ConfigCallback{
        void onRecipeSelected(int recipeId, String recipeName);
    }

    public WidgetConfigAdapter(Context context){
        this.context = context;
    }


    @NonNull
    @Override
    public WidgetConfigAdapter.RecipeWidgetItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.config_widget_item, parent, false);
        return new RecipeWidgetItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WidgetConfigAdapter.RecipeWidgetItem holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.bindRecipe(recipe);

    }

    @Override
    public int getItemCount() {
        if(recipes != null){
            return recipes.size();
        }
        return 0;
    }

    public void setRecipes(List<Recipe> recipes){
        this.recipes = recipes;
    }

    public class RecipeWidgetItem extends RecyclerView.ViewHolder implements  View.OnClickListener{
        @BindView(R.id.widget_recipe_name)
        TextView recipeName;
        @BindView(R.id.widget_number_of_ingredients)
        TextView numberOfIngredients;



        public RecipeWidgetItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bindRecipe(Recipe recipe){
            recipeName.setText(recipe.getName());
            numberOfIngredients.setText(String.format(context.getString(R.string.ingredients_label_intro_card),
                    recipe.getIngredients().size()));
        }

        @Override
        public void onClick(View v) {
            if(context instanceof ConfigCallback){
                Recipe recipe = recipes.get(getAdapterPosition());
               ConfigCallback configCallback = (ConfigCallback) context;
               configCallback.onRecipeSelected(recipe.getId(), recipe.getName());
            }

        }
    }
}
