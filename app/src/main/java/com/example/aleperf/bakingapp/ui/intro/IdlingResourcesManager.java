package com.example.aleperf.bakingapp.ui.intro;

import android.support.test.espresso.idling.CountingIdlingResource;

public interface IdlingResourcesManager {

    void incrementIdlingResource();
    void decrementIdlingResource();
    CountingIdlingResource getCountIdlingResource();
}
