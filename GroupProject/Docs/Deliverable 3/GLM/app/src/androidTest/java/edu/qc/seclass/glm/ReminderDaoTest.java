package edu.qc.seclass.glm;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ReminderDaoTest {

    private ReminderRoomDatabase db;

    @Before
    public void initDb() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                ReminderRoomDatabase.class).build();
        ReminderType reminderType = new ReminderType("Appointment");
        db.reminderTypeDao().insert(reminderType);
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertReminderDaoSaves() {
        Reminder reminder = new Reminder("Doctor's Appointment", "Appointment");
        db.reminderDao().insert(reminder);
        List<Reminder> reminders = db.reminderDao().getAlphabetizedReminders();
        assertFalse(reminders.isEmpty());
    }

    @Test
    public void insertReminderDaoSavesInvalidType() {
        Reminder reminder = new Reminder("Doctor's Appointment", "Shopping");
        try {
            db.reminderDao().insert(reminder);
        } catch (Exception e) {}
        List<Reminder> reminders = db.reminderDao().getAlphabetizedReminders();
        assertTrue(reminders.isEmpty());
    }

    @Test
    public void getAllRemindersRetrievesData() {
        Reminder reminder1 = new Reminder("Doctor's Appointment", "Appointment");
        Reminder reminder2 = new Reminder("Dentist's Appointment", "Appointment");
        Reminder reminder3 = new Reminder("Eye Doctor's Appointment", "Appointment");
        Reminder reminder4 = new Reminder("Regular Checkup", "Appointment");
        Reminder reminder5 = new Reminder("Bone Repair Surgery", "Appointment");

        ReminderList reminderList = new ReminderList("Appointments");
        reminderList.add(reminder1);
        reminderList.add(reminder2);
        reminderList.add(reminder3);
        reminderList.add(reminder4);
        reminderList.add(reminder5);

        db.reminderDao().insert(reminder1);
        db.reminderDao().insert(reminder2);
        db.reminderDao().insert(reminder3);
        db.reminderDao().insert(reminder4);
        db.reminderDao().insert(reminder5);

        List<Reminder> reminders = db.reminderDao().getAlphabetizedReminders();

        assertEquals(5,reminders.size());
    }

    @Test
    public void deleteAllReminders() {
        Reminder reminder = new Reminder("Doctor's Appointment", "Appointment");
        db.reminderDao().insert(reminder);

        db.reminderDao().deleteAll();
        assert(db.reminderDao().getAlphabetizedReminders().isEmpty());
    }

    @Test
    public void updateReminderDescChangesData() {
        Reminder reminder = new Reminder("Doctor's Appointment", "Appointment");
        db.reminderDao().insert(reminder);

        db.reminderDao().updateReminderDescription(reminder.getReminderID(), "Dentist's Appointment");
        assertEquals("Dentist's Appointment", db.reminderDao().getReminderByID(reminder.getReminderID()).getDescription());
    }

    @Test
    public void setCheckedUpdatesChecked() {
        Reminder reminder = new Reminder("Doctor's Appointment", "Appointment");
        db.reminderDao().insert(reminder);

        db.reminderDao().setChecked(reminder.getReminderID(), true);
        assertEquals(true, db.reminderDao().getReminderByID(reminder.getReminderID()).isChecked());
    }

    @Test
    public void setCheckedUpdatesCheckedFails() {
        Reminder reminder = new Reminder("Doctor's Appointment", "Appointment");
        db.reminderDao().insert(reminder);

        db.reminderDao().setChecked(reminder.getReminderID(), true);
        assertFalse(!db.reminderDao().getReminderByID(reminder.getReminderID()).isChecked());
    }

    @Test
    public void getAllRemindersOfTypeReturnsRightTypes() {
        ReminderType reminderType = new ReminderType("Shopping");
        db.reminderTypeDao().insert(reminderType);

        Reminder reminder1 = new Reminder("Doctor's Appointment", "Appointment");
        Reminder reminder2 = new Reminder("Dentist's Appointment", "Appointment");
        Reminder reminder3 = new Reminder("Eye Doctor's Appointment", "Appointment");
        Reminder reminder4 = new Reminder("Grocery Shopping", "Shopping");
        Reminder reminder5 = new Reminder("Food Shopping for Date with Ashley", "Shopping");

        ReminderList reminderList = new ReminderList("Appointments");
        reminderList.add(reminder1);
        reminderList.add(reminder2);
        reminderList.add(reminder3);

        ReminderList reminderListS = new ReminderList("Shopping");
        reminderListS.add(reminder4);
        reminderListS.add(reminder5);

        db.reminderDao().insert(reminder1);
        db.reminderDao().insert(reminder2);
        db.reminderDao().insert(reminder3);
        db.reminderDao().insert(reminder4);
        db.reminderDao().insert(reminder5);

        List<Reminder> reminders1 = db.reminderDao().getAllRemindersOfType("Appointment");
        List<Reminder> reminders2 = db.reminderDao().getAllRemindersOfType("Shopping");

        assertEquals(3, reminders1.size());
        assertEquals(2, reminders2.size());
    }

    @Test
    public void deleteSingleReminderByID() {
        Reminder reminder1 = new Reminder("Doctor's Appointment", "Appointment");
        Reminder reminder2 = new Reminder("Dentist's Appointment", "Appointment");
        Reminder reminder3 = new Reminder("Eye Doctor's Appointment", "Appointment");
        Reminder reminder4 = new Reminder("Regular Checkup", "Appointment");
        Reminder reminder5 = new Reminder("Bone Repair Surgery", "Appointment");

        ReminderList reminderList = new ReminderList("Appointments");
        reminderList.add(reminder1);
        reminderList.add(reminder2);
        reminderList.add(reminder3);
        reminderList.add(reminder4);
        reminderList.add(reminder5);

        db.reminderDao().insert(reminder1);
        db.reminderDao().insert(reminder2);
        db.reminderDao().insert(reminder3);
        db.reminderDao().insert(reminder4);
        db.reminderDao().insert(reminder5);

        db.reminderDao().deleteReminderbyID(reminder4.getReminderID());

        List<Reminder> reminders = db.reminderDao().getAllRemindersOfType("Appointment");

        assertEquals(4,reminders.size());
    }

}