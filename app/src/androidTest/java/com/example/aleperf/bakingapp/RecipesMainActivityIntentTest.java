package com.example.aleperf.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;


import com.example.aleperf.bakingapp.ui.intro.RecipesMainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;


public class RecipesMainActivityIntentTest {

    private final static int POSITION_ZERO = 0;
    private static final String RECIPE_EXTRA_ID = "recipe extra id";
    private static final String RECIPE_EXTRA_TITLE = "recipe extra title";
    private static final int RECIPE_ID = 1;
    private static final String NUTELLA_PIE = "Nutella Pie";


    private CountingIdlingResource countingIdlingResource;


    @Rule
    public IntentsTestRule<RecipesMainActivity> intentsTestRule =
            new IntentsTestRule<>(RecipesMainActivity.class);


    @Before
     public void setUp(){

        IdlingRegistry registry = IdlingRegistry.getInstance();
        countingIdlingResource = intentsTestRule.getActivity().getCountIdlingResource();
        registry.register(countingIdlingResource);
    }

    @Test
    public void intentSentToCorrectPackage(){
        onView(withId(R.id.recipes_grid_intro)).perform(RecyclerViewActions.actionOnItemAtPosition(POSITION_ZERO,
                click()));
        intended(toPackage("com.example.aleperf.bakingapp"));
    }

    @Test
    public void intentContainsExtras(){
        onView(withId(R.id.recipes_grid_intro)).perform(RecyclerViewActions.actionOnItemAtPosition(POSITION_ZERO,
                click()));
        intended(hasExtra(RECIPE_EXTRA_ID, RECIPE_ID));
        intended(hasExtra(RECIPE_EXTRA_TITLE, NUTELLA_PIE));
    }



    @After
    public void cleanUp(){
        IdlingRegistry registry = IdlingRegistry.getInstance();
        registry.unregister(countingIdlingResource);
    }

}



