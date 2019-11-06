package edu.qc.seclass.glm;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (tableName = "reminder_table",
        foreignKeys = {
            @ForeignKey(entity = TypeEntity.class,
                        parentColumns = "type_id",
                        childColumns = "type_id",
                        onDelete = ForeignKey.CASCADE
            )
        })
public class ReminderEntity {

    @PrimaryKey (autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "reminder_id")
    public int reminderID;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "type_id")
    public int type_id;

    @ColumnInfo(name = "is_checked")
    public boolean isChecked;
/*
    @ColumnInfo(name = "alert")
    public Alert alert;
*/
    public ReminderEntity  (String description, int type_id, boolean isChecked) {
        this.description = description;
        this.type_id = type_id;
        this.isChecked = isChecked();
    }

    public int getReminderID() {
        return reminderID;
    }

    public void setReminderID(int uid) {
        this.reminderID = uid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
/*
    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }*/
}