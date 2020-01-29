package com.bitocta.sportapp;

import android.widget.TextView;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.bitocta.sportapp.ui.LoadingActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(AndroidJUnit4ClassRunner.class)
public class ProgressRecyclerViewText {

    private static int THIRD_DAY_NUMBER = 3;
    private static int FIFTH_DAY_NUMBER = 5;
    private static int TENTH_DAY_NUMBER = 10;
    private static String DAY_TITLE = "Day ";

    @Rule
    public ActivityTestRule<LoadingActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(LoadingActivity.class);


    @Test
    public void clickDay_ShouldOpenSpecificDay(){
        onView(withId(R.id.days_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(THIRD_DAY_NUMBER-1,
                        click()));

        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText(DAY_TITLE+THIRD_DAY_NUMBER)));

        pressBack();

        onView(withId(R.id.days_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(FIFTH_DAY_NUMBER-1,
                        click()));

        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText(DAY_TITLE+FIFTH_DAY_NUMBER)));

        pressBack();

        onView(withId(R.id.days_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(TENTH_DAY_NUMBER-1,
                        click()));

        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText(DAY_TITLE+TENTH_DAY_NUMBER)));



    }
}
