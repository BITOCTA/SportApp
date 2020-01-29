package com.bitocta.sportapp;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.bitocta.sportapp.ui.LoadingActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4ClassRunner.class)
public class ExerciseTimerActivityTest {

    private static final String DAY_TITLE = "Day ";
    private static final int DAY_NUMBER=1;
    private static final String FIRST_EXERCISE_NUMBER_TAG="Exercise 1 of 3";
    private static final String SECOND_EXERCISE_NUMBER_TAG="Exercise 2 of 3";
    private static final int DAY_EXERCISES_NUMBER=3;


    @Rule
    public ActivityTestRule<LoadingActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(LoadingActivity.class);

    @Test
    public void startExerciseTimerActivity_OnlyShouldDisplayStartExerciseButton(){
        onView(withId(R.id.days_list)).perform(RecyclerViewActions.actionOnItemAtPosition(DAY_NUMBER-1,click()));
        onView(withId(R.id.fab_start_day)).perform(click());
        onView(withId(R.id.start_exercise_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.end_exercise_btn)).check(matches(not(isDisplayed())));
        onView(withId(R.id.next_exercise_btn)).check(matches(not(isDisplayed())));
    }

    @Test
    public void clickStartExerciseButton_ShouldDisplayEndExerciseButton(){

        onView(withId(R.id.days_list)).perform(RecyclerViewActions.actionOnItemAtPosition(DAY_NUMBER-1,click()));
        onView(withId(R.id.fab_start_day)).perform(click());
        onView(withId(R.id.start_exercise_btn)).perform(click());
        onView(withId(R.id.end_exercise_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.next_exercise_btn)).check(matches(not(isDisplayed())));
    }

    @Test
    public void clickEndExerciseButton_ShouldDisplayNextExerciseButton(){

        onView(withId(R.id.days_list)).perform(RecyclerViewActions.actionOnItemAtPosition(DAY_NUMBER-1,click()));
        onView(withId(R.id.fab_start_day)).perform(click());
        onView(withId(R.id.start_exercise_btn)).perform(click());
        onView(withId(R.id.end_exercise_btn)).perform(click());
        onView(withId(R.id.next_exercise_btn)).check(matches(isDisplayed()));
    }

    @Test
    public void clickNextExerciseButton_ShouldSwitchToNextExercise(){

        onView(withId(R.id.days_list)).perform(RecyclerViewActions.actionOnItemAtPosition(DAY_NUMBER-1,click()));
        onView(withId(R.id.fab_start_day)).perform(click());

        onView(withId(R.id.ex_number)).check(matches(withText(FIRST_EXERCISE_NUMBER_TAG)));

        onView(withId(R.id.start_exercise_btn)).perform(click());
        onView(withId(R.id.end_exercise_btn)).perform(click());
        onView(withId(R.id.next_exercise_btn)).perform(click());

        onView(withId(R.id.ex_number)).check(matches(withText(SECOND_EXERCISE_NUMBER_TAG)));


    }

    @Test
    public void completeDay_ShouldDisplayEndDayActivityWithCorrespondingTropheyDay(){

        onView(withId(R.id.days_list)).perform(RecyclerViewActions.actionOnItemAtPosition(DAY_NUMBER-1,click()));
        onView(withId(R.id.fab_start_day)).perform(click());

        for(int i=0;i<DAY_EXERCISES_NUMBER;i++){
            onView(withId(R.id.start_exercise_btn)).perform(click());
            onView(withId(R.id.end_exercise_btn)).perform(click());
            onView(withId(R.id.next_exercise_btn)).perform(click());

        }

        onView(withId(R.id.activity_end_day)).check(matches(isDisplayed()));
        onView(withId(R.id.day_trophey)).check(matches(withText(DAY_TITLE+DAY_NUMBER)));

        onView(withId(R.id.go_to_progress_btn)).perform(click());
        onView(withId(R.id.fragment_progress)).check(matches(isDisplayed()));


    }


}


