package com.cs250.joanne.myfragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {

    int resource;

    public TaskAdapter(Context ctx, int res, List<Task> tasks)
    {
        super(ctx, res, tasks);
        resource = res;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout taskView;
        Task task = getItem(position);

        if (convertView == null) {
            taskView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, taskView, true);
        } else {
            taskView = (LinearLayout) convertView;
        }

        TextView nameView = (TextView) taskView.findViewById(R.id.detail);
        TextView categoryView = (TextView) taskView.findViewById(R.id.category);
        TextView dateView = (TextView) taskView.findViewById(R.id.date);

        nameView.setText(task.getName());
        categoryView.setText(task.getCategory());
        dateView.setText(task.getDate());


        return taskView;
    }

}
