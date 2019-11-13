package edu.qc.seclass.glm;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    @ColumnInfo(name = "word")
    private String mWord;

    @ColumnInfo(name = "reminder_type")
    private String type;

    public Reminder(@NonNull String word, String type) {
        reminderID = UUID.randomUUID().toString();
        this.mWord = word;
        this.type = type;
    }

    @NonNull
    public String getWord() {
        return this.mWord;
    }

    public void setmWord(String mWord) {
        this.mWord = mWord;
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
        out.writeString(mWord);
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
        mWord = in.readString();
        type = in.readString();
    }
}
