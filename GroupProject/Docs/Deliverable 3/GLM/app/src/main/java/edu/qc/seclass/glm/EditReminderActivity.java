package edu.qc.seclass.glm;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditReminderActivity extends Activity {

    EditText desc, type;
    String descString, typeString, dateText = "", timeText = "";
    Calendar reminderDate;
    boolean dateSet = false, timeSet = false;
    Reminder selectedReminder;
    Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_reminder);
        selectedReminder = getIntent().getParcelableExtra("SELECTED_REMINDER");
        populateFields();
        findViewById(R.id.editReminderCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.editReminderDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidReminder()) {
                    selectedReminder.setDescription(descString);
                    // dont allow them to change the type
                    Intent intent=new Intent();
                    intent.putExtra("NEW_REMINDER",selectedReminder);
                    int list = getIntent().getIntExtra("LIST",0);
                    int child = getIntent().getIntExtra("REMINDER",0);
                    intent.putExtra("LIST", list);
                    intent.putExtra("REMINDER", child);
                    setResult(1,intent);
                    finish();//finishing activity
                }
                else showToast("Please enter values for Description and Type");
            }
        });

        reminderDate = Calendar.getInstance();
        int hour = reminderDate.get(Calendar.HOUR_OF_DAY);
        int minute = reminderDate.get(Calendar.MINUTE);

        final Button time = (Button) findViewById(R.id.editInputTimeButton);

        final TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timeSet = true;
                String am_pm = "";
                reminderDate.set(Calendar.HOUR_OF_DAY, selectedHour);
                reminderDate.set(Calendar.MINUTE, selectedMinute);
                if (reminderDate.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = "AM";
                else if (reminderDate.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = "PM";
                String strHrsToShow = (reminderDate.get(Calendar.HOUR) == 0) ?"12":reminderDate.get(Calendar.HOUR)+"";
                timeText = strHrsToShow + ":" + selectedMinute + " " + am_pm;
                time.setText(timeText);
            }
        }, hour, minute, false);

        // --TIME-- Clicking on the Time Button opens up the Time Picker
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimePicker.show();
            }
        });

        dateButton = (Button) findViewById(R.id.editInputDateButton);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                dateSet = true;
                reminderDate.set(Calendar.YEAR, year);
                reminderDate.set(Calendar.MONTH, monthOfYear);
                reminderDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditReminderActivity.this, date, reminderDate
                        .get(Calendar.YEAR), reminderDate.get(Calendar.MONTH),
                        reminderDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final Button repeatBtn = (Button) findViewById(R.id.editInputRepeatButton);
        repeatBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(EditReminderActivity.this, repeatBtn);
                popupMenu.getMenuInflater().inflate(R.menu.popup_repeat_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.Never:
                                repeatBtn.setText("Never");
                                return true;
                            case R.id.Once:
                                repeatBtn.setText("Once");
                                return true;
                            case R.id.Daily:
                                repeatBtn.setText("Daily");
                                return true;
                            case R.id.Weekly:
                                repeatBtn.setText("Weekly");
                                return true;
                            case R.id.Monthly:
                                repeatBtn.setText("Monthly");
                                return true;
                            case R.id.Yearly:
                                repeatBtn.setText("Yearly");
                                return true;
                        }
                        return true;
                    }
                });

                popupMenu.show();
            }
        });
    }

    private void populateFields () {
        desc = findViewById(R.id.editInputDescription);
        type = findViewById(R.id.editInputType);
        desc.setText(selectedReminder.getDescription());
        type.setText(selectedReminder.getType());
    }

    public boolean isValidReminder () {
        descString = desc.getText().toString();
        typeString = type.getText().toString();
        return !(descString.equals("") || typeString.equals(""));
    }

    private void showToast (String message) {
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void updateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("E, MMM dd yyyy");
        dateText = sdf.format(reminderDate.getTime());
        dateButton.setText(dateText);
    }

}
