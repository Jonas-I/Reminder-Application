package edu.qc.seclass.glm;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    /*@Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }*/

    /*
    @Test
    public void CreateButtonTest() {
        onView(withId(R.id.createButton)).perform(click());
        onView(withId(R.id.overlay)).check(matches(isDisplayed()));
        onView(withId(R.id.createCenterView)).check(matches(isDisplayed()));
        onView(withId(R.id.createReminder)).check(matches(isDisplayed()));
        onView(withId(R.id.createList)).check(matches(isDisplayed()));
        onView(withId(R.id.cancel)).check(matches(isDisplayed()));
    }
    */

    /*@Test
    public void CreateReminderActivityLaunchTest() {
        onView(withId(R.id.createButton)).perform(click());
        intended(hasComponent(CreateReminderActivity.class.getName()));
    }*/
/*
    @Test
    public void CreateReminderListActivityLaunchTest() {
        onView(withId(R.id.createButton)).perform(click());
        onView(withId(R.id.createList)).perform(click());
        intended(hasComponent(CreateReminderListActivity.class.getName()));
    }

    @Test
    public void CreateButtonCancelTest() {
        onView(withId(R.id.createButton)).perform(click());
        onView(withId(R.id.cancel)).perform(click());
        onView(withId(R.id.overlay)).check(matches(not(isDisplayed())));
        onView(withId(R.id.createCenterView)).check(matches(not(isDisplayed())));
        onView(withId(R.id.createReminder)).check(matches(not(isDisplayed())));
        onView(withId(R.id.createList)).check(matches(not(isDisplayed())));
        onView(withId(R.id.cancel)).check(matches(not(isDisplayed())));
    }
*/
}
