package com.example.aleperf.bakingapp.dagger;


import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;

import com.example.aleperf.bakingapp.BakingViewModelProviderFactory;
import com.example.aleperf.bakingapp.database.RecipeDao;
import com.example.aleperf.bakingapp.database.RecipeDatabase;
import com.example.aleperf.bakingapp.database.RecipeRepositoryImpl;
import com.example.aleperf.bakingapp.networking.RecipesService;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module(includes = {RecipesServiceModule.class, ApplicationModule.class})
public class RecipeDatabaseModule {

    private RecipeDatabase database;


    @Inject
    public RecipeDatabaseModule(Application application){
        this.database = Room.databaseBuilder(application, RecipeDatabase.class, "Recipe.db").build();


    }

    @Provides
    @BakingApplicationScope
    RecipeRepositoryImpl provideRecipeRepositoryImpl(RecipeDao recipeDao, RecipesService recipesService){
        return new RecipeRepositoryImpl(recipeDao, recipesService);
    }

    @Provides
    @BakingApplicationScope
    RecipeDao provideRecipeDao(RecipeDatabase database){
        return database.recipeDao();
    }

    @Provides
    @BakingApplicationScope
    RecipeDatabase provideRecipeDatabase(Application application){
        return database;
    }

    @Provides
    @BakingApplicationScope
    ViewModelProvider.Factory  getViewModelFactory(RecipeRepositoryImpl repository){
        return new BakingViewModelProviderFactory(repository);
    }



}
