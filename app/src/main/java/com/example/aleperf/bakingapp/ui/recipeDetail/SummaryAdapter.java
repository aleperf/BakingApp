package com.example.aleperf.bakingapp.ui.recipeDetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.model.Recipe.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SummaryAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final int VIEW_TYPE_INGREDIENT = 0;
    private final int VIEW_TYPE_STEP = 1;
    private final int INTRO_POSITION = 1;
    private final boolean IS_CLICKABLE = true;
    Context context;
    String recipeTitle;
    List<Step> steps;

    public SummaryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        switch (viewType) {
            case VIEW_TYPE_INGREDIENT:
                view = inflater.inflate(R.layout.ingredients_card_selection, parent, false);
                return new IngredientTitleHolder(view, context, recipeTitle, IS_CLICKABLE);
            default:
                view = inflater.inflate(R.layout.step_item, parent, false);
                return new StepHolder(view);


        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_INGREDIENT:
                IngredientTitleHolder ingredientTitleHolder = (IngredientTitleHolder) holder;
                ingredientTitleHolder.bindIngredient();
                break;
            default:
                StepHolder stepHolder = (StepHolder) holder;
                stepHolder.bindSteps(steps.get(position - 1), position);
        }

    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (recipeTitle != null) {
            count++;
        }
        if (steps != null) {
            count += steps.size();
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_INGREDIENT;
        }
        return VIEW_TYPE_STEP;
    }

    public void setSummaryContent(String recipeTitle, List<Step> steps) {
        this.recipeTitle = recipeTitle;
        this.steps = steps;
    }




    public class StepHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.step_summary_description)
        TextView summaryDescription;
        @BindView(R.id.title_step_counter)
        TextView stepNumber;

        public StepHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(this);
        }

        void bindSteps(Step step, int position) {
            if (position == INTRO_POSITION) {
                stepNumber.setText(context.getString(R.string.intro_step));
            } else {
                String formatString = context.getString(R.string.step_summary_card);
                String stepText = String.format(formatString, position - 1);
                stepNumber.setText(stepText);
            }
            summaryDescription.setText(step.getShortDescription());
        }

        @Override
        public void onClick(View v) {
            if (context instanceof StepSelector) {
                StepSelector stepSelector = (StepSelector) context;
                stepSelector.onStepSelected(getAdapterPosition() - 1);

            }
        }
    }
}
