package com.example.aleperf.bakingapp.database;


import android.util.Log;

import com.example.aleperf.bakingapp.model.Recipe;
import com.example.aleperf.bakingapp.networking.RecipesService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of the Repository Pattern. The repository is the only source of data in the app,
 * it manage the database and the networking.
 */

public class RecipeRepositoryImpl implements RecipeRepository {

    private final String TAG = RecipeRepositoryImpl.class.getSimpleName();


    RecipeDao recipeDao;

    RecipesService client;


    @Inject
    public RecipeRepositoryImpl(RecipeDao recipeDao, RecipesService client) {
        this.recipeDao = recipeDao;
        this.client = client;
    }


    @Override
    public Maybe<List<Recipe>> getAllRecipes() {
        checkForEmptyDatabase();
        return recipeDao.getAllRecipes().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Recipe> getRecipeWithId(int id) {
        return recipeDao.getRecipeWithId(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void insertAllRecipes(List<Recipe> recipes) {
        if (recipes == null) {
            return;
        }
        Completable.fromAction(() -> recipeDao.insertAllRecipes(recipes)).subscribeOn(Schedulers.io()).subscribe();

    }

    @Override
    public void insertRecipe(Recipe recipe) {
        if (recipe == null) {
            return;
        }

        Completable.fromAction(() -> recipeDao.insertRecipe(recipe)).subscribeOn(Schedulers.io());
    }

    /**
     * Check if the database is empty, if it is empty load recipes
     * from network and insert new data in the database
     */

    public void checkForEmptyDatabase() {
        recipeDao.getNumberOfRecipes().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new MaybeObserver<Integer>() {
                    Integer recipeCount = 0;

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "subscribing in Maybe");
                    }

                    @Override
                    public void onSuccess(Integer integer) {

                        recipeCount = integer;
                        Log.d(TAG, "in onSuccess, recipeCount == " + recipeCount);
                        if (recipeCount == 0) {
                            loadRecipes();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "in on Complete, recipeCount == " + recipeCount);
                        if (recipeCount == 0) {
                            Log.d(TAG, "in on complete, loading recipes");
                            loadRecipes();
                        }
                    }
                }
        );
    }

    /**
     * Load recipes from network and insert them in the database
     */

    private void loadRecipes() {
        Call<List<Recipe>> call = client.getAllRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> responseRecipes = response.body();
                insertAllRecipes(responseRecipes);
                Log.d(TAG, "recipes inserted");
                if (responseRecipes != null) {
                    Log.d(TAG, "recipes size: " + responseRecipes.size());
                }

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d(TAG, "error in loading recipes " + t.getMessage());

            }
        });
    }
}
