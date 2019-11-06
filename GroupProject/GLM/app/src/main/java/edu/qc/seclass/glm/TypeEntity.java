package edu.qc.seclass.glm;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "type_table")
public class TypeEntity {

    @PrimaryKey (autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "type_id")
    public int typeID;

    @ColumnInfo(name = "type")
    public String type;

    public TypeEntity (String type) {
        this.type = type;
    }


}