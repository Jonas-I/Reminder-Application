package edu.qc.seclass.glm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.Iterator;

import static org.junit.Assert.*;

public class ReminderListTest {

    private ReminderList reminderlist;

    @Before
    public void setUp() throws Exception {
        reminderlist = new ReminderList("Shopping");
    }

    @After
    public void tearDown() throws Exception {
        reminderlist = null;
    }

    @Test
    public void addReminderToASpecificList() {
        Reminder reminder1 = new Reminder("Doctor's Appointment", "Appointment");
        Reminder reminder2 = new Reminder("Checkup Appointment", "Appointment");
        Reminder reminder3 = new Reminder("Therapist's Appointment", "Appointment");
        reminderlist.add(reminder1);
        reminderlist.add(reminder2);
        reminderlist.add(reminder3);
        Iterator ri = reminderlist.iterator();
        assertEquals(reminder3 ,ri.next());
        assertEquals(reminder2 ,ri.next());
        assertEquals(reminder1 ,ri.next());

    }
}