package com.example.aleperf.bakingapp.dagger;

import android.app.Application;

import com.example.aleperf.bakingapp.ui.RecipeSummaryFragment;
import com.example.aleperf.bakingapp.ui.RecipesIntroFragment;

import dagger.Component;

@BakingApplicationScope
@Component(modules = {RecipeDatabaseModule.class, RecipesServiceModule.class, ApplicationModule.class})
public interface BakingApplicationComponent {

     void inject(RecipesIntroFragment fragment);
     void inject(RecipeSummaryFragment fragment);

     Application application();

}
