package edu.qc.seclass.glm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
}
