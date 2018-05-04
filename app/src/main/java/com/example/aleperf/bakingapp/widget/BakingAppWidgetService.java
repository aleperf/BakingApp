package com.example.aleperf.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.aleperf.bakingapp.BakingApplication;
import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.database.RecipeDao;
import com.example.aleperf.bakingapp.model.Recipe;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BakingAppWidgetService extends RemoteViewsService {

    @Inject
    RecipeDao recipeDao;

    @Override
    public void onCreate() {
        super.onCreate();
        ((BakingApplication) this.getApplicationContext()).getBakingApplicationComponent().inject(this);

    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new BakingAppRemoteViewFactory(this.getApplicationContext());
    }

    public class BakingAppRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

        Context context;
        private List<Recipe> recipes;
        private Flowable<List<Recipe>> recipesObs;


        public BakingAppRemoteViewFactory(Context applicationContext) {
            this.context = applicationContext;
            recipesObs = recipeDao.provideAllRecipes();
            subscribe(context);

        }

        /**
         * Observe the the database and triggers a widget update after loading new data
         *
         * @param context
         */

        private void subscribe(Context context) {

            recipesObs = recipeDao.provideAllRecipes();
            recipesObs.observeOn(Schedulers.io()).subscribe(new Consumer<List<Recipe>>() {
                @Override
                public void accept(List<Recipe> recipesList) throws Exception {
                    recipes = recipesList;

                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    int appWidgetIds[] = appWidgetManager.getAppWidgetIds(
                            new ComponentName(context, BakingAppWidget.class));
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_recipes_list);


                }
            });
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

            if (recipes != null) {
                return recipes.size();
            }
            return 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Resources res = context.getResources();
            Recipe recipe = recipes.get(position);
            String name = recipe.getName();
            int id = recipe.getId();
            int ingredientsNumber = recipe.getIngredients().size();
            String ingredientsText = String.format(res.getString(R.string.ingredients_number), ingredientsNumber);
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_item);
            rv.setTextViewText(R.id.widget_recipe_name, name);
            rv.setTextViewText(R.id.widget_number_of_ingredients, ingredientsText);
            //set fillIntent
            Bundle extras = new Bundle();
            extras.putString(BakingAppWidget.RECIPE_EXTRA_TITLE, name);
            extras.putInt(BakingAppWidget.RECIPE_EXTRA_ID, id);
            Intent fillIntent = new Intent(BakingAppWidget.SHOW_INGREDIENTS_ACTION);
            fillIntent.putExtras(extras);
            rv.setOnClickFillInIntent(R.id.widget_item, fillIntent);
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
