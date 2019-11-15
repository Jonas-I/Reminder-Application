package edu.qc.seclass.glm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    Button createButton;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<ReminderList> listDataHeader;
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

        if (!areNotificationsEnabled)
            enableNotifications();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE)+2);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE)+2);
        Intent intent1 = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


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

    private void enableNotifications () {
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
}
