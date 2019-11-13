package edu.qc.seclass.glm;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ReminderTypeDao {

    @Query("SELECT * from type_table ORDER BY type ASC")
    List<ReminderType> getAlphabetizedTypes();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ReminderType reminderType);

    @Query("DELETE FROM type_table")
    void deleteAll();

}
