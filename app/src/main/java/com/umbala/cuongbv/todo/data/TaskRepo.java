package com.umbala.cuongbv.todo.data;

import android.content.Context;
import android.util.Log;

import com.umbala.cuongbv.todo.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * lớp này tạo ra giữ liệu giả để test các khối khác khi chưa triển khai được Database hoặc API
 * lớp này cũng là một ví dụ cụ thể cho việc liên kết lỏng lẻo giữa tầng data
 * và tầng ui (tham khảo thêm {@link TaskRepository})
 *
 */
public class TaskRepo implements TaskRepository {

    private static TaskRepo instance;

    public static TaskRepo getInstance(Context context){
        if (instance == null){
            instance = new TaskRepo(context);
        }
        return instance;
    }

    private DataBase database;

    private TaskRepo(Context context) {
        database = DataBase.getInstance(context);
//        tasks = new ArrayList<>();
//        tasks.add(new Task.Builder().setTaskName("Task1").setTaskContent("This is Task 1").builder());
//        tasks.add(new Task.Builder().setTaskName("Task2").setTaskContent("This is Task 2").builder());
//        tasks.add(new Task.Builder().setTaskName("Task3").setTaskContent("This is Task 3").builder());
//        tasks.add(new Task.Builder().setTaskName("Task4").setTaskContent("This is Task 4").builder());
//        tasks.add(new Task.Builder().setTaskName("Task5").setTaskContent("This is Task 5").builder());
//        tasks.add(new Task.Builder().setTaskName("Task6").setTaskContent("This is Task 6").builder());
    }

    @Override
    public List<Task> getAllTask() {
        List<Task> tasks = database.taskDAO().getAllTask();
        Collections.sort(tasks);
        return tasks;
    }

    @Override
    public Task getTask(String id) {
        return database.taskDAO().getTask(id);
    }

    @Override
    public void addTask(Task task) {
        database.taskDAO().addTask(task);
    }

    @Override
    public void delTask(String id) {
        database.taskDAO().delTask(id);
    }

    @Override
    public void updateTask(Task task) {
        database.taskDAO().updateTask(task);
    }

}
