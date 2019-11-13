package edu.qc.seclass.glm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    Button createButton;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<ReminderList> listDataHeader;
    static ReminderRoomDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = ReminderRoomDatabase.getDatabase(getApplicationContext());
        listView = (ExpandableListView) findViewById(R.id.ExpandLV);
        displayLists();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, MainActivity.this);
        listView.setAdapter(listAdapter);

        createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createButton.setEnabled(false);
                Intent intent = new Intent(MainActivity.this, CreateReminderActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        createButton.setEnabled(true);
        ExpandableListAdapter.editButtonPressed = false;
        ExpandableListAdapter.createButtonPressed = false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE || requestCode == 3) && resultCode == RESULT_OK) {
            String descString = data.getStringExtra("DESCRIPTION");
            String typeString = data.getStringExtra("TYPE");
            ReminderType type = new ReminderType(typeString);
            Reminder reminder = new Reminder(descString, type.getType());
            db.reminderTypeDao().insert(type);
            db.reminderDao().insert(reminder);
            ReminderList newList = new ReminderList(type.getType());
            if (listDataHeader.contains(newList)) {
                int indexOfList = listDataHeader.indexOf(newList);
                listDataHeader.get(indexOfList).add(reminder);
                listAdapter.notifyDataSetChanged();
            }
            else {
                newList.add(reminder);
                listDataHeader.add(0,newList);
                listAdapter.notifyDataSetChanged();
            }
        }
        else if (requestCode == 2) {
            if (resultCode == 1) { // Return from Edit activity
                Reminder createdReminder = data.getParcelableExtra("NEW_REMINDER");
                int list = data.getIntExtra("LIST", 0);
                int child = data.getIntExtra("REMINDER", 0);
                String reminderID = createdReminder.getReminderID();
                String desc = createdReminder.getDescription();
                db.reminderDao().updateReminderDescription(reminderID, desc);
                listDataHeader.get(list).set(child, createdReminder);
                listAdapter.notifyDataSetChanged();
            }
        }
        else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    // Displays the list to the main activity screen by pulling reminders from the database
    private void displayLists() {
        listDataHeader = new ArrayList<>();
        List<ReminderType> typeList = db.reminderTypeDao().getAlphabetizedTypes();
        for (ReminderType type: typeList) {
            List<Reminder> list = db.reminderDao().getAllRemindersOfType(type.getType());
            ReminderList reminderList = new ReminderList(type.getType());
            for (Reminder r: list)
                reminderList.add(r);
            listDataHeader.add(reminderList);
        }
    }
}
