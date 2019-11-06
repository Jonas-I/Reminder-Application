/*
import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import edu.qc.seclass.glm.ReminderDao;
import edu.qc.seclass.glm.ReminderDatabase;
import edu.qc.seclass.glm.ReminderEntity;

i*mport android.content.Context;

/package edu.qc.seclass.glm;

@RunWith(AndroidJUnit4.class)
public class DBTest {

    private ReminderDao userDao;
    private ReminderDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, ReminderDatabase.class).build();
        userDao = db.reminderDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        ReminderEntity user = ;
        user.setName("george");
        userDao.insert(user);
        List<User> byName = userDao.findUsersByName("george");
        assertThat(byName.get(0), equalTo(user));
    }
}*/