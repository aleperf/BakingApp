package com.example.aleperf.bakingapp.dagger;

import android.app.Application;

import com.example.aleperf.bakingapp.BakingApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final BakingApplication application;

    public ApplicationModule(BakingApplication application){
        this.application = application;
    }

    @Provides
    @BakingApplicationScope
    BakingApplication provideBakingApplication(){
        return application;
    }

    @Provides
    @BakingApplicationScope
    Application provideApplication(){
        return application;
    }
}
