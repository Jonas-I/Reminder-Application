package edu.qc.seclass.glm;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ReminderEntity {
    /*
    private String description;
    private ReminderType type;
    private boolean isChecked = false;
    private Alert alert;
    private static int reminderCounter = 0;
    private final int reminderID;*/

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;
}