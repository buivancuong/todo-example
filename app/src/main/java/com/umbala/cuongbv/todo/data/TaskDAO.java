package com.umbala.cuongbv.todo.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.umbala.cuongbv.todo.model.Task;

import java.util.List;

@Dao
public interface TaskDAO {

    @Query("SELECT * FROM task")
    List<Task> getAllTask();

    /**
     * get 1 task from task list by id
     * @param id
     * @return
     */
    @Query("SELECT * FROM task WHERE task_id = :id")
    Task getTask(String id);

    @Insert
    void addTask(Task task);

    @Query("DELETE FROM task WHERE task_id = :id")
    void delTask(String id);

    @Update
    void updateTask(Task task);


}
