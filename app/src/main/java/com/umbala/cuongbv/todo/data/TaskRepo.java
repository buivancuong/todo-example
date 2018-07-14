package com.umbala.cuongbv.todo.data;

import com.umbala.cuongbv.todo.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepo implements TaskRepository {

    ArrayList<Task> tasks;

    public TaskRepo() {
        tasks = new ArrayList<>();
        tasks.add(new Task.Builder().setTaskName("Task1").setTaskContent("This is Task 1").builder());
        tasks.add(new Task.Builder().setTaskName("Task2").setTaskContent("This is Task 2").builder());
        tasks.add(new Task.Builder().setTaskName("Task3").setTaskContent("This is Task 3").builder());
        tasks.add(new Task.Builder().setTaskName("Task4").setTaskContent("This is Task 4").builder());
        tasks.add(new Task.Builder().setTaskName("Task5").setTaskContent("This is Task 5").builder());
        tasks.add(new Task.Builder().setTaskName("Task6").setTaskContent("This is Task 6").builder());
    }



    @Override
    public List<Task> getAllTask() {
        return tasks;
    }

    @Override
    public Task getTask(String id) {
        return null;
    }

    @Override
    public void addTask(Task task) {

    }

    @Override
    public void delTask(String id) {

    }

    @Override
    public void updateTask(Task task) {

    }

    @Override
    public List<Task> getSortTaskList() {
        return null;
    }
}
