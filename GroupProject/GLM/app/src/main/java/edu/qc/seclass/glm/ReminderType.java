package edu.qc.seclass.glm;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "type_table")
public class ReminderType {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "type")
    private String type;

    public ReminderType(@NonNull String type) {
        this.type = type;
    }

    @NonNull
    public String getType() {
        return this.type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

}
