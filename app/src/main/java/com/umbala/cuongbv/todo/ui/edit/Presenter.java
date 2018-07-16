package com.umbala.cuongbv.todo.ui.edit;

import com.umbala.cuongbv.todo.data.TaskRepo;
import com.umbala.cuongbv.todo.data.TaskRepository;
import com.umbala.cuongbv.todo.model.Task;

import java.util.List;

public class Presenter implements EditContractor.Presenter {

    private int hour, minute, day, month, year;

    private EditContractor.View view;
    private TaskRepository taskRepository;

    private Task task;

    public Presenter(EditContractor.View view, TaskRepository taskRepository) {
        this.view = view;
        this.taskRepository = taskRepository;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        if (task != null) {
            view.showEditTask(task);
        }
        this.task = task;
    }

    @Override
    public void addTask(Task task) {
        if (this.task == null) {
            taskRepository.addTask(task);
        }
        else {
            task.setTaskID(this.task.getTaskID());
            taskRepository.updateTask(task);
        }
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
