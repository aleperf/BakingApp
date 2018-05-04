package com.example.aleperf.bakingapp.widget;

import android.app.PendingIntent;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.arch.lifecycle.LiveData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.aleperf.bakingapp.BakingApplication;
import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.database.RecipeDao;
import com.example.aleperf.bakingapp.database.RecipeRepository;
import com.example.aleperf.bakingapp.model.Recipe;
import com.example.aleperf.bakingapp.ui.intro.RecipesMainActivity;
import com.example.aleperf.bakingapp.ui.recipeDetail.RecipeDetailActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class BakingAppWidget extends AppWidgetProvider {

    private final static String RECIPES_EXTRA = "baking app recipes extra list";
    public final static String SHOW_INGREDIENTS_ACTION ="com.example.aleperf.bakingapp.SHOW_INGREDIENT_ACTION";
    public static final String RECIPE_EXTRA_TITLE = "recipe extra title";
    public static final String RECIPE_EXTRA_ID = "recipe extra id";



    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        if(intent.getAction().equals(SHOW_INGREDIENTS_ACTION)){
            Bundle extrasBundle = intent.getExtras();
            Intent openIngredientList = new Intent(context, RecipeDetailActivity.class);
            openIngredientList.setAction(SHOW_INGREDIENTS_ACTION);
            openIngredientList.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            openIngredientList.putExtras(extrasBundle);
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
            taskStackBuilder.addNextIntentWithParentStack(openIngredientList);
            context.startActivity(openIngredientList);

        }
        super.onReceive(context, intent);
        }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {


        // update each of the app widgets with the remote adapter
        for (int i = 0; i < appWidgetIds.length; ++i) {

            // Set up the intent that starts the StackViewService, which will
            // provide the views for this collection.
            Intent intent = new Intent(context, BakingAppWidgetService.class);
            // Add the app widget ID to the intent extras.
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            // Instantiate the RemoteViews object for the app widget layout.
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
            // Set up the RemoteViews object to use a RemoteViews adapter.
            // This adapter connects
            // to a RemoteViewsService  through the specified intent.
            // This is how you populate the data.
            rv.setRemoteAdapter(R.id.widget_recipes_list, intent);

            // The empty view is displayed when the collection has no items.
            // It should be in the same layout used to instantiate the RemoteViews
            // object above.
            rv.setEmptyView(R.id.widget_recipes_list, R.id.widget_empty_view);

            //Set a PendingIntent to broadcast the SHOW_INGREDIENTS_ACTION

            Intent showIngredientsIntent = new Intent(context, BakingAppWidget.class);
            showIngredientsIntent.setAction(SHOW_INGREDIENTS_ACTION);
            showIngredientsIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            showIngredientsIntent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent showIngredientPendingIntent = PendingIntent.getBroadcast(context, 0,
                    showIngredientsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.widget_recipes_list, showIngredientPendingIntent);
            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
           // appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds[i], R.id.widget_recipes_list);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

