package com.example.aleperf.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.aleperf.bakingapp.BakingApplication;
import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.database.RecipeRepository;
import com.example.aleperf.bakingapp.model.Recipe;
import com.example.aleperf.bakingapp.ui.intro.RecipesAdapter;
import com.example.aleperf.bakingapp.ui.intro.RecipesViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BakingAppWidgetConfigureActivity extends AppCompatActivity implements WidgetConfigAdapter.ConfigCallback {

    @BindView(R.id.widget_config_recycler_view)
    RecyclerView recyclerView;
    LiveData<List<Recipe>> recipes;
    @Inject
    RecipeRepository repository;
    WidgetConfigAdapter adapter;

    int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;


    public BakingAppWidgetConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId, int recipeId, String name) {
        SharedPreferences.Editor prefs = context.getApplicationContext().getSharedPreferences(context.getString(R.string.prefs_name), 0).edit();
        prefs.putInt(context.getString(R.string.prefs_prefix) + appWidgetId, recipeId);
        prefs.putString(context.getString(R.string.prefs_recipe_prefix) + appWidgetId, name);
        prefs.apply();
    }


    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getApplicationContext().getSharedPreferences(context.getString(R.string.prefs_name), 0).edit();
        prefs.remove(context.getString(R.string.prefs_prefix) + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        ((BakingApplication) this.getApplication()).getBakingApplicationComponent().inject(this);
        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);
        setContentView(R.layout.baking_app_widget_configure);
        ButterKnife.bind(this);
        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }
        LinearLayoutManager manager = new LinearLayoutManager(this);
        adapter = new WidgetConfigAdapter(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recipes = repository.getAllRecipes();
        subscribe();
        }

    private void subscribe() {
        Observer<List<Recipe>> observer = recipes -> {
            if (recipes != null && recipes.size() != 0) {
                adapter.setRecipes(recipes);
            }
        };
        recipes.observe(this, observer);
    }

    @Override
    public void onRecipeSelected(int recipeId, String name) {
        saveTitlePref(this, appWidgetId, recipeId, name);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        BakingAppWidget.updateAppWidget(this, appWidgetManager, appWidgetId);
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        super.onResume();
        Resources res = getResources();
        int width = res.getDimensionPixelSize(R.dimen.dialog_fragment_width);
        int height = res.getDimensionPixelSize(R.dimen.dialog_fragment_height);
        getWindow().setLayout(width, height);
    }
}

