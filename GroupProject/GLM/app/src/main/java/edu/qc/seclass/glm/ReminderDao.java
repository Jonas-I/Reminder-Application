package edu.qc.seclass.glm;

import android.arch.lifecycle.LiveData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ReminderDao {

    @Query("SELECT * FROM reminder_table")
    LiveData<List<ReminderEntity>> getAll();

    @Query("SELECT * FROM reminder_table WHERE reminder_id IN (:reminderIDs)")
    LiveData<List<ReminderEntity>> loadAllByIds(int[] reminderIDs);

    @Query("SELECT * FROM reminder_table WHERE description LIKE :desc LIMIT 1")
    Reminder findByName(String desc);

    @Insert
    void insertAll(ReminderEntity ... reminders);

    @Delete
    void delete(ReminderEntity reminder);

}