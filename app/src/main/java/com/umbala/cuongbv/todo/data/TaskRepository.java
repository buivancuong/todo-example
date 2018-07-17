package com.umbala.cuongbv.todo.data;

import com.umbala.cuongbv.todo.model.Task;

import java.util.List;

import io.reactivex.Observable;

/**
 * interface này là giao diện chung cho phần xử lý dữ liệu của model {@link Task}
 * tất cả các phần xử lý dữ liệu liên quan đến task ví dụ như trao dữ liệu từ API,
 * từ DB hoặc ổ cứng sẽ cần implement interface này.
 * Việc sử dụng interface này nhằm mục đích làm lỏng lẻo các liên kết giữa
 * tầng xử lý dữ liệu và tầng hiển thị dữ liệu. các liên kết giữa các tầng này lỏng lẻo tới mức
 * có thể thay thế được. ví dụ như, hiện tại đang sử dụng SQLite để lưu dữ liệu của các task
 * nhưng sau một thời gian vận hành có thể bỏ SQLite để thay thế bằng API hoặc lưu qua file
 * trên ổ cứng.
 *
 */
public interface TaskRepository {

    /**
     * get all task from task list on DB
     * @return list of Tasks
     */
    Observable<List<Task>> getAllTask();

    /**
     * get 1 task from task list by id
     * @param id
     * @return
     */
    Observable<Task> getTask(String id);

    Observable addTask(Task task);

    Observable delTask(String id);

    Observable updateTask(Task task);

}
