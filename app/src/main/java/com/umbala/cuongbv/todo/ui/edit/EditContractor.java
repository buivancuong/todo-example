package com.umbala.cuongbv.todo.ui.edit;

import android.support.annotation.NonNull;

import com.umbala.cuongbv.todo.model.Task;

import java.util.List;

public interface EditContractor {

    interface View{

        void showEditTask(@NonNull Task task);

    }

    interface Presenter{

        void addTask(Task task);

    }

}
