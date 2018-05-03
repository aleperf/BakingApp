package com.example.aleperf.bakingapp.widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.aleperf.bakingapp.BakingApplication;
import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.database.RecipeDao;
import com.example.aleperf.bakingapp.database.RecipeRepository;
import com.example.aleperf.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
        private ArrayList<String> recipesData = new ArrayList<>();
        private Flowable<List<Recipe>> recipes;


        public BakingAppRemoteViewFactory(Context applicationContext) {
            this.context = applicationContext;
            recipes = recipeDao.provideAllRecipes();
            subscribe(context);

        }

        private void subscribe(Context context) {

            recipes = recipeDao.provideAllRecipes();
            recipes.observeOn(Schedulers.io()).subscribe(new Consumer<List<Recipe>>() {
                @Override
                public void accept(List<Recipe> recipes) throws Exception {
                    for (int i = 0; i < recipes.size(); i++) {
                        String name = recipes.get(i).getName();
                        recipesData.add(name);

                    }

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
                return recipesData.size();
            }
            return 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Resources res = context.getResources();
            String name = recipesData.get(position);
            int ingredientsNumber = 8;
            String ingredientsText = String.format(res.getString(R.string.ingredients_number), ingredientsNumber);
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_item);
            rv.setTextViewText(R.id.widget_recipe_name, name);
            rv.setTextViewText(R.id.widget_number_of_ingredients, ingredientsText);
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
