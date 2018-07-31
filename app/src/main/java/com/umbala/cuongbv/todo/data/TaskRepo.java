package com.umbala.cuongbv.todo.data;

import android.content.Context;

import com.umbala.cuongbv.todo.model.Task;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

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
    public Observable<List<Task>> getAllTask() {
        return new Observable<List<Task>>() {
            @Override
            protected void subscribeActual(Observer<? super List<Task>> observer) {
                final List<Task> tasks = database.taskDAO().getAllTask();
                Collections.sort(tasks);
                observer.onNext(tasks);
            }
        };
    }

    @Override
    public Observable<Task> getTask(final String id) {
        return new Observable<Task>() {
            @Override
            protected void subscribeActual(Observer<? super Task> observer) {
                observer.onNext(database.taskDAO().getTask(id));
            }
        };
    }

    @Override
    public Observable<Double> getTotalTime() {
        return new Observable<Double>() {
            @Override
            protected void subscribeActual(Observer<? super Double> observer) {
                List<Task> tasks = database.taskDAO().getAllTask();
                double totalEstimateTime = 0;
                for (int i = 0; i < tasks.size(); i++){
                    if (tasks.get(i).getTaskDoneState() == 0) {
                        totalEstimateTime += tasks.get(i).getTaskEstimateTime();
                    }
                }
                observer.onNext(totalEstimateTime);
            }
        };
    }

    @Override
    public Observable addTask(final Task task) {
        return new Observable() {
            @Override
            protected void subscribeActual(Observer observer) {
                database.taskDAO().addTask(task);
                observer.onComplete();
            }
        };
    }

    @Override
    public Observable delTask(final int id) {
        return new Observable() {
            @Override
            protected void subscribeActual(Observer observer) {
                database.taskDAO().delTask(id);
                observer.onComplete();
            }
        };
    }

    @Override
    public Observable updateTask(final Task task) {
        return new Observable() {
            @Override
            protected void subscribeActual(Observer observer) {
                database.taskDAO().updateTask(task);
                observer.onComplete();
            }
        };
    }

}
