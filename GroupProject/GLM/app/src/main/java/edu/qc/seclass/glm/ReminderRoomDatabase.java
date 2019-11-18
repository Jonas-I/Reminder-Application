package edu.qc.seclass.glm;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */

@Database(entities = {Reminder.class, ReminderType.class, Alert.class}, version = 2)
@TypeConverters({DateConverter.class})
public abstract class ReminderRoomDatabase extends RoomDatabase {

    public abstract ReminderDao reminderDao();
    public abstract ReminderTypeDao reminderTypeDao();
    public abstract AlertDao alertDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile ReminderRoomDatabase INSTANCE;

    static ReminderRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ReminderRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ReminderRoomDatabase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     *
     * If you want to populate the database only when the database is created for the 1st timeBtn,
     * override RoomDatabase.Callback()#onCreate
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ReminderDao mDao;

        PopulateDbAsync(ReminderRoomDatabase db) {
            mDao = db.reminderDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // if database is empty you can prepopulate with default values here
            return null;
        }
    }

}
