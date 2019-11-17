package edu.qc.seclass.glm;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface AlertDao {

    @Query("SELECT * from alert_table WHERE alert_id LIKE :ID")
    Alert getAlertByID (String ID);

    @Query("UPDATE alert_table SET repeat = :repeat WHERE alert_id = :id")
    void setRepeat(String id, String repeat);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Alert alert);

    @Query("DELETE FROM alert_table")
    void deleteAll();

}
