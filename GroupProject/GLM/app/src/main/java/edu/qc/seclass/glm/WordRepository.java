package edu.qc.seclass.glm;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */

class WordRepository {

    private ReminderDao mReminderDao;
    private ReminderTypeDao mReminderTypeDao;
    private List<Reminder> mAllWords;
    private LiveData<List<Reminder>> allRemindersByType;
    private List<ReminderType> allTypes;
    static List<Reminder>remindersOfType;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    WordRepository(Application application) {
        ReminderRoomDatabase db = ReminderRoomDatabase.getDatabase(application);
        mReminderDao = db.reminderDao();
        mReminderTypeDao = db.reminderTypeDao();
        mAllWords = mReminderDao.getAlphabetizedWords();
        allTypes = mReminderTypeDao.getAlphabetizedTypes();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    List<Reminder> getAllWords() {
        return mAllWords;
    }

    List<ReminderType> getAllTypes() {
        return allTypes;
    }

    List<Reminder> getAllRemindersOfType (ReminderType type) {
        new getReminders(mReminderDao).execute(type);
        return remindersOfType;
    }


    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    void insert(Reminder reminder) {
        new insertReminderAsyncTask(mReminderDao).execute(reminder);
    }

    void insert(ReminderType reminderType) {
        new insertTypeAsyncTask(mReminderTypeDao).execute(reminderType);
    }

    private static class insertReminderAsyncTask extends AsyncTask<Reminder, Void, Void> {

        private ReminderDao mAsyncTaskDao;

        insertReminderAsyncTask(ReminderDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Reminder... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class getReminders extends AsyncTask<ReminderType, Void, Void> {

        private ReminderDao mAsyncTaskDao;
        static List<Reminder> reminders;

        getReminders(ReminderDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ReminderType ... params) {
            WordRepository.remindersOfType = mAsyncTaskDao.getAllRemindersOfType(params[0].getType());
            return null;
        }

        public static List<Reminder> reminders () {
            return reminders;
        }
    }

    private static class insertTypeAsyncTask extends AsyncTask<ReminderType, Void, Void> {

        private ReminderTypeDao mAsyncTaskDao;

        insertTypeAsyncTask(ReminderTypeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ReminderType ... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
