package edu.qc.seclass.glm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ReminderTest {

    private Reminder reminder;

    @Before
    public void setUp() throws Exception {
        reminder = new Reminder("Doctor's Appointment", "Appointment");
    }

    @After
    public void tearDown() throws Exception {
        reminder = null;
    }

    @Test
    public void getDescription() {
        assertEquals("Doctor's Appointment", reminder.getDescription());
    }

    @Test
    public void setDescription() {
        reminder.setDescription("Grocery Shopping");
        assertEquals("Grocery Shopping", reminder.getDescription());
    }

    @Test
    public void getType() {
        assertEquals("Appointment", reminder.getType());
    }

    @Test
    public void setType() {
        reminder.setType("Email");
        assertEquals("Email", reminder.getType());
    }

    @Test
    public void isChecked() {
        assertEquals(false, reminder.isChecked());
    }

    @Test
    public void isCheckedAndSetChecked() {
        reminder.setChecked(true);
        assertEquals(true, reminder.isChecked());
    }
}