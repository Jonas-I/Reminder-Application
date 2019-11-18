package edu.qc.seclass.glm;

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

    @Query("UPDATE reminder_table SET is_checked = :checked WHERE reminder_id = :id")
    void setChecked(String id, boolean checked);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Reminder reminder);

    @Query("DELETE FROM reminder_table")
    void deleteAll();

    @Query("DELETE FROM reminder_table WHERE reminder_id = :id")
    void deleteReminderbyID(String id);

}
