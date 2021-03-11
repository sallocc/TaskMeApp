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
    private boolean completed;

    Task(String title, String category, String date) {
        this.taskName = title;
        this.taskCategory = category;
        this.taskDate = date;
        this.completed = false;
    }

    public String getName() { return taskName; }

    public boolean isCompleted() { return completed; }

    public void setCompleted(boolean completed) { this.completed = completed; }

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

    public void setName(String name) { this.taskName = name; }

    public void setTaskCategory(String category) { this.taskCategory = category; }

    public void setTaskDate(String date) { this.taskDate = date; }
}
