package com.bitocta.sportapp;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.bitocta.sportapp.ui.LoadingActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4ClassRunner.class)
public class StatisticsActivityTest {


    @Rule
    public ActivityTestRule<LoadingActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(LoadingActivity.class);

    @Test
    public void clickUpdateWeight_ShouldOpenUpdateWeightDialog(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Statistics")).perform(click());
        onView(withId(R.id.update_weight_text)).perform(click());
        onView(withId(R.id.dialog_update_weight)).check(matches(isDisplayed()));
    }
}
