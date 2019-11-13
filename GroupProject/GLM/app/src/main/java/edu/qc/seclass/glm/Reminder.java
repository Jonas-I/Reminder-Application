package edu.qc.seclass.glm;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "reminder_table",
        foreignKeys = @ForeignKey(entity = ReminderType.class,
                parentColumns = "type",
                childColumns = "reminder_type",
                onDelete = ForeignKey.CASCADE))
public class Reminder implements Parcelable {

    @PrimaryKey
    @NonNull
    @ColumnInfo (name = "reminder_id")
    private String reminderID;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "reminder_type")
    private String type;

    public Reminder(@NonNull String description, String type) {
        reminderID = UUID.randomUUID().toString();
        this.description = description;
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReminderID() {
        return reminderID;
    }

    public void setReminderID(String reminderID) {
        this.reminderID = reminderID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(reminderID);
        out.writeString(description);
        out.writeString(type);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Reminder> CREATOR = new Parcelable.Creator<Reminder>() {
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Reminder(Parcel in) {
        reminderID = in.readString();
        description = in.readString();
        type = in.readString();
    }
}
