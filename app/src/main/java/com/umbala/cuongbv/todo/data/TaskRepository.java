package com.umbala.cuongbv.todo.data;

import com.umbala.cuongbv.todo.model.Task;

import java.util.List;

public interface TaskRepository {

    /**
     * get all task from task list on DB
     * @return list of Tasks
     */
    List<Task> getAllTask();

    /**
     * get 1 task from task list by id
     * @param id
     * @return
     */
    Task getTask(String id);

    void addTask(Task task);

    void delTask(String id);

    void updateTask(Task task);

    List<Task> getSortTaskList();
}
