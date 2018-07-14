package com.umbala.cuongbv.todo.model;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Task implements Comparable<Task> {

    private String taskName;
    private String taskContent;
    private int taskPriority;
    private long taskEstimateTime;
    private long taskReminder;
    private int taskDoneState;
    private String taskID;

    private Task(Builder builder) {
        this.taskName = builder.taskName;
        this.taskContent = builder.taskContent;
        this.taskPriority = builder.taskPriority;
        this.taskEstimateTime = builder.taskEstimateTime;
        this.taskReminder = builder.taskReminder;
        this.taskDoneState = builder.taskDoneState;
        this.taskID = UUID.randomUUID().toString();
    }

    @Override
    public int compareTo(@NonNull Task task) {
        return Integer.compare(this.taskDoneState, task.taskDoneState);
    }

    public static class Builder {

        private String taskName = "";
        private String taskContent = "";
        private int taskPriority = 3;
        private long taskEstimateTime = 0;
        private long taskReminder = 0; // ms
        private int taskDoneState = 0;

        public Builder setTaskName(String name) {
            taskName = name;
            return this;
        }

        public Builder setTaskContent(String content) {
            taskContent = content;
            return this;
        }

        public Builder setTaskPriority(int priority) {
            taskPriority = priority;
            return this;
        }

        public Builder setTaskEstimateTime (long estimateTime) {
            taskEstimateTime = estimateTime;
            return this;
        }

        public Builder setTaskReminder(long reminder) {
            taskReminder = reminder;
            return this;
        }

        public Builder setTaskDoneState(int doneState) {
            taskDoneState = doneState;
            return this;
        }

        public Task builder(){
            return new Task(this);
        }
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    public long getTaskEstimateTime() {
        return taskEstimateTime;
    }

    public long getTaskReminder() {
        return taskReminder;
    }

    public int getTaskDoneState() {
        return taskDoneState;
    }

    public String getTaskID() {
        return taskID;
    }

    public String getEstimateTime() {
        return taskEstimateTime/(60 * 60 * 1000) + "";
    }

    public String getRemindDate() {
        Date date = new Date(taskReminder);
        DateFormat dateFormat = new SimpleDateFormat("dd:MM:yy - HH:mm:ss", Locale.US);
        return dateFormat.format(date);
    }
}
