package edu.qc.seclass.glm;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ReminderDao {

    @Query("SELECT * FROM ReminderEntity")
    List<ReminderEntity> getAll();

    @Query("SELECT * FROM ReminderEntity WHERE reminderID IN (:reminderIDs)")
    List<ReminderEntity> loadAllByIds(int[] reminderIDs);

    @Query("SELECT * FROM ReminderEntity WHERE description LIKE :desc LIMIT 1")
    Reminder findByName(String desc);

    @Insert
    void insertAll(ReminderEntity... reminders);

    @Delete
    void delete(ReminderEntity reminder);

}