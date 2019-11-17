package edu.qc.seclass.glm;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "alert_table")
public class Alert implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "alert_id")
    private String alertID;

    @ColumnInfo(name = "time")
    private Date alertTime;

    @ColumnInfo(name = "repeat")
    private String repeat;

    public Alert(Date alertTime, String repeat) {
        alertID = UUID.randomUUID().toString();
        this.alertTime = alertTime;
        this.repeat = repeat;
    }

    @Ignore
    public Alert(Date alertTime) {
        alertID = UUID.randomUUID().toString();
        this.alertTime = alertTime;
        this.repeat = "NEVER";
    }

    @NonNull
    public String getAlertID() {
        return alertID;
    }

    public void setAlertID(@NonNull String alertID) {
        this.alertID = alertID;
    }

    public Date getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(Date alertTime) {
        this.alertTime = alertTime;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }
}
