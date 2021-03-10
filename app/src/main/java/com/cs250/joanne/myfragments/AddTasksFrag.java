package com.cs250.joanne.myfragments;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
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
                category.setText(parent.getItemAtPosition(5).toString());
            }
        });



        btn = (Button) view.findViewById(R.id.add_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task myTask = new Task(tv.getText().toString(), category.getText().toString(), date.getText().toString());
                myact.myCurrentTasks.add(myTask);
                int totalTasks = myact.myPrefs.getInt("totalTasks", 0);
                myact.myPrefs.edit().putInt("totalTasks", totalTasks + 1).apply();


                Collections.sort(myact.myCurrentTasks, new Comparator<Task>() {
                       @Override
                       public int compare(Task lhs, Task rhs) {
                           return lhs.getDateObject().compareTo(rhs.getDateObject());
                       }
                });
                Toast.makeText(getActivity().getApplicationContext(), "added task", LENGTH_SHORT).show();
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


            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c.getTime());

            date.setText(formattedDate);
        }




        }


}
