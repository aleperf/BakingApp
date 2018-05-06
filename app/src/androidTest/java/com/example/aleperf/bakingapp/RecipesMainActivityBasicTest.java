package com.example.aleperf.bakingapp;



import android.content.Intent;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;

import com.example.aleperf.bakingapp.ui.intro.RecipesIntroFragment;
import com.example.aleperf.bakingapp.ui.intro.RecipesMainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RecipesMainActivityBasicTest {

     @Rule
     public ActivityTestRule<RecipesMainActivity> mActivityRule = new ActivityTestRule<>(
             RecipesMainActivity.class, false, false);


     @Before
     public void launchActivity(){
          mActivityRule.launchActivity(new Intent("android.intent.action.MAIN"));
     }

     @Test
     public void isDisplayedToolbar(){
          onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
     }


     @Test
     public void isDisplayedRecyclerView(){
          onView(withId(R.id.recipes_grid_intro)).check(matches(isDisplayed()));
     }



}
