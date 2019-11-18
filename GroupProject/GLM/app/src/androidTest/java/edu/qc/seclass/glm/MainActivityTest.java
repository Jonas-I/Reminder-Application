package edu.qc.seclass.glm;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.StringStartsWith.startsWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void AddReminderListTestInMain() {
        onView(withId(R.id.createButton)).perform(click());
        onView(withId(R.id.inputType)).perform(typeText("Appointments"), closeSoftKeyboard());
        onView(withId(R.id.inputDescription)).perform(typeText("Doctor's Appointment"), closeSoftKeyboard());
        onView(withId(R.id.createReminderDone)).perform(click());

        onView(allOf(withId(R.id.remListTitle), withText(startsWith("Appointments")))).perform(click());
        onView(allOf(withId(R.id.remItemName), withText(startsWith("Doctor's Appointment")))).perform(click());
    }

    @Test
    public void createButtonOpensCreateReminderActivity() {
        onView(withId(R.id.createButton)).perform(click());
        onView(withId((R.id.createReminderTitle))).check(matches(withText("Create Reminder")));
    }

    @Test
    public void AddReminderWithAlertTest() {
        onView(withId(R.id.createButton)).perform(click());
        onView(withId(R.id.inputType)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.inputDescription)).perform(typeText("Test Reminder"), closeSoftKeyboard());
        onView(withId(R.id.inputTimeButton)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(6, 30));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.inputDateButton)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2019, 12, 31));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.createReminderDone)).perform(click());

        onView(allOf(withId(R.id.remListTitle), withText(startsWith("Test")))).perform(click());
        onView(allOf(withId(R.id.remItemName), withText(startsWith("Test Reminder")))).perform(click());
    }
}
