package com.example.aleperf.bakingapp.dagger;

import android.app.Application;

import com.example.aleperf.bakingapp.ui.recipeDetail.RecipeDetailStepFragment;
import com.example.aleperf.bakingapp.ui.recipeDetail.RecipeSummaryFragment;
import com.example.aleperf.bakingapp.ui.intro.RecipesIntroFragment;

import dagger.Component;

@BakingApplicationScope
@Component(modules = {RecipeDatabaseModule.class, RecipesServiceModule.class, ApplicationModule.class})
public interface BakingApplicationComponent {

     void inject(RecipesIntroFragment fragment);
     void inject(RecipeSummaryFragment fragment);
     void inject(RecipeDetailStepFragment fragment);

     Application application();

}
