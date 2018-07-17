package com.umbala.cuongbv.todo.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
    @Ignore
            //(name = "task_reminder")
    private int[] taskReminder = {0, 0, 0, 0, 0};       // hour, minute, day, month, year
    @ColumnInfo(name = "task_done_state")
    private int taskDoneState;
    @NonNull
    @PrimaryKey()
    @ColumnInfo(name = "task_id")
    private String taskID;

    public Task(){

    }

    private Task(Builder builder) {
        this.taskName = builder.taskName;
        this.taskContent = builder.taskContent;
        this.taskPriority = builder.taskPriority;
        this.taskEstimateTime = builder.taskEstimateTime;
        this.taskReminder = builder.taskReminder;
        this.taskDoneState = builder.taskDoneState;
        this.taskID = UUID.randomUUID().toString();
    }

    protected Task(Parcel in) {
        taskName = in.readString();
        taskContent = in.readString();
        taskPriority = in.readInt();
        taskEstimateTime = in.readDouble();
        in.readIntArray(taskReminder);
        taskDoneState = in.readInt();
        taskID = in.readString();
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

    public void setTaskID(String taskID) {
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
        parcel.writeIntArray(taskReminder);
        parcel.writeInt(taskDoneState);
        parcel.writeString(taskID);
    }

    public static class Builder {

        private String taskName = "";
        private String taskContent = "";
        private int taskPriority = 3;
        private double taskEstimateTime = 0;
        private int[] taskReminder = {0, 0, 1, 1, 1970}; //
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

        public Builder setTaskEstimateTime (double estimateTime) {
            taskEstimateTime = estimateTime;
            return this;
        }

        public Builder setTaskReminder(int[] reminder) {
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

    public double getTaskEstimateTime() {
        return taskEstimateTime;
    }

    public int[] getTaskReminder() {
        return taskReminder;
    }

    public int getTaskDoneState() {
        return taskDoneState;
    }

    public void setTaskDoneState(int taskDoneState) {
        this.taskDoneState = taskDoneState;
    }

    public String getTaskID() {
        return taskID;
    }

    public String getEstimateTime() {
        return taskEstimateTime/(60 * 60 * 1000) + "";
    }

    public void setTaskReminder(int[] taskReminder) {
        this.taskReminder = taskReminder;
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

}
