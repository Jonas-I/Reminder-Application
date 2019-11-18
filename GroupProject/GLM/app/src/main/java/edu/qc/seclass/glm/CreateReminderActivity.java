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
import java.util.Date;

public class CreateReminderActivity extends Activity {

    EditText desc, type;
    String descString, typeString, dateText = "", timeText = "";
    Calendar reminderDate;
    boolean dateSet = false, timeSet = false;
    Button dateBtn, repeatBtn, timeBtn;
    Reminder selectedReminder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_reminder);
        type = findViewById(R.id.inputType);
        desc = findViewById(R.id.inputDescription);
        repeatBtn = (Button) findViewById(R.id.inputRepeatButton);
        dateBtn = (Button) findViewById(R.id.inputDateButton);
        timeBtn = (Button) findViewById(R.id.inputTimeButton);
        findViewById(R.id.createReminderCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0);
                type.setEnabled(true);
                finish();
            }
        });
        int request = getIntent().getIntExtra("REQUEST_CODE",1);
        if (request == 2) {
            selectedReminder = getIntent().getParcelableExtra("SELECTED_REMINDER");
            populateFields();
        }
        else if (request == 3) populateFieldsForAddToList();
        findViewById(R.id.createReminderDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidReminder()) {
                    Intent intent=new Intent();
                    intent.putExtra("DESCRIPTION",descString);
                    intent.putExtra("TYPE",typeString);
                    if (dateSet && timeSet)
                        intent.putExtra("REMINDER_CALENDAR", reminderDate);
                    else intent.putExtra("REMINDER_CALENDAR",(Calendar)null);
                    setResult(RESULT_OK,intent);
                    type.setEnabled(true);
                    //inputRepeatButton
                    Button repeatButton = findViewById(R.id.inputRepeatButton);
                    String repeat = repeatButton.getText().toString();
                    intent.putExtra("REPEAT", repeat);
                    finish();//finishing activity
                }
                else {
                    if (dateSet ^ timeSet) showToast("If you'd like to set an alert make sure you enter a timeBtn and a date");
                    else showToast("Please enter values for Description and Type");
                }
            }
        });

        reminderDate = Calendar.getInstance();
        int hour = reminderDate.get(Calendar.HOUR_OF_DAY);
        int minute = reminderDate.get(Calendar.MINUTE);

        final TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timeSet = true;
                editTimeLabel(selectedHour, selectedMinute);
            }
        }, hour, minute, false);

        // --TIME-- Clicking on the Time Button opens up the Time Picker
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimePicker.show();
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                dateSet = true;
                reminderDate.set(Calendar.YEAR, year);
                reminderDate.set(Calendar.MONTH, monthOfYear);
                reminderDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                editDateLabel(reminderDate.getTime());
            }

        };

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateReminderActivity.this, date, reminderDate
                        .get(Calendar.YEAR), reminderDate.get(Calendar.MONTH),
                        reminderDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        repeatBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(CreateReminderActivity.this, repeatBtn);
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

    private void showToast (String message) {
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void populateFields () {
        desc.setText(selectedReminder.getDescription());
        type.setText(selectedReminder.getType());
        String alertId = selectedReminder.getAlertID();
        if (alertId != null) {
            Alert alert = MainActivity.db.alertDao().getAlertByID(alertId);
            repeatBtn.setText(alert.getRepeat());
            Date alertDate = alert.getAlertTime();
            editDateLabel(alertDate);
            reminderDate = Calendar.getInstance();
            reminderDate.setTime(alertDate);
            editTimeLabel(reminderDate.get(Calendar.HOUR_OF_DAY),reminderDate.get(Calendar.MINUTE));
        }
    }

    public boolean isValidReminder () {
        descString = desc.getText().toString();
        type = findViewById(R.id.inputType);
        typeString = type.getText().toString();
        return !(descString.equals("") || typeString.equals(""))
                && !(dateSet ^ timeSet);
    }

    private void editDateLabel(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("E, MMM dd yyyy");
        dateText = sdf.format(date);
        dateBtn.setText(dateText);
    }

    private void editTimeLabel(int selectedHour, int selectedMinute) {
        String am_pm = "";
        reminderDate.set(Calendar.HOUR_OF_DAY, selectedHour);
        reminderDate.set(Calendar.MINUTE, selectedMinute);
        if (reminderDate.get(Calendar.AM_PM) == Calendar.AM)
            am_pm = "AM";
        else if (reminderDate.get(Calendar.AM_PM) == Calendar.PM)
            am_pm = "PM";
        String strHrsToShow = (reminderDate.get(Calendar.HOUR) == 0) ?"12":reminderDate.get(Calendar.HOUR)+"";
        timeText = strHrsToShow + ":" + selectedMinute + " " + am_pm;
        timeBtn.setText(timeText);
    }

    private void populateFieldsForAddToList () {
        String selectedType = getIntent().getStringExtra("TYPE");
        type.setText(selectedType);
        type.setEnabled(false);
    }

}
