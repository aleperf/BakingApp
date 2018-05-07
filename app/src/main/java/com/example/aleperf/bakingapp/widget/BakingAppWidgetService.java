package com.example.aleperf.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.example.aleperf.bakingapp.BakingApplication;
import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.database.RecipeRepository;
import com.example.aleperf.bakingapp.model.Recipe;
import com.example.aleperf.bakingapp.model.Recipe.Ingredient;
import com.example.aleperf.bakingapp.utils.RecipeUtilities;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
public class BakingAppWidgetService extends RemoteViewsService {



    @Inject
    RecipeRepository repository;
    private int appWidgetId;


    @Override
    public void onCreate() {
        super.onCreate();
        ((BakingApplication) this.getApplicationContext()).getBakingApplicationComponent().inject(this);

    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        return new BakingAppRemoteViewFactory(this.getApplicationContext());
    }

    public class BakingAppRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

        Context context;
        private List<Ingredient> ingredients;
        private Flowable<Recipe> recipeObs;


        public BakingAppRemoteViewFactory(Context applicationContext) {
            this.context = applicationContext;
            subscribe(context);

        }

        /**
         * Observe the the database and triggers a widget update after loading new data
         *
         * @param context, the calling context.
         */

        private void subscribe(Context context) {
            int id = getRecipeIdPrefs(context);
            recipeObs = repository.provideRecipeWithId(id);

            recipeObs.observeOn(Schedulers.io()).subscribe(new Consumer<Recipe>() {
                @Override
                public void accept(Recipe recipe) {
                    ingredients = recipe.getIngredients();
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    int appWidgetIds[] = appWidgetManager.getAppWidgetIds(
                            new ComponentName(context, BakingAppWidget.class));
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_recipes_list);


                }
            });

            }

        private int getRecipeIdPrefs(Context context) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.prefs_name), 0);
            return sharedPreferences.getInt(context.getString(R.string.prefs_prefix) + appWidgetId, 0);

        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
        }

        @Override
        public int getCount() {

            if (ingredients != null) {
                return ingredients.size();
            }
            return 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Ingredient ingredient = ingredients.get(position);
            String name = ingredient.getIngredient();
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
            rv.setTextViewText(R.id.widget_item_baking_app_name, name);
            String quantity = RecipeUtilities.getNormalizedMeasure(context, ingredient.getQuantity(),
                    ingredient.getMeasure());
            rv.setTextViewText(R.id.widget_item_baking_app_quantity, quantity);
            return rv;

        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {

            return position;

        }

        @Override
        public boolean hasStableIds() {
            return true;
        }


    }

}
