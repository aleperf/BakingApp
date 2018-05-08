package com.example.aleperf.bakingapp;

import android.content.Intent;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;

import com.example.aleperf.bakingapp.ui.recipeDetail.RecipeDetailStepActivity;
import com.google.android.exoplayer2.ui.PlayerView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;

import static org.hamcrest.core.AllOf.allOf;

public class RecipeDetailStepActivityBasicTest {

    private  CountingIdlingResource countingIdlingResource;
    private static final String STEP_EXTRA_POSITION = "step extra position";
    private static final String RECIPE_EXTRA_TITLE = "recipe extra title";
    private static final String RECIPE_EXTRA_ID = "recipe extra id";
    private static final String NUTELLA_PIE = "Nutella Pie";
    private static final int NUTELLA_ID = 1;
    private static final int STEP_POSITION = 0;
    private static final int STEP_POSITION_NO_VIDEO = 1;
    private static final int TOTAL_STEPS = 6;
    private static final int PHONE_LANDSCAPE = 2;


    @Rule
    public ActivityTestRule<RecipeDetailStepActivity> rule = new ActivityTestRule<>(RecipeDetailStepActivity.class,
            false, false);

    @Before
    public void setUp() {
        Intent intent = new Intent();
        intent.putExtra(RECIPE_EXTRA_TITLE, NUTELLA_PIE);
        intent.putExtra(RECIPE_EXTRA_ID, NUTELLA_ID);
        intent.putExtra(STEP_EXTRA_POSITION, STEP_POSITION);
        rule.launchActivity(intent);
        countingIdlingResource = rule.getActivity().getCountIdlingResource();
        IdlingRegistry.getInstance().register(countingIdlingResource);
    }

    @Test
    public void isDisplayedPlayerView() {
        onView(allOf(instanceOf(PlayerView.class), withId(R.id.step_video))).check(matches(isDisplayed()));
    }

    @Test
    public void isDisplayedVideoPlaceholderIfNoVideo() {

        int screenType = rule.getActivity().getResources().getInteger(R.integer.max_screen_switch);
        if (screenType != PHONE_LANDSCAPE) {
            onView(withId(R.id.arrowRight)).perform(click());
            onView(withId(R.id.step_video_place_holder)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void isDisplayedCorrectStepTitle() {
        onView(withId(R.id.title_step_counter)).check(matches(withText(rule.getActivity().getString(R.string.intro_step))));
    }

    @Test
    public void clickingArrowLeftOnFirstStepNotNavigateBack() {
        int screenType = rule.getActivity().getResources().getInteger(R.integer.max_screen_switch);
        if (screenType != PHONE_LANDSCAPE) {
            onView(withId(R.id.arrowLeft)).perform(click());
            onView(withId(R.id.arrowLeft)).perform(click());
            onView(withId(R.id.title_step_counter)).check(matches(withText(rule.getActivity().getString(R.string.intro_step))));
        }
    }

    @Test
    public void clickingOnLastStepNotNavigateFurther() {
        int screenType = rule.getActivity().getResources().getInteger(R.integer.max_screen_switch);
        if (screenType != PHONE_LANDSCAPE) {
            for (int i = 0; i <= TOTAL_STEPS; i++) {
                onView(withId(R.id.arrowRight)).perform(click());
            }
            String formatString = rule.getActivity().getString(R.string.step_count);
            String stepTitle = String.format(formatString, TOTAL_STEPS, TOTAL_STEPS);
            onView(withId(R.id.title_step_counter)).check(matches(withText(stepTitle)));
        }
    }

    @Test
    public void clickingOneTimeGoToNextStep() {
        int screenType = rule.getActivity().getResources().getInteger(R.integer.max_screen_switch);
        if (screenType != PHONE_LANDSCAPE) {
            onView(withId(R.id.arrowRight)).perform(click());
            String formatString = rule.getActivity().getString(R.string.step_count);
            String stepTitle = String.format(formatString, STEP_POSITION_NO_VIDEO, TOTAL_STEPS);
            onView(withId(R.id.title_step_counter)).check(matches(withText(stepTitle)));
        }
    }

    @After
    public void cleanUp() {
        IdlingRegistry.getInstance().unregister(countingIdlingResource);
    }
}
