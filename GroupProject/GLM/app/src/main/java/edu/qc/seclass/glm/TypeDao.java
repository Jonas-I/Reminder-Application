package edu.qc.seclass.glm;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface TypeDao {

    @Query("SELECT * FROM type_table")
    List<TypeEntity> getAll();

    @Query("SELECT * FROM type_table WHERE type_id IN (:typeIds)")
    List<TypeEntity> loadAllByIds(int[] typeIds);

    @Query("SELECT * FROM type_table WHERE type LIKE :type LIMIT 1")
    TypeEntity findByName(String type);

    @Insert
    void insertAll(TypeEntity ... typeEntities);

    @Delete
    void delete(TypeEntity type);

}