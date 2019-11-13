package edu.qc.seclass.glm;

import java.util.ArrayList;

public class ReminderList extends ArrayList<Reminder>{

    private String type;

    public ReminderList (String type) {
        super();
        this.type = type;
    }

    public static void main(String[] args) {
        ReminderList testList = new ReminderList("Type");
    }

    public String getType() {
        return type;
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
