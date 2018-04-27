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
import com.example.aleperf.bakingapp.model.Recipe.Step;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsSummaryAdapter extends RecyclerView.Adapter<StepsSummaryAdapter.StepsSummaryHolder> {

    private Context context;
    private List<Step> steps;


    public StepsSummaryAdapter(Context context){
        this.context = context;
    }


    @NonNull
    @Override
    public StepsSummaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.step_item, parent, false);
        return new StepsSummaryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsSummaryHolder holder, int position) {
        Step step = steps.get(position);
        holder.bindSteps(step, position);

    }

    @Override
    public int getItemCount() {
        if(steps != null){
            return steps.size();
        }
        return 0;
    }

    public void setSteps(List<Step> steps){
        this.steps = steps;
        notifyDataSetChanged();
    }


    public class StepsSummaryHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.step_summary_description)
        TextView summaryDescription;
        @BindView(R.id.step_number)
        TextView stepNumber;

       public  StepsSummaryHolder(View view){
           super(view);
           ButterKnife.bind(this, view);
           itemView.setOnClickListener(this);
       }

       void bindSteps(Step step, int position){
           stepNumber.setText(context.getString(R.string.step_numerator) + String.valueOf(position + 1));
           summaryDescription.setText(step.getShortDescription());
       }


        @Override
        public void onClick(View v) {
            if(context instanceof StepSelector){
                StepSelector stepSelector = (StepSelector) context;
                stepSelector.onStepSelected(getAdapterPosition());

            }
        }
    }
}
