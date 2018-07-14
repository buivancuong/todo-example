package com.umbala.cuongbv.todo.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.umbala.cuongbv.todo.R;
import com.umbala.cuongbv.todo.data.TaskRepo;
import com.umbala.cuongbv.todo.model.Task;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContractor.View {

    RecyclerView recyclerView;
    TextView textViewEstimateTime;
    TaskAdapter taskAdapter;
    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.tasklist);
        textViewEstimateTime = findViewById(R.id.textView);

        taskAdapter = new TaskAdapter();
        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        presenter = new Presenter(new TaskRepo(), this);
        presenter.getTaskList();

    }

    @Override
    public void showTaskList(List<Task> tasks) {

        taskAdapter.setTasks(tasks);

    }
}
