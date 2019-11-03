package edu.qc.seclass.glm;

public class Reminder implements Comparable{

    private String description;
    private ReminderType type;
    private boolean isChecked = false;
    private Alert alert;
    private static int reminderCounter = 0;
    private final int reminderID;

    public Reminder(String description, ReminderType type) {
        this.description = description;
        this.type = type;
        alert = null;
        reminderID = reminderCounter++;
        System.out.println(reminderID + " " + reminderCounter);
    }


    public Reminder(String description, ReminderType type, Alert alert) {
        this.description = description;
        this.type = type;
        this.alert = alert;
        reminderID = reminderCounter++;
        System.out.println(reminderID + " " + reminderCounter);
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

    @Override
    public int compareTo(Object o) {
        return this.description.compareToIgnoreCase(((Reminder)o).description);
    }
}
