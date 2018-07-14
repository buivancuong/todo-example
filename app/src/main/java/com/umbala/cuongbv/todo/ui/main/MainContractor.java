package com.umbala.cuongbv.todo.ui.main;

import com.umbala.cuongbv.todo.model.Task;

import java.util.List;

public interface MainContractor {

    interface View {

        void showTaskList(List<Task> tasks);

    }

    interface Presenter{ // controller

        void getTaskList();

    }

}
