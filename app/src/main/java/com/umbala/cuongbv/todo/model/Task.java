package com.umbala.cuongbv.todo.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * lớp Task này sử dụng Builder Pattern, một trong số các Design Pattern của GoF.
 * nếu chưa biết gì về pattern này, có thể tiếp cận qua cuốn sách
 * Headfirst Design Pattern của nhà xuất bản O'Reilly
 *
 */
@Entity(tableName = "task")
public class Task implements Comparable<Task>,Parcelable {

    @ColumnInfo(name = "task_name")
    private String taskName;
    @ColumnInfo(name = "task_content")
    private String taskContent;
    @ColumnInfo(name = "task_priority")
    private int taskPriority;
    @ColumnInfo(name = "task_estimate_time")
    private double taskEstimateTime;
    @ColumnInfo(name = "task_hour")
    private int taskHour;
    @ColumnInfo(name = "task_minute")
    private int taskMinute;
    @ColumnInfo(name = "task_day")
    private int taskDay;
    @ColumnInfo(name = "task_month")
    private int taskMonth;
    @ColumnInfo(name = "task_year")
    private int taskYear;
    @ColumnInfo(name = "task_done_state")
    private int taskDoneState;
    @ColumnInfo(name = "isReminder")
    private int isReminder;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    private int taskID;

    public Task(){

    }

    private Task(Builder builder) {
        this.taskName = builder.taskName;
        this.taskContent = builder.taskContent;
        this.taskPriority = builder.taskPriority;
        this.taskEstimateTime = builder.taskEstimateTime;
        this.taskHour = builder.taskHour;
        this.taskMinute = builder.taskMinute;
        this.taskDay = builder.taskDay;
        this.taskMonth = builder.taskMonth;
        this.taskYear = builder.taskYear;
        this.taskDoneState = builder.taskDoneState;
        this.isReminder = builder.isReminder;
    }

    protected Task(Parcel in) {
        taskName = in.readString();
        taskContent = in.readString();
        taskPriority = in.readInt();
        taskEstimateTime = in.readDouble();
        taskHour = in.readInt();
        taskMinute = in.readInt();
        taskDay = in.readInt();
        taskMonth = in.readInt();
        taskYear = in.readInt();
        taskDoneState = in.readInt();
        taskID = in.readInt();
        isReminder = in.readInt();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    /**
     * Lóp task có implement interface {@link Comparable}.
     * interface này được viết ra để nhằm mục đích giúp một đối tượng có thể so sánh được
     * với một đối tượng khác, mục đích của việc này để sắp xếp một danh sách các đối tượng
     * dựa trên thuộc tính nào đó của đối tượng bằng thuật toán Merged sort build-in cùng với
     * {@link java.util.Collections} framework của Java.
     *
     * @param task
     * @return
     */
    @Override
    public int compareTo(@NonNull Task task) {
        return Integer.compare(this.taskDoneState, task.taskDoneState);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(taskName);
        parcel.writeString(taskContent);
        parcel.writeInt(taskPriority);
        parcel.writeDouble(taskEstimateTime);
        parcel.writeInt(taskHour);
        parcel.writeInt(taskMinute);
        parcel.writeInt(taskDay);
        parcel.writeInt(taskMonth);
        parcel.writeInt(taskYear);
        parcel.writeInt(taskDoneState);
        parcel.writeInt(taskID);
        parcel.writeInt(isReminder);
    }

    public static class Builder {

        private String taskName = "";
        private String taskContent = "";
        private int taskPriority = 3;
        private double taskEstimateTime = 0;
        private int taskHour;
        private int taskMinute;
        private int taskDay;
        private int taskMonth;
        private int taskYear;
        private int taskDoneState = 0;
        private int isReminder = 0;

        public Builder setIsReminder(int reminder) {
            isReminder = reminder;
            return this;
        }

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

        public Builder setTaskEstimateTime (double estimateTime) {
            taskEstimateTime = estimateTime;
            return this;
        }

        public Builder setTaskHour (int hour) {
            taskHour = hour;
            return this;
        }

        public Builder setTaskMinute (int minute) {
            taskMinute = minute;
            return this;
        }

        public Builder setTaskDay (int day) {
            taskDay = day;
            return this;
        }

        public Builder setTaskMonth (int month) {
            taskMonth = month;
            return this;
        }

        public Builder setTaskYear (int year) {
            taskYear = year;
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

    public double getTaskEstimateTime() {
        return taskEstimateTime;
    }

    public int getTaskHour() {
        return taskHour;
    }

    public int getTaskMinute() {
        return taskMinute;
    }

    public int getTaskDay() {
        return taskDay;
    }

    public int getTaskMonth() {
        return taskMonth;
    }

    public int getTaskYear() {
        return taskYear;
    }

    public int getTaskDoneState() {
        return taskDoneState;
    }

    public void setTaskDoneState(int taskDoneState) {
        this.taskDoneState = taskDoneState;
    }

    public int getTaskID() {
        return taskID;
    }

    public String getEstimateTime() {
        return taskEstimateTime + "";
    }

    public void setTaskHour(int taskHour) {
        this.taskHour = taskHour;
    }

    public void setTaskMinute(int taskMinute) {
        this.taskMinute = taskMinute;
    }

    public void setTaskDay(int taskDay) {
        this.taskDay = taskDay;
    }

    public void setTaskMonth(int taskMonth) {
        this.taskMonth = taskMonth;
    }

    public void setTaskYear(int taskYear) {
        this.taskYear = taskYear;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public void setTaskPriority(int taskPriority) {
        this.taskPriority = taskPriority;
    }

    public void setTaskEstimateTime(double taskEstimateTime) {
        this.taskEstimateTime = taskEstimateTime;
    }

    public int getIsReminder() {
        return isReminder;
    }

    public void setIsReminder(int isReminder) {
        this.isReminder = isReminder;
    }
}
