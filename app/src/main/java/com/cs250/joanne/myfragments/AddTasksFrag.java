package com.cs250.joanne.myfragments;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static android.widget.Toast.LENGTH_SHORT;

public class AddTasksFrag extends Fragment {

    private TextView tv;
    private Spinner categorySpinner;

    private TextView category;
    private static TextView date;

    private Button btn;
    private Button dateButton;
    private Button cancel;
    private MainActivity myact;
    Context context;
    final static Calendar c = Calendar.getInstance();
    ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.addtask_frag, container, false);

        myact = (MainActivity) getActivity();
        context = myact.getApplicationContext();

        tv = (EditText) view.findViewById(R.id.item_text);
        category = (TextView) view.findViewById(R.id.category_text);
        date = (TextView) view.findViewById(R.id.date_text);

        categorySpinner = (Spinner) view.findViewById(R.id.category_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.category_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category.setText(parent.getItemAtPosition(4).toString());
            }
        });

        cancel = (Button) view.findViewById(R.id.cancel_btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Reset metadata
                myact.myPrefs.edit().putInt("taskIndex", -1).putString("taskCompletion", "current").apply();

                Toast.makeText(getActivity().getApplicationContext(), "Update cancelled", LENGTH_SHORT).show();
                myact.toolbar.setTitle(R.string.current_tasks);
                myact.transaction = myact.getSupportFragmentManager().beginTransaction();
                myact.transaction.replace(R.id.fragment_container, myact.currentTasks);
                myact.transaction.addToBackStack(null);

// Commit the transaction
                myact.transaction.commit();
            }
        });

        btn = (Button) view.findViewById(R.id.add_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add error handling for form
                String taskName = tv.getText().toString();
                String taskDate = date.getText().toString();
                if (taskName.length() < 1) {
                    Toast.makeText(context, "Please enter a task name",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (taskDate.equals("00/00/00")) {
                    Toast.makeText(context, "Please choose a date",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<Task> taskList = myact.myPrefs.getString("taskCompletion", "current").equals("current") ?
                        myact.myCurrentTasks: myact.myCompletedTasks;
                //Grab index metadata if possible, otherwise make new task
                int index = myact.myPrefs.getInt("taskIndex", -1);
                Task myTask = index == -1 ?
                        new Task(tv.getText().toString(), category.getText().toString(), date.getText().toString()):
                        taskList.remove(index);
                myTask.setName(tv.getText().toString());
                myTask.setTaskCategory(category.getText().toString());
                myTask.setTaskDate(date.getText().toString());
                taskList.add(myTask);
                myact.taskAdapter.notifyDataSetChanged();
                myact.completedTaskAdapter.notifyDataSetChanged();
                int totalTasks = myact.myPrefs.getInt("totalTasks", 0);
                //If we are adding a task instead of editing
                if (index == -1) {
                    myact.myPrefs.edit().putInt("totalTasks", totalTasks + 1).apply();
                    //update stats
                    Date currentDate = new Date();
                    if (myTask.getDateObject().compareTo(currentDate) < 0) {
                        //update past due
                        int pastDue = myact.myPrefs.getInt("pastDue", 0);
                        myact.myPrefs.edit().putInt("pastDue", pastDue + 1).apply();
                    } else {
                        //update to be done
                        int toBeDone = myact.myPrefs.getInt("toBeDone", 0);
                        myact.myPrefs.edit().putInt("toBeDone", toBeDone + 1).apply();
                    }
                }


                Collections.sort(taskList, new Comparator<Task>() {
                       @Override
                       public int compare(Task lhs, Task rhs) {
                           return lhs.getDateObject().compareTo(rhs.getDateObject());
                       }
                });
                //Reset metadata
                myact.myPrefs.edit().putInt("taskIndex", -1).putString("taskCompletion", "current").apply();

                Toast.makeText(getActivity().getApplicationContext(), "added task", LENGTH_SHORT).show();
                myact.toolbar.setTitle(R.string.current_tasks);
                myact.transaction = myact.getSupportFragmentManager().beginTransaction();
                myact.transaction.replace(R.id.fragment_container, myact.currentTasks);
                myact.transaction.addToBackStack(null);

// Commit the transaction
                myact.transaction.commit();
            }
        });

        dateButton = (Button) view.findViewById(R.id.date_button);
        dateButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DialogFragment newFragment = new DatePickerFragment();
               newFragment.show(getFragmentManager(), "datePicker");
           }
        });

        return view;
    }

    // Called at the start of the visible lifetime.
    @Override
    public void onStart(){
        super.onStart();
        Log.d ("Other Fragment2", "onStart");
        // Apply any required UI change now that the Fragment is visible.
        int index = myact.myPrefs.getInt("taskIndex", -1);
        ArrayList<Task> taskList = myact.myPrefs.getString("taskCompletion", "current").equals("current") ?
                myact.myCurrentTasks: myact.myCompletedTasks;
        System.out.println("This edit came from a task that is " + myact.myPrefs.getString("taskCompletion", "current"));
        Task myTask = index == -1 ? new Task("", "miscellaneous", "00/00/00"):
                taskList.get(index);
        tv.setText(myTask.getName());
        category.setText(myTask.getCategory());
        date.setText(myTask.getDate());

    }

    // Called at the end of the active lifetime.
    @Override
    public void onPause(){
        Log.d ("Other Fragment", "onPause");
        // Suspend UI updates, threads, or CPU intensive processes
        // that don't need to be updated when the Activity isn't
        // the active foreground activity.
        // Persist all edits or state changes
        // as after this call the process is likely to be killed.
        myact.myPrefs.edit().putInt("taskIndex", -1).putString("taskCompletion", "current").apply();
        super.onPause();
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
           // today = calendar.getTime();

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            date.setText((month + 1) + "/" + day + "/" + year);
            populateSetDate(year, month, day);
        }

        public void populateSetDate(int year, int month, int day) {

            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            Date time = c.getTime();


            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String formattedDate = df.format(c.getTime());

            date.setText(formattedDate);
        }




        }


}
