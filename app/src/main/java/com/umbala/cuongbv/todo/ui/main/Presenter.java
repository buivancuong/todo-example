package com.umbala.cuongbv.todo.ui.main;

import com.umbala.cuongbv.todo.data.TaskRepository;
import com.umbala.cuongbv.todo.model.Task;

import java.util.ArrayList;
import java.util.List;

public class Presenter implements MainContractor.Presenter {

    private TaskRepository taskRepository;
    private MainContractor.View view;

    @Override
    public void getTaskList() {
        List<Task> tasks = taskRepository.getAllTask();
        view.showTaskList(tasks);
    }

    public Presenter(TaskRepository taskRepository, MainContractor.View view) {
        this.taskRepository = taskRepository;
        this.view = view;
    }
}
