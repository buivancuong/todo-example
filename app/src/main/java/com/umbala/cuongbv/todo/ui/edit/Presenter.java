package com.umbala.cuongbv.todo.ui.edit;


import com.umbala.cuongbv.todo.data.TaskRepository;
import com.umbala.cuongbv.todo.model.Task;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Presenter implements EditContractor.Presenter {

    private int hour, minute, day, month, year;
//    private float estimateTime;
    private EditContractor.View view;
    private TaskRepository taskRepository;

    private Task task;

    public Presenter(EditContractor.View view, TaskRepository taskRepository) {
        this.view = view;
        this.taskRepository = taskRepository;
    }

/*    public float getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(float estimateTime) {
        this.estimateTime = estimateTime;
    }*/

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public EditContractor.View getView() {
        return view;
    }

    public void setView(EditContractor.View view) {
        this.view = view;
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
            taskRepository.addTask(task)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Object o) {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            view.exitActivity();
                        }
                    });
        }
        else {
            task.setTaskID(this.task.getTaskID());
            taskRepository.updateTask(task)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Object o) {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            view.exitActivity();
                        }
                    });
        }
    }

}
