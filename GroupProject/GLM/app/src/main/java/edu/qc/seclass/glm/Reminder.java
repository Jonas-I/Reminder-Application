package edu.qc.seclass.glm;

public class Reminder {

    String description;
    ReminderType type;
    boolean isChecked;
    Alert alert;
    int reminderID;

    public Reminder(String description, ReminderType type) {
        this.description = description;
        this.type = type;
    }


    public Reminder(String description, ReminderType type, Alert alert) {
        this.description = description;
        this.type = type;
        this.alert = alert;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ReminderType getType() {
        return type;
    }

    public void setType(ReminderType type) {
        this.type = type;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public int getReminderID() {
        return reminderID;
    }

    public void setReminderID(int reminderID) {
        this.reminderID = reminderID;
    }
}
