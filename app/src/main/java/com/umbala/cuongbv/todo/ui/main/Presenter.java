package com.umbala.cuongbv.todo.ui.main;

import android.os.Handler;

import com.umbala.cuongbv.todo.data.TaskRepo;
import com.umbala.cuongbv.todo.data.TaskRepository;
import com.umbala.cuongbv.todo.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * là một sự triển khai của {@link MainContractor.Presenter}
 * lớp này sẽ phụ trách triển khai các chức năng được khai báo trong MainContractor.Presenter
 *
 */
public class Presenter implements MainContractor.Presenter {

    private TaskRepository taskRepository;
    private MainContractor.View view;

    /**
     * phương thức này sẽ phụ trách việc lấy danh sách task từ {@link TaskRepository}
     * ngoài ra phương thức này sẽ fake việc delay khi lấy dữ liệu nặng
     * ví dụ như lấy dữ liệu từ API thì sẽ phải load trong một thời gian nào đó,
     * sau khi load xong mới có dữ liệu cho view hiển thị,
     * để người dùng không có cảm giác chờ đợi thì nên hiển thị một dialog
     * thể hiện rằng dữ liệu đang được load. sau khi load thành công thì
     * ẩn dialog loading này đi
     *
     *
     * Bên edit
     */
    @Override
    public void getTaskList() {
        view.showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.hideLoading();
                List<Task> tasks = taskRepository.getAllTask();
                view.showTaskList(tasks);
            }
        }, 2000); //fake delay trong 2 giây
    }

    @Override
    public void delTask(String id) {
        taskRepository.delTask(id);
        getTaskList();
    }

    @Override
    public void updateTask(Task task) {
        taskRepository.updateTask(task);
        getTaskList();
    }


    public Presenter(TaskRepository taskRepository, MainContractor.View view) {
        this.taskRepository = taskRepository;
        this.view = view;
    }
}
