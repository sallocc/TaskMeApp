package com.cs250.joanne.myfragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatsFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatsFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "doneByDeadline";
    private static final String ARG_PARAM2 = "doneAfterDeadline";
    private static final String ARG_PARAM3 = "pastDue";
    private static final String ARG_PARAM4 = "toBeDone";
    private static final String ARG_PARAM5 = "totalTasks";

    // TODO: Rename and change types of parameters
    private int doneByDeadline;
    private int doneAfterDeadline;
    private int pastDue;
    private int toBeDone;
    private int totalTasks;

    Context cntx;
    private MainActivity myact;

    public StatsFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatsFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static StatsFrag newInstance(int param1, int param2, int param3, int param4, int param5) {
        StatsFrag fragment = new StatsFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3, param3);
        args.putInt(ARG_PARAM4, param4);
        args.putInt(ARG_PARAM5, param5);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cntx = getActivity().getApplicationContext();
        myact = (MainActivity) getActivity();

        if (myact.myPrefs != null) {
            doneByDeadline = myact.myPrefs.getInt("doneByDeadline", 0);
            doneAfterDeadline = myact.myPrefs.getInt("doneAfterDeadline", 0);
            pastDue = myact.myPrefs.getInt("pastDue", 0);
            toBeDone = myact.myPrefs.getInt("toBeDone", 0);
            totalTasks = myact.myPrefs.getInt("totalTasks", 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        TextView doneByDeadlineText = view.findViewById(R.id.done_by_deadline_number);
        doneByDeadlineText.setText(String.valueOf(doneByDeadline));
        TextView doneAfterDeadlineText = view.findViewById(R.id.done_after_deadline_number);
        doneAfterDeadlineText.setText(String.valueOf(doneAfterDeadline));
        TextView pastDueText = view.findViewById(R.id.past_due_number);
        pastDueText.setText(String.valueOf(pastDue));
        TextView toBeDoneText = view.findViewById(R.id.to_be_done_number);
        toBeDoneText.setText(String.valueOf(toBeDone));
        TextView totalTasksText = view.findViewById(R.id.total_tasks_number);
        totalTasksText.setText(String.valueOf(totalTasks));

        return view;
    }
}