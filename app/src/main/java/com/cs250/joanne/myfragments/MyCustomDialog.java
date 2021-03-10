package com.cs250.joanne.myfragments;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class MyCustomDialog extends DialogFragment {

    private static final String TAG = "MyCustomDialog";

    public interface OnInputListener {
        void sendInput(String input);
    }

    public OnInputListener mOnInputListener;

    //widgets
    private TextView mCategory;
    private TextView mTitle;
    private TextView mDate;
    MainActivity mainActivity = ((MainActivity)getActivity());
    public Task task;
    private TextView mActionCompleted, mActionCancel;


    //vars

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_task_dialog, container, false);
        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionCompleted = view.findViewById(R.id.action_completed);
        mCategory = view.findViewById(R.id.dialogCategory);
        mTitle = view.findViewById(R.id.dialogTitle);
        mDate = view.findViewById(R.id.dialogDate);
        mTitle.setText(task.getName());
        mCategory.setText(task.getCategory());
        mDate.setText(task.getDate());

        MainActivity myact = (MainActivity) getActivity();

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog");
                //Change to complete task
                getDialog().dismiss();
            }
        });


        mActionCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: capturing input");

//                String input = mInput.getText().toString();
//                  if(!input.equals("")){
//
//                    //Easiest way: just set the value
                   // mainActivity.mInputDisplay.setText(input);
                if (mainActivity.myCompletedTasks != null) {
                    mainActivity.myCompletedTasks.add(task);

                }
                mainActivity.myCurrentTasks.remove(task);
//                }

                //"Best Practice" but it takes longer
//                mOnInputListener.sendInput(input);

                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnInputListener = (OnInputListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }
}