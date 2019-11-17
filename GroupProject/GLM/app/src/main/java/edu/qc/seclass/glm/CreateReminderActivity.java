package edu.qc.seclass.glm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateReminderActivity extends Activity {

    EditText desc, type;
    String descString, typeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_reminder);
        type = findViewById(R.id.inputType);
        findViewById(R.id.createReminderCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0);
                type.setEnabled(true);
                finish();
            }
        });
        int request = getIntent().getIntExtra("REQUEST",1);
        if (request == 3)
            populateFields();
        findViewById(R.id.createReminderDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidReminder()) {
                    Intent intent=new Intent();
                    intent.putExtra("DESCRIPTION",descString);
                    intent.putExtra("TYPE",typeString);
                    setResult(RESULT_OK,intent);
                    type.setEnabled(true);
                    finish();//finishing activity
                }
                else showToast("Please enter values for Description and Type");
            }
        });



        // Time and Date
        final Button time = (Button) findViewById(R.id.inputTimeButton);
        final Button date = (Button) findViewById(R.id.inputDateButton);
        final Button cancel = (Button) findViewById(R.id.createReminderCancel);
        final Button done = (Button) findViewById(R.id.createReminderDone);
        final Button timeCancel= (Button) findViewById(R.id.timeCancel);
        final Button timeDone = (Button) findViewById(R.id.timeDone);
        final Button dateCancel= (Button) findViewById(R.id.dateCancel);
        final Button dateDone = (Button) findViewById(R.id.dateDone);
        final View dtBorderListView = (View) findViewById(R.id.dtBorderListView);
        final View dtOverlay = (View) findViewById(R.id.dateTimeOverlay);
        final TimePicker timePicker = findViewById(R.id.inputTime);
        final DatePicker datePicker = findViewById(R.id.inputDate);

        // --TIME-- Clicking on the Time Button opens up the Time Picker

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dtOverlay.setVisibility(View.VISIBLE);
                dtBorderListView.setVisibility(View.VISIBLE);
                timePicker.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.GONE);
                done.setVisibility(View.GONE);
                timeDone.setVisibility(View.VISIBLE);
                timeCancel.setVisibility(View.VISIBLE);
                date.setClickable(false);
                time.setClickable(false);



            }
        });

        // Clicking "Set Time" should save the time and set a notification.
        timeDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dtOverlay.setVisibility(View.GONE);
                dtBorderListView.setVisibility(View.GONE);
                timePicker.setVisibility(View.GONE);
                datePicker.setVisibility(View.GONE);
                cancel.setVisibility(View.VISIBLE);
                done.setVisibility(View.VISIBLE);
                timeDone.setVisibility(View.GONE);
                timeCancel.setVisibility(View.GONE);
                date.setClickable(true);
                time.setClickable(true);
            }
        });

        // Clicking Cancel should not save the time and disregard a notification. (Similar implementation for Date)
        timeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dtOverlay.setVisibility(View.GONE);
                dtBorderListView.setVisibility(View.GONE);
                datePicker.setVisibility(View.GONE);
                timePicker.setVisibility(View.GONE);
                cancel.setVisibility(View.VISIBLE);
                done.setVisibility(View.VISIBLE);
                timeDone.setVisibility(View.GONE);
                timeCancel.setVisibility(View.GONE);
                date.setClickable(true);
                time.setClickable(true);
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dtOverlay.setVisibility(View.VISIBLE);
                dtBorderListView.setVisibility(View.VISIBLE);
                datePicker.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.GONE);
                done.setVisibility(View.GONE);
                dateDone.setVisibility(View.VISIBLE);
                dateCancel.setVisibility(View.VISIBLE);
                date.setClickable(false);
                time.setClickable(false);
            }
        });

        dateDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dtOverlay.setVisibility(View.GONE);
                dtBorderListView.setVisibility(View.GONE);
                timePicker.setVisibility(View.GONE);
                datePicker.setVisibility(View.GONE);
                cancel.setVisibility(View.VISIBLE);
                done.setVisibility(View.VISIBLE);
                dateDone.setVisibility(View.GONE);
                dateCancel.setVisibility(View.GONE);
                date.setClickable(true);
                time.setClickable(true);

            }
        });

        dateCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dtOverlay.setVisibility(View.GONE);
                dtBorderListView.setVisibility(View.GONE);
                datePicker.setVisibility(View.GONE);
                timePicker.setVisibility(View.GONE);
                cancel.setVisibility(View.VISIBLE);
                done.setVisibility(View.VISIBLE);
                dateDone.setVisibility(View.GONE);
                dateCancel.setVisibility(View.GONE);
                date.setClickable(true);
                time.setClickable(true);

            }
        });
    }

    private void showToast (String message) {
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void populateFields () {
        String selectedType = getIntent().getStringExtra("TYPE");
        type.setText(selectedType);
        type.setEnabled(false);
    }

    public boolean isValidReminder () {
        desc = findViewById(R.id.inputDescription);
        descString = desc.getText().toString();
        type = findViewById(R.id.inputType);
        typeString = type.getText().toString();
        return !(descString.equals("") || typeString.equals(""));
    }

}
