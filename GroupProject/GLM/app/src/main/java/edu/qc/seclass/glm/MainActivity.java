package edu.qc.seclass.glm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<ReminderList> listDataHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ExpandableListView) findViewById(R.id.ExpandLV);
        displayLists();
        listAdapter = new ExpandableListAdapter(this, listDataHeader);
        listView.setAdapter(listAdapter);

//        findViewById(R.id.createButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                displayMenu(MainActivity.this);
//            }
//        });

    }

    // Displays the list to the main activity screen
    private void displayLists() {
        listDataHeader = new ArrayList<>();

        ReminderType defaultType = new ReminderType("Reminders");
        ReminderList defaultList = new ReminderList("Reminders",defaultType);
        defaultList.addReminder(new Reminder("Buy Groceries",defaultType));
        listDataHeader.add(defaultList);

        ReminderType hw = new ReminderType("Homework");
        ReminderList homework = new ReminderList("Reminders",hw);
        homework.addReminder(new Reminder("Finish 370 Project",hw));
        listDataHeader.add(homework);
    }

    // displayMenu displays the XML, create_menu, and does not require the need for a new activity
//    private void displayMenu(Context context) {
//        final Dialog dialog =  new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.create_menu);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(true);
//
//        Button createReminder = (Button) dialog.findViewById(R.id.createReminder);
//        Button createList = (Button) dialog.findViewById(R.id.createList);
//        Button cancel = (Button) dialog.findViewById(R.id.cancel);
//
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//    }
}
