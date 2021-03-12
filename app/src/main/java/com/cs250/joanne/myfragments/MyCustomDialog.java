package com.cs250.joanne.myfragments;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.Nullable;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyCustomDialog extends DialogFragment {

    private static final String TAG = "MyCustomDialog";
    ArrayList<Task> array;


    public interface OnInputListener {
        void sendInput(String input);
    }

    public OnInputListener mOnInputListener;

    //widgets
    private TextView mCategory, mTitle, mDate, mActionCancel, mDoneDate;
    private Button mActionCompleted;
    //MainActivity mainActivity;
    public Task task;
    protected boolean isCompleted = false;

    //vars
//    public MyCustomDialog(ArrayList<Task> myCurrentTasks) {
//        array = myCurrentTasks;
//    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_task_dialog, container, false);
        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionCompleted = view.findViewById(R.id.action_completed);
        mCategory = view.findViewById(R.id.dialogCategory);
        mTitle = view.findViewById(R.id.dialogTitle);
        mDate = view.findViewById(R.id.dialogDate);
        mDoneDate = view.findViewById(R.id.dialogDoneDate);
        mTitle.setText(task.getName());
        mCategory.setText(task.getCategory());
        mDate.setText(task.getDate());
        mDoneDate.setText(task.getDoneDate());
        final MainActivity myact = (MainActivity) getActivity();

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog");
                //Change to complete task
                getDialog().dismiss();
            }
        });

        if (isCompleted) {
            // hide mark completed button
            mActionCompleted.setVisibility(View.GONE);
        } else {
            // hide done date
            mDoneDate.setVisibility(View.GONE);
        }

        mActionCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: capturing input");

//                String input = mInput.getText().toString();
//                  if(!input.equals("")){
//
//                    //Easiest way: just set the value
                // mainActivity.mInputDisplay.setText(input);
                task.setDone();
                Date currentDate = new Date();
                if (task.getDateObject().compareTo(currentDate) < 0) {
                    //update past due
                    int doneAfterDeadline = myact.myPrefs.getInt("doneAfterDeadline", 0);
                    myact.myPrefs.edit().putInt("doneAfterDeadline", doneAfterDeadline + 1).apply();
                } else {
                    //update to be done
                    int doneByDeadline = myact.myPrefs.getInt("doneByDeadline", 0);
                    myact.myPrefs.edit().putInt("doneByDeadline", doneByDeadline + 1).apply();
                }
                if (myact.myCompletedTasks != null) {
                    myact.myCompletedTasks.add(task);
                }
                myact.taskAdapter.notifyDataSetChanged();
                myact.myCurrentTasks.remove(task);

                myact.completedTaskAdapter.notifyDataSetChanged();
//                mainActivity.mString jsonCompletedTasks = gson.toJson(myCompletedTasks);yCurrentTasks.remove(task);
////                }
                getDialog().dismiss();


//                Gson gson = new Gson();
//                SharedPreferences sharedPref = myact.getPreferences(Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPref.edit();
//                String jsonCompletedTasks = gson.toJson(sharedPref.getString("CompletedTasks", ""));
//                String jsonCurrentTasks = gson.toJson(sharedPref.getString("CurrentTasks", ""));
//                Type type = new TypeToken<List<Task>>() {
//                }.getType();
//                List<Task> taskCompleted = gson.fromJson(jsonCompletedTasks, type);
//                List<Task> taskCurrent = gson.fromJson(jsonCurrentTasks, type);
//                taskCurrent.remove(new Task(mTitle.getText().toString(), mCategory.getText().toString(), mDate.getText().toString()));
//                //added
//                taskCompleted.add(new Task(mTitle.getText().toString(), mCategory.getText().toString(), mDate.getText().toString()));
//                jsonCompletedTasks = gson.toJson(taskCompleted);
//                jsonCurrentTasks = gson.toJson(taskCurrent);
//                editor.putString("CompletedTasks", jsonCompletedTasks);
//                editor.putString("CurrentTasks", jsonCurrentTasks);
//                editor.apply();


                // Task current = new Task(mTitle.getText().toString(),mCategory.getText().toString(),mDate.getText().toString());
//                for (Task tasks : mainActivity.myCurrentTasks) {
//                    if (tasks == current) {
//                        mainActivity.myCurrentTasks.remove(current);
//                    }
                //          }
                // String jsonText = mainActivity.myPrefs.getString("key", null);
                // String[] text = gson.fromJson(jsonText, String[].class);  //EDIT: gso to gson


                //       getDialog().dismiss();
            }
        });

         return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            //mOnInputListener = (OnInputListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }
}