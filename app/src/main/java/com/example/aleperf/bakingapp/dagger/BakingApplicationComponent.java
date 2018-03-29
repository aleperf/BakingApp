package com.example.aleperf.bakingapp.dagger;

import android.app.Application;

import com.example.aleperf.bakingapp.database.RecipeRepositoryImpl;
import com.example.aleperf.bakingapp.networking.RecipesService;
import com.example.aleperf.bakingapp.ui.RecipesMasterFragment;

import dagger.Component;

@BakingApplicationScope
@Component(modules = {RecipeDatabaseModule.class, RecipesServiceModule.class, ApplicationModule.class})
public interface BakingApplicationComponent {

     void inject(RecipesMasterFragment fragment);

     Application application();

}
