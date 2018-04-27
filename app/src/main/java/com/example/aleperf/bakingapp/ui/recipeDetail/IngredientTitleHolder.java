package com.example.aleperf.bakingapp.ui.recipeDetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleperf.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IngredientTitleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;
    private String recipeTitle;
    @BindView(R.id.ingredients_text_view)
    TextView ingredientsTextView;

    public IngredientTitleHolder(View view, Context context, String recipeTitle) {
        super(view);
        ButterKnife.bind(this, view);
        this.context = context;
        this.recipeTitle = recipeTitle;
        view.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //TODO START THE INGREDIENTS ACTIVITY OR DIALOG
        Toast.makeText(context, "clicking " + recipeTitle, Toast.LENGTH_SHORT).show();
    }

    public void  bindIngredient(String recipeName){
        String ingredientsTitle = context.getResources().getString(R.string.recipe_ingredient);
        ingredientsTextView.setText(String.format(ingredientsTitle, recipeName));
    }
}
