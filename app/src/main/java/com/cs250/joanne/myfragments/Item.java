package com.cs250.joanne.myfragments;

import java.lang.String;
import java.util.Calendar;

/**
 * Holds data for one item
 */
public class Item {
    private String taskName;
    private String taskCategory;
    private String taskDate;


    Item(String title, String category, String date) {
        this.taskName = title;
        this.taskCategory = category;
        this.taskDate = date;
    }

    public String getName() { return taskName; }

    public String getCategory() { return taskCategory; }

    public String getDate() { return taskDate; }

}
