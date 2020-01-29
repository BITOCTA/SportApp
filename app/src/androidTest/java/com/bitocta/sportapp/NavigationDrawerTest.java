package com.bitocta.sportapp;

import android.content.Intent;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.bitocta.sportapp.ui.LoadingActivity;
import com.bitocta.sportapp.ui.MainActivity;
import com.bitocta.sportapp.ui.WelcomeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import androidx.test.espresso.*;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.contrib.DrawerMatchers.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;


@RunWith(AndroidJUnit4ClassRunner.class)
public class NavigationDrawerTest {

    @Rule
    public ActivityTestRule<LoadingActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(LoadingActivity.class);



    @Test
    public void clickBurgerButton_ShouldOpenNavigationDrawer(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open()).check(matches(isOpen()));
    }


    @Test
    public void clickDifferentOptions_ShouldOpenDifferentFragments() {

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Statistics")).perform(click());
        onView(withId(R.id.fragment_statistics)).check(matches(isDisplayed()));

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Progress")).perform(click());
        onView(withId(R.id.fragment_progress)).check(matches(isDisplayed()));

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Plans")).perform(click());
        onView(withId(R.id.fragment_plans)).check(matches(isDisplayed()));
    }

    @Test
    public void clickAvatar_ShouldOpenProfileFragment(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_user_image)).perform(click());
        onView(withId(R.id.fragment_profile)).check(matches(isDisplayed()));
    }




}
