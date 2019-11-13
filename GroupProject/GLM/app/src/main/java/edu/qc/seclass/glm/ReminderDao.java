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

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ReminderDao {

    @Query("SELECT * from reminder_table ORDER BY description ASC")
    List<Reminder> getAlphabetizedReminders();

    @Query("SELECT * from reminder_table WHERE reminder_type LIKE :type")
    List<Reminder> getAllRemindersOfType(String type);

    @Query("SELECT * from reminder_table WHERE reminder_id LIKE :ID")
    Reminder getReminderByID (String ID);

    @Query("UPDATE reminder_table SET description = :desc WHERE reminder_id = :id")
    void updateReminderDescription(String id, String desc);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Reminder reminder);

    @Query("DELETE FROM reminder_table")
    void deleteAll();

    @Query("DELETE FROM reminder_table WHERE reminder_id = :id")
    void deleteReminderbyID(String id);

}
