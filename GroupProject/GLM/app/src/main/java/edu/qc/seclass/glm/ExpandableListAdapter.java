package edu.qc.seclass.glm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<ReminderList> listDataHeader;
    private ArrayList<ReminderList> original;
    Activity mainActivity;
    public static boolean editButtonPressed = false;
    public static boolean createButtonPressed = false;

    private final Set<Pair<Long, Long>> tagCheckedItems = new HashSet<>();

    public ExpandableListAdapter(Context context, ArrayList<ReminderList> listDataHeader, Activity mainActivity) {
        this.context = context;
        this.listDataHeader = new ArrayList<>();

        this.listDataHeader = listDataHeader;

        this.mainActivity = mainActivity;
        this.original = new ArrayList<>();
        this.original.addAll(listDataHeader);
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listDataHeader.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition).getType();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listDataHeader.get(groupPosition).get(childPosition).getDescription();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.reminder_list, null);
        }

//        final Button addReminderToListBtn = (Button) convertView.findViewById(R.id.createNewReminderButton);
//        addReminderToListBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (!createButtonPressed) {
//                    createButtonPressed = true;
//                    ReminderList selectedList = listDataHeader.get(groupPosition);
//                    Intent intent = new Intent(mainActivity, CreateReminderActivity.class);
//                    intent.putExtra("SELECTED_LIST", selectedList);
//                    intent.putExtra("LIST", groupPosition);
//                    intent.putExtra("TYPE",selectedList.getType());
//                    intent.putExtra("REQUEST", 3);
//                    mainActivity.startActivityForResult(intent,3);
//                }
//            }
//        });
        final CheckBox checkAll = convertView.findViewById(R.id.checkAllButton);
        checkAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean isChecked = checkAll.isChecked();
                ReminderList reminderList = listDataHeader.get(groupPosition);
                for (Reminder r: reminderList) {
                    String id = r.getReminderID();
                    MainActivity.db.reminderDao().setChecked(id, isChecked);
                    r.setChecked(isChecked);
                }
                notifyDataSetChanged();
            }
        });
        final Button listButton = (Button) convertView.findViewById(R.id.listButton);
        listButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mainActivity, listButton);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        ReminderList selectedList = listDataHeader.get(groupPosition);
                        switch (item.getItemId()) {
                            case R.id.pm_createReminder:
                                if (!createButtonPressed) {
                                    createButtonPressed = true;
                                    Intent intent = new Intent(mainActivity, CreateReminderActivity.class);
                                    intent.putExtra("SELECTED_LIST", selectedList);
                                    intent.putExtra("LIST", groupPosition);
                                    intent.putExtra("TYPE",selectedList.getType());
                                    intent.putExtra("REQUEST_CODE", 3);
//                                    Toast.makeText(mainActivity, "Creating new Reminder", Toast.LENGTH_SHORT).show();
                                    mainActivity.startActivityForResult(intent,3);
                                }
                                return true;
                            case R.id.pm_deleteList:
                                String listType = listDataHeader.remove(groupPosition).getType();
                                MainActivity.db.reminderTypeDao().deleteAllOfType(listType);
                                notifyDataSetChanged();
                                Toast.makeText(mainActivity, "List Deleted", Toast.LENGTH_SHORT).show();
                                return true;
                        }
                        return true;
                    }
                });

                popupMenu.show();
            }
        });
        TextView remListTitle = (TextView) convertView.findViewById(R.id.remListTitle);
        remListTitle.setTypeface(null, Typeface.BOLD);
        remListTitle.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.reminder_item, null);
        }
        CheckBox cbListChild = (CheckBox) convertView.findViewById(R.id.remItem);
        Reminder selectedReminder = listDataHeader.get(groupPosition).get(childPosition);
        Reminder reminderFromDB = MainActivity.db.reminderDao().getReminderByID(selectedReminder.getReminderID());
        selectedReminder.setChecked(reminderFromDB.isChecked());
        cbListChild.setChecked(selectedReminder.isChecked());
        cbListChild.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Reminder selectedReminder = listDataHeader.get(groupPosition).get(childPosition);
                selectedReminder.setChecked(((CheckBox)v).isChecked());
                MainActivity.db.reminderDao().setChecked(selectedReminder.getReminderID(),selectedReminder.isChecked());
            }
        });
        Button deleteBtn = (Button) convertView.findViewById(R.id.btnDelete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Reminder oldReminder = listDataHeader.get(groupPosition).remove(childPosition);
                MainActivity.db.reminderDao().deleteReminderbyID(oldReminder.getReminderID());
                notifyDataSetChanged();
            }
        });
        final Button editBtn = (Button) convertView.findViewById(R.id.btnEdit);
        editBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!editButtonPressed) {
                    editButtonPressed = true;
                    Reminder selectedReminder = listDataHeader.get(groupPosition).get(childPosition);
                    Intent intent = new Intent(mainActivity, CreateReminderActivity.class);
                    intent.putExtra("SELECTED_REMINDER", selectedReminder);
                    intent.putExtra("LIST", groupPosition);
                    intent.putExtra("REMINDER",childPosition);
                    intent.putExtra("REQUEST_CODE",2);
                    mainActivity.startActivityForResult(intent,2);
                }
            }
        });
        TextView tvListChild = (TextView) convertView.findViewById(R.id.remItemName);
        tvListChild.setText(childText);
        TextView alertText = convertView.findViewById(R.id.remAlert);
        String alertID = selectedReminder.getAlertID();
        if (alertID != null) {
            Alert alert = MainActivity.db.alertDao().getAlertByID(alertID);
            SimpleDateFormat sdf = new SimpleDateFormat("E, MMM dd yyyy");
            Date alertDate = alert.getAlertTime();
            Calendar alertCal = Calendar.getInstance();
            alertCal.setTime(alertDate);
            String time = getTimeText(alertCal);
            alertText.setText("Alert: " + sdf.format(alertDate) + " at " + time + "\nRepeat: "
                    + alert.getRepeat());
        }
        else alertText.setText("Alert: None");
        return convertView;
    }

    private String getTimeText (Calendar reminderDate) {
        String am_pm = "";
        if (reminderDate.get(Calendar.AM_PM) == Calendar.AM)
            am_pm = "AM";
        else if (reminderDate.get(Calendar.AM_PM) == Calendar.PM)
            am_pm = "PM";
        String strHrsToShow = (reminderDate.get(Calendar.HOUR) == 0) ?"12":reminderDate.get(Calendar.HOUR)+"";
        return strHrsToShow + ":" + reminderDate.get(Calendar.MINUTE) + " " + am_pm;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public Set<Pair<Long, Long>> getCheckedItems() {
        return tagCheckedItems;
    }

    public void filter(String query) {
        // TODO: Current Problem -> ChildViews not displaying, however, the query displays anything shown in the ReminderList (Parent) and/or the Reminder (Child).
        // Only the child views are not showing.
        query = query.toLowerCase();
        Log.v("ExpandableListAdapter", String.valueOf(listDataHeader.size()));
        listDataHeader.clear();

        if (query.isEmpty()) {
            listDataHeader.addAll(original);
            Log.v("ExpandableListAdapter", "LOG: 2");
        } else {
            for (ReminderList reminderList: original) {
                ArrayList<Reminder> mainList = new ArrayList<>();
                mainList.addAll(reminderList);//reminderList.getReminderList(); // nothing in this list ?
                Log.v("ExpandableListAdapter", "" + mainList.isEmpty());

                ArrayList<Reminder> newList = new ArrayList<>();
                Log.v("ExpandableListAdapter", "LOG: 1");

                for (Reminder reminder: mainList) {
                    if (reminder.getDescription().toLowerCase().contains(query) ||
                            reminder.getReminderID().toLowerCase().contains(query) ||
                            reminder.getType().toLowerCase().contains(query)) {
                        newList.add(reminder);
                        Log.v("ExpandableListAdapter", "QUERY: " + query);

                    }
                }
                if (newList.size() > 0) {
                    ReminderList nReminderList = new ReminderList(reminderList.getType());
                    listDataHeader.add(nReminderList);
                    Log.v("ExpandableListAdapter", "LOG: 3 -> " + reminderList.getType());
                }
            }
        }
        Log.v("ExpandableListAdapter", String.valueOf(listDataHeader.size()));
        notifyDataSetChanged();

    }
}
