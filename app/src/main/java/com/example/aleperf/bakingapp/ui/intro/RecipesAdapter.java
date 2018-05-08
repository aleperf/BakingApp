package com.example.aleperf.bakingapp.ui.intro;


import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.model.Recipe;
import com.squareup.picasso.Picasso;
import com.example.aleperf.bakingapp.utils.RecipeUtilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

    private List<Recipe> recipes;
    private Context context;

    interface RecipeCallback {
        void onClickRecipe(Recipe recipe);
    }


    public RecipesAdapter(Context context) {
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
        holder.bindRecipe(position);
    }

    @Override
    public int getItemCount() {
        if (recipes != null) {
            return recipes.size();
        }
        return 0;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
        }


    public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.cake_image)
        ImageView cakeImage;
        @BindView(R.id.cake_name)
        TextView cakeName;
        @BindView(R.id.servings_label)
        TextView servings;
        @BindView(R.id.ingredients_label)
        TextView ingredients;


        public RecipesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        public void bindRecipe(int position) {
            Recipe recipe = recipes.get(position);
            Resources res = context.getResources();
            String imageUrl = recipe.getImage();
            int defaultDrawableId = RecipeUtilities.getImageDefaultId(position);

            if (imageUrl != null && imageUrl.length() > 0) {
                Picasso.get().load(imageUrl).placeholder(defaultDrawableId).error(defaultDrawableId).into(cakeImage);
            } else {
                cakeImage.setImageResource(defaultDrawableId);
            }
            cakeName.setText(recipe.getName());
            ingredients.setText(String.format(res.getString(R.string.ingredients_label_intro_card), recipe.getIngredients().size()));
            servings.setText(String.format(res.getString(R.string.servings_label_intro_card), recipe.getServings()));

        }


        @Override
        public void onClick(View v) {
            if (context instanceof RecipeCallback) {
                Recipe recipe = recipes.get(getAdapterPosition());
                ((RecipeCallback) context).onClickRecipe(recipe);
            }
        }
    }


}
