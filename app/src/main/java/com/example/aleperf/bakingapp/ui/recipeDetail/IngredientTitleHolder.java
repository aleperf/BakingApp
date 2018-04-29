package com.example.aleperf.bakingapp.ui.recipeDetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleperf.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IngredientTitleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;
    private String recipeTitle;
    private boolean isClickable;
    @BindView(R.id.ingredients_text_view)
    TextView ingredientsTextView;


    public IngredientTitleHolder(View view, Context context, String recipeTitle, boolean isClickable) {
        super(view);
        ButterKnife.bind(this, view);
        this.context = context;
        this.recipeTitle = recipeTitle;
        this.isClickable = isClickable;
        view.setOnClickListener(this);


    }

    public void  bindIngredient(){
        String ingredientsTitle = context.getResources().getString(R.string.recipe_ingredient);
        ingredientsTextView.setText(String.format(ingredientsTitle, recipeTitle));
    }

    @Override
    public void onClick(View v) {
        if(isClickable){
            Toast.makeText(context, "Is clickable: " + recipeTitle, Toast.LENGTH_SHORT).show();
        }

    }
}
