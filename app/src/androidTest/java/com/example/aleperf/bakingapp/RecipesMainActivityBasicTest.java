package com.example.aleperf.bakingapp;



import android.content.Intent;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;

import com.example.aleperf.bakingapp.ui.intro.RecipesMainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipesMainActivityBasicTest {

    private  final static int POSITION_ZERO = 0;

    private static CountingIdlingResource countingIdlingResource;

     @Rule
     public ActivityTestRule<RecipesMainActivity> activityRule = new ActivityTestRule<>(
             RecipesMainActivity.class, false, false);


     @Before
     public void launchActivity(){
          activityRule.launchActivity(new Intent());
          }

    @Before
    public void setUp(){

        IdlingRegistry registry = IdlingRegistry.getInstance();
        countingIdlingResource = activityRule.getActivity().getCountIdlingResource();
        registry.register(countingIdlingResource);
    }


    @Test
     public void isDisplayedToolbar(){
          onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
     }


     @Test
     public void isDisplayedRecyclerView(){
          onView(withId(R.id.recipes_grid_intro)).check(matches(isDisplayed()));
     }

     @Test
    public void isDisplayedAppNameInToolbar(){
         onView(withText(R.string.app_name)).check(matches(withParent(withId(R.id.toolbar))));
     }

     @Test
     public void isPerformedClickOnCard(){
         onView(withId(R.id.recipes_grid_intro)).perform(RecyclerViewActions.actionOnItemAtPosition(POSITION_ZERO,
                 click()));

         onView(withId(R.id.summary_recycler_view)).check(matches(isDisplayed()));
     }

    @After
    public void cleanUp(){
        IdlingRegistry registry = IdlingRegistry.getInstance();
        registry.unregister(countingIdlingResource);
    }


}
