package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import androidx.test.espresso.intent.Intents;


import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new
            ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void testAddCity(){
        // Clicking the add button
        onView(withId(R.id.button_add)).perform(click());

        // Type the city name
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));

        // Press the continue button
        onView(withId(R.id.button_confirm)).perform(click());

        // Check if the city (edmonton) exists in the list
        onView(withText("Edmonton")).check(matches(isDisplayed()));
    }

    @Test
    public void textClearCity(){
        // Add first city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Add second city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Calgary"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Press clear button
        onView(withId(R.id.button_clear)).perform(click());

        // Check if the cities are removed
        onView(withText("Edmonton")).check(doesNotExist());
        onView(withText("Calgary")).check(doesNotExist());
    }

    @Test
    public void testListView(){
        // Add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Checking if it matches the first element
        onData(is(instanceOf(String.class))).inAdapterView(withId(R.id.city_list)).atPosition(0).check(matches(withText("Edmonton")));
    }

    @Test
    public void testActivitySwitchesToShowActivity() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        Intents.init();
        onView(withId(R.id.city_list)).perform(click());
        intended(hasComponent(ShowActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void testCityNameIsConsistent() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        onView(withId(R.id.city_list)).perform(click());

        onView(withId(R.id.text_view)).check(matches(withText("Edmonton")));
    }

    @Test
    public void testBackButtonReturnsToMainActivity() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        onView(withId(R.id.city_list)).perform(click());
        onView(withId(R.id.button_back)).perform(click());
        onView(withId(R.id.city_list)).check(matches(withId(R.id.city_list)));
    }

}
