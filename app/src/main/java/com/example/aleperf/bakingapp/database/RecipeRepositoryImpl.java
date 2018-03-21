package com.example.aleperf.bakingapp.database;


import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.aleperf.bakingapp.model.Recipe;
import com.example.aleperf.bakingapp.networking.RecipesClient;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeRepositoryImpl implements RecipeRepository{

    private final String TAG = RecipeRepositoryImpl.class.getSimpleName();

    RecipeDao recipeDao;

    RecipesClient client;




    @Inject
    public RecipeRepositoryImpl(RecipeDao recipeDao, RecipesClient client){
        this.recipeDao = recipeDao;
        this.client = client;

        getAllRecipes().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new MaybeObserver<List<Recipe>>() {
                    List<Recipe> actualRecipes = new ArrayList<>();
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Recipe> recipes) {
                     actualRecipes.addAll(recipes);
                     Log.d(TAG, "adding recipes in onSuccess, recipes.size = " + String.valueOf(recipes.size()));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                     if(actualRecipes.size() == 0){
                         Log.d(TAG, "No recipes found");
                         loadRecipes();
                     }
                    }
                }
        );




        }
        //qui potrei far partire il network
        //TODO potrei far partire il networking da qui, con una chiamata ad initializeDatabase.


    @Override
    public Maybe<List<Recipe>> getAllRecipes() {
       return getAllRecipes().observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Recipe> getRecipeWithId(int id) {
        return recipeDao.getRecipeWithId(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void insertAllRecipes(List<Recipe> recipes) {
        if(recipes == null){
          return;
        }
        Completable.fromAction(()-> recipeDao.insertAllRecipes(recipes)).subscribeOn(Schedulers.io());

    }

    @Override
    public void insertRecipe(Recipe recipe) {

        Completable.fromAction(()-> recipeDao.insertRecipe(recipe)).subscribeOn(Schedulers.io());
    }

    private void loadRecipes(){
        Call<List<Recipe>> call = client.getAllRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> responseRecipes = response.body();
                recipeDao.insertAllRecipes(responseRecipes);

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d(TAG, "error in loading recipes " + t.getMessage());

            }
        });
    }
}
