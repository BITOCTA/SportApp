package com.bitocta.sportapp;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import com.bitocta.sportapp.ui.LoadingActivity;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class StartDayTest {

    private static final int DAY_NUMBER=1;

    @Rule
    public ActivityTestRule<LoadingActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(LoadingActivity.class);

    @Test
    public void clickSpecificDay_StartButtonIsVisible(){
        onView(withId(R.id.days_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(DAY_NUMBER-1,
                        click()));

        onView(withId(R.id.fab_start_day)).check(matches(isDisplayed()));


    }

    @Test
    public void clickStartButton_ShouldOpenActivityExerciseTimer(){
        onView(withId(R.id.days_list)).perform(RecyclerViewActions.actionOnItemAtPosition(DAY_NUMBER-1,click()));
        onView(withId(R.id.fab_start_day)).perform(click());
        onView(withId(R.id.activity_exercise_timer)).check(matches(isDisplayed()));
    }

}
