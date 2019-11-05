package edu.qc.seclass.glm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

        findViewById(R.id.createButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.overlay).setVisibility(View.VISIBLE);
                findViewById(R.id.createCenterView).setVisibility(View.VISIBLE);
                findViewById(R.id.createReminder).setVisibility(View.VISIBLE);
                findViewById(R.id.createList).setVisibility(View.VISIBLE);
                findViewById(R.id.cancel).setVisibility(View.VISIBLE);

                Button cancel = (Button) findViewById(R.id.cancel);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findViewById(R.id.overlay).setVisibility(View.GONE);
                        findViewById(R.id.createCenterView).setVisibility(View.GONE);
                        findViewById(R.id.createReminder).setVisibility(View.GONE);
                        findViewById(R.id.createList).setVisibility(View.GONE);
                        findViewById(R.id.cancel).setVisibility(View.GONE);
                    }
                });
            }
        });

        findViewById(R.id.createReminder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Clicked");
                Intent intent = new Intent(MainActivity.this, CreateReminderActivity.class);
                startActivity(intent);
            }
        });

    }


    // Displays the list to the main activity screen
    private void displayLists() {
        listDataHeader = new ArrayList<>();

        ReminderType defaultType = new ReminderType("Reminders");
        ReminderList defaultList = new ReminderList("Reminders",defaultType);
        defaultList.addReminder(new Reminder("Buy Groceries",defaultType));
        defaultList.addReminder(new Reminder("Apply to jobs",defaultType));
        listDataHeader.add(defaultList);

        ReminderType hw = new ReminderType("Homework");
        ReminderList homework = new ReminderList("Reminders",hw);
        homework.addReminder(new Reminder("Finish 370 Project",hw));
        homework.addReminder(new Reminder("Finish 316 Project",hw));
        listDataHeader.add(homework);

    }


}
