package edu.qc.seclass.glm;

import java.util.ArrayList;

public class ReminderList extends ArrayList<Reminder>{

    private String type;
    ArrayList<Reminder> reminderList = new ArrayList<>();

    public ReminderList (String type) {
        super();
        this.type = type;
    }

    public ReminderList(String type, ArrayList<Reminder> reminderList){
        super();
        this.type = type;
        this.reminderList = reminderList;
    }

    public static void main(String[] args) {
        ReminderList testList = new ReminderList("Type");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Reminder> getReminderList() {
        return reminderList;
    }

    public void setReminderList(ArrayList<Reminder> reminderList) {
        this.reminderList = reminderList;
    }

    @Override
    public boolean equals (Object other) {
        return this.type.equalsIgnoreCase(((ReminderList)other).getType());
    }

    @Override
    public boolean add (Reminder other) {
        super.add(0,other);
        return true;
    }


}
