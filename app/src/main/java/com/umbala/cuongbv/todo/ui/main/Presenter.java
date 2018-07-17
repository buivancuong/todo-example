package com.umbala.cuongbv.todo.ui.main;



import com.umbala.cuongbv.todo.data.TaskRepository;
import com.umbala.cuongbv.todo.model.Task;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * là một sự triển khai của {@link MainContractor.Presenter}
 * lớp này sẽ phụ trách triển khai các chức năng được khai báo trong MainContractor.Presenter
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
     * <p>
     * <p>
     * Bên edit
     */
    @Override
    public void getTaskList() {
        view.showLoading();
        taskRepository.getAllTask()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Task>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Task> tasks) {
                        view.showTaskList(tasks);
                        view.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void delTask(String id) {
        taskRepository.delTask(id)
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
                        getTaskList();
                    }
                });
    }

    @Override
    public void updateTask(Task task) {
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
                        getTaskList();
                    }
                });
    }


    public Presenter(TaskRepository taskRepository, MainContractor.View view) {
        this.taskRepository = taskRepository;
        this.view = view;
    }
}
