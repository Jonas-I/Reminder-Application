package edu.qc.seclass.glm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

/*
        TODO:   (Jonas)
                Search Bar                                  [ ]
                    - Pad searching from displaying @ end    [X]
                    - Implementation                         [ ]
                    - Visual                                 [X]
                Dropdown Menu for Types                     [ ]
                    - Implementation                         [ ]
                    - Visual                                 [ ]
                Glitchy Checkbox Visual Bug                 [ ] (?) May be due to process of retrieving information in childView (?)
                Set Icon for Notifications                  [X]

         Misc:  Default Reminder List
                Delete List if no Reminder Lists Exist
                Default Reminder Types
 */
public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    Button createButton;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private ArrayList<ReminderList> listDataHeader;
    static ReminderRoomDatabase db;
    private NotificationManagerCompat mNotificationManagerCompat;
    private RelativeLayout mMainRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainRelativeLayout = (RelativeLayout) findViewById(R.id.mainRelativeLayout);
        db = ReminderRoomDatabase.getDatabase(getApplicationContext());
        listView = (ExpandableListView) findViewById(R.id.ExpandLV);
        displayLists();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, MainActivity.this);
        listView.setAdapter(listAdapter);
        mNotificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        boolean areNotificationsEnabled = mNotificationManagerCompat.areNotificationsEnabled();
        //DialogFragment timePicker = new TimePickerFragment();
        //timePicker.show(getSupportFragmentManager(), "time picker");

//        EditText searchInput = (EditText) findViewById(R.id.searchInput);
//
//        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (event.getAction() == KeyEvent.KEYCODE_ENTER) {
//                    listAdapter.filter(v.getText().toString());
//                    return true;
//                }
//                return false;
//            }
//        });
        SearchManager sm = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView search = (SearchView) findViewById(R.id.searchInput);
        search.setSearchableInfo(sm.getSearchableInfo(getComponentName()));
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listAdapter.filter(query);
                expandAll();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listAdapter.filter(newText);
                expandAll();
                return false;
            }
        });
        search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                listAdapter.filter("");
                return false;
            }
        });

        if (!areNotificationsEnabled) {
            // Because the user took an action to create a notification, we create a prompt to let
            // the user re-enable notifications for this application again.
            Snackbar snackbar = Snackbar
                    .make(
                            mMainRelativeLayout,
                            "You need to enable notifications for this app",
                            Snackbar.LENGTH_LONG)
                    .setAction("ENABLE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Links to this app's notification settings
                            openNotificationSettingsForApp();
                        }
                    });
            snackbar.show();
            return;
        }
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
        if (createButton != null)
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
            Alert testAlert = new Alert(new Date(System.currentTimeMillis() + 10000));
            db.alertDao().insert(testAlert);
            Reminder reminder = new Reminder(descString, type.getType(),testAlert.getAlertID());
            db.reminderTypeDao().insert(type);
            db.reminderDao().insert(reminder);
            ReminderList newList = new ReminderList(type.getType());
            Calendar alertTime = Calendar.getInstance();
            alertTime.setTime(testAlert.getAlertTime());
            startAlarm(alertTime);
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

    private void openNotificationSettingsForApp() {
        // Links to this app's notification settings.
        Intent intent = new Intent();
        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
        //for Android 5-7
        intent.putExtra("app_package", getPackageName());
        intent.putExtra("app_uid", getApplicationInfo().uid);
        // for Android 8 and above
        intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
        startActivity(intent);
    }

    public void expandAll() {
        int s = listAdapter.getGroupCount();
        for (int i = 0; i < s; i++) {
            listView.expandGroup(i);
        }
    }

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        startAlarm(c);
    }
}
