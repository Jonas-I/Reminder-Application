package edu.qc.seclass.glm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
