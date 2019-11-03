package edu.qc.seclass.glm;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ExpandableListView) findViewById(R.id.ExpandLV);
        displayLists();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listHash);
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
        // The
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();


        listDataHeader.add("Reminders");
        List<String> defaultList = new ArrayList<>();
        defaultList.add("Test");
        defaultList.add("Test2");
        listHash.put(listDataHeader.get(0), defaultList);

        listDataHeader.add("Title2");
        List<String> defaultList2 = new ArrayList<>();
        defaultList2.add("HelloWorld");
        listHash.put(listDataHeader.get(1),defaultList2);

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
