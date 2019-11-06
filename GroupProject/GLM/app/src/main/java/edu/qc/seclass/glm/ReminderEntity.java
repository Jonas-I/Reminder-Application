package edu.qc.seclass.glm;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ReminderEntity {

    @PrimaryKey
    public int reminderID;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "type")
    public ReminderType type;

    @ColumnInfo(name = "is_checked")
    public boolean isChecked;

    @ColumnInfo(name = "alert")
    public Alert alert;

}