package com.cs250.joanne.myfragments;

import java.lang.String;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Holds data for one item
 */
public class Task {
    private String taskName;
    private String taskCategory;
    private String taskDate;

    Task(String title, String category, String date) {
        this.taskName = title;
        this.taskCategory = category;
        this.taskDate = date;
    }

    public String getName() { return taskName; }

    public String getCategory() { return taskCategory; }

    public String getDate() { return taskDate; }
    public boolean equals(Object taskOther) {
        if (taskOther == this) {
            return true;
        }
        if (!(taskOther instanceof Task)) {
            return false;
        }
        Task o = (Task) taskOther;
        return (this.taskName.equals(o.taskName) && this.taskCategory.equals(o.taskCategory) && this.taskDate.equals(o.taskDate));
    }
    public Date getDateObject() {
        try {
            return new SimpleDateFormat("MM/dd/yyyy").parse(taskDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
