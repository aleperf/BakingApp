package com.example.aleperf.bakingapp.ui.intro;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

    private List<Recipe> recipes;
    private Context context;

    public interface RecipeCallback{
        void onClickRecipe(Recipe recipe);
    }

    public RecipesAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_card_item, parent, false);
        return new RecipesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
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
        notifyDataSetChanged();
    }


    public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.cake_image)
        ImageView cakeImage;
        @BindView(R.id.cake_name)
        TextView cakeName;
        @BindView(R.id.servings_number)
        TextView servings;
        @BindView(R.id.steps_number)
        TextView steps;


        public RecipesViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
            }

        public void bindRecipe(Recipe recipe){
            String imageUrl = recipe.getImage();
            if(imageUrl != null && imageUrl.length() > 0){
                //loadImage with Picasso
            } else {
                cakeImage.setImageResource(R.drawable.cake_placeholder_16_9);
            }
            cakeName.setText(recipe.getName());
            servings.setText(String.valueOf(recipe.getServings()));
            steps.setText(String.valueOf(recipe.getSteps().size()));

        }


        @Override
        public void onClick(View v) {
            if(context instanceof RecipesMainActivity){
                Recipe recipe = recipes.get(getAdapterPosition());
                ((RecipesMainActivity) context).onClickRecipe(recipe);
            }
        }
    }


}
