package edu.qc.seclass.glm;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ReminderTypeDaoTest {

    private ReminderRoomDatabase db;

    @Before
    public void initDb() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                ReminderRoomDatabase.class).build();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void getTypesReturnsAlphabetizedTypesAndChecksInserts() {
        ReminderType reminderType1 = new ReminderType("Appointment");
        ReminderType reminderType2 = new ReminderType("Shopping");
        ReminderType reminderType3 = new ReminderType("Gaming");
        ReminderType reminderType4 = new ReminderType("Homework");

        db.reminderTypeDao().insert(reminderType1);
        db.reminderTypeDao().insert(reminderType2);
        db.reminderTypeDao().insert(reminderType3);
        db.reminderTypeDao().insert(reminderType4);

        List<ReminderType> rt = db.reminderTypeDao().getAlphabetizedTypes();
        assertFalse(rt.isEmpty());

        assertEquals("Appointment", rt.get(0).getType());
        assertEquals("Gaming", rt.get(1).getType());
        assertEquals("Homework", rt.get(2).getType());
        assertEquals("Shopping", rt.get(3).getType());
    }

    @Test
    public void deleteAllTypesClearsTypes() {
        ReminderType reminderType1 = new ReminderType("Appointment");
        ReminderType reminderType2 = new ReminderType("Shopping");
        ReminderType reminderType3 = new ReminderType("Gaming");
        ReminderType reminderType4 = new ReminderType("Homework");

        db.reminderTypeDao().insert(reminderType1);
        db.reminderTypeDao().insert(reminderType2);
        db.reminderTypeDao().insert(reminderType3);
        db.reminderTypeDao().insert(reminderType4);

        db.reminderTypeDao().deleteAll();

        List<ReminderType> rt = db.reminderTypeDao().getAlphabetizedTypes();
        assertTrue(rt.isEmpty());
    }

}