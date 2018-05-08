package com.example.aleperf.bakingapp;

import android.content.Intent;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;

import com.example.aleperf.bakingapp.ui.recipeDetail.RecipeDetailActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class RecipeDetailActivityBasicTest {

    private static final String RECIPE_EXTRA_ID = "recipe extra id";
    private static final String RECIPE_EXTRA_TITLE = "recipe extra title";
    private static final int RECIPE_ID = 1;
    private static final String NUTELLA_PIE = "Nutella Pie";
    private static final int POSITION_INGREDIENTS = 0;
    private static final int PHONE_LANDSCAPE = 2;



    private CountingIdlingResource countingIdlingResource;

    @Rule
    public ActivityTestRule<RecipeDetailActivity> rule = new ActivityTestRule<>(RecipeDetailActivity.class,
            false, false);

    @Before
    public void setUp() {
        Intent intent = new Intent();
        intent.putExtra(RECIPE_EXTRA_TITLE, NUTELLA_PIE);
        intent.putExtra(RECIPE_EXTRA_ID, RECIPE_ID);
        rule.launchActivity(intent);
        countingIdlingResource = rule.getActivity().getCountIdlingResource();
        IdlingRegistry.getInstance().register(countingIdlingResource);
    }


    @Test
    public void checkIsDisplayedCorrectRecipeNameInToolbar() {
        onView(withText(NUTELLA_PIE)).check(matches(withParent(withId(R.id.detail_activity_toolbar))));
    }

    @Test
    public void ingredientCardOpensDialog() {
        String ingredientString = rule.getActivity().getString(R.string.recipe_ingredient);
        String ingredientTitle = String.format(ingredientString, NUTELLA_PIE);
        onView(withId(R.id.summary_recycler_view)).perform(RecyclerViewActions.scrollToPosition(POSITION_INGREDIENTS));
        onView(withText(ingredientTitle)).perform(click());
        onView(withId(R.id.ingredients_dialog_rv)).check(matches(isDisplayed()));

    }

    @Test
    public void checkCorrectStepIsOpened() {
        int screenType = rule.getActivity().getResources().getInteger(R.integer.max_screen_switch);
        String stepOne = rule.getActivity().getString(R.string.test_string_step);
        String stepOneOfSix = rule.getActivity().getString(R.string.test_string_step_counter);
        if (screenType != PHONE_LANDSCAPE) {
            onView(withId(R.id.summary_recycler_view)).perform(RecyclerViewActions.scrollToPosition(1));
            onView(withText(stepOne)).perform(click());
            onView(withText(stepOneOfSix)).check(matches(isDisplayed()));
        }
    }

    @After
    public void cleanUp() {
        IdlingRegistry.getInstance().unregister(countingIdlingResource);
    }


}



