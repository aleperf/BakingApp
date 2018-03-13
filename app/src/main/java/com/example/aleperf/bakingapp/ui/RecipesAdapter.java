package com.example.aleperf.bakingapp.ui;


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

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

    private List<Recipe> recipes;
    private Context context;

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


    public class RecipesViewHolder extends RecyclerView.ViewHolder{

        private ImageView cakeImage;
        private TextView cakeName;
        private TextView servings;
        private TextView steps;


        public RecipesViewHolder(View view){
            super(view);
            cakeImage = view.findViewById(R.id.cake_image);
            cakeName = view.findViewById(R.id.cake_name);
            servings = view.findViewById(R.id.servings_number);
            steps = view.findViewById(R.id.steps_number);
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



    }


}
