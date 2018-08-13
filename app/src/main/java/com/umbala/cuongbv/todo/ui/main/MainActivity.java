package com.umbala.cuongbv.todo.ui.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.umbala.cuongbv.todo.R;
import com.umbala.cuongbv.todo.data.TaskRepo;
import com.umbala.cuongbv.todo.model.Task;
import com.umbala.cuongbv.todo.ui.edit.EditActivity;

import java.util.Calendar;
import java.util.List;

/**
 * lớp này là một khai triển của interface {@link MainContractor.View}
 */
public class MainActivity extends AppCompatActivity implements MainContractor.View, TaskAdapter.ClickListener {

    RecyclerView recyclerView;
    TextView textViewEstimateTime, noTextView;
    TaskAdapter taskAdapter;
    Presenter presenter;
    ProgressDialog mProgressDialog;
    Toolbar mainToolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainToolbar = (Toolbar) findViewById(R.id.mainToolBar);
        setSupportActionBar(mainToolbar);

        noTextView = findViewById(R.id.noTextView);

        recyclerView = findViewById(R.id.tasklist);
        textViewEstimateTime = findViewById(R.id.textView);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Loading");

        taskAdapter = new TaskAdapter();
        taskAdapter.setClickListener(this);
        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new TaskDecoration());

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.RIGHT, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
                final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.delTask(((TaskAdapter.TaskViewHolder) viewHolder).task.getTaskID());
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        taskAdapter.notifyDataSetChanged();
                    }
                }).create();
                alertDialog.setMessage("Delete this task?");
                alertDialog.show();
            }
        };
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        presenter = new Presenter(TaskRepo.getInstance(this), this);
        presenter.getTaskList();

        Intent nullIntent = new Intent(MainActivity.this, AlarmService.class);
        startService(nullIntent);

        findViewById(R.id.addTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(EditActivity.getStartIntent(MainActivity.this, null), 1); // 1: add task
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1) {
            presenter.getTaskList();
        }
    }

    @Override
    public void showTaskList(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            noTextView.setVisibility(View.VISIBLE);
        } else {
            noTextView.setVisibility(View.INVISIBLE);
        }
        taskAdapter.setTasks(tasks);
    }

    @Override
    public void showEstimateTime(double t) {
        textViewEstimateTime.setText(String.format("Total Estimated Time: %s hours", t));
    }

    @Override
    public void onItemClicked(Task task) {
        startActivityForResult(EditActivity.getStartIntent(this,task), 1);
    }

    @Override
    public void onItemLongClick(final Task task) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.delTask(task.getTaskID());
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).create();
        alertDialog.setMessage("Delete this task?");
        alertDialog.show();
    }

    @Override
    public void onDoneStateChanged(Task task) {
        presenter.updateTask(task);
    }

    @Override
    public void onTurnOnReminder(Task task) {
        presenter.updateTask(task);
        Intent onReminderIntent = new Intent(MainActivity.this, AlarmService.class);

        Calendar calendar = Calendar.getInstance();
        calendar.set(task.getTaskYear(), task.getTaskMonth(), task.getTaskDay(), task.getTaskHour(), task.getTaskMinute(), 0);

        onReminderIntent.putExtra("Task Content", task.getTaskContent());
        onReminderIntent.putExtra("Task Name", task.getTaskName());
        onReminderIntent.putExtra("Task ID", task.getTaskID());
        onReminderIntent.putExtra("Task Reminder", calendar.getTimeInMillis());

        startService(onReminderIntent);
    }

    @Override
    public void onTurnOffReminder(Task task) {
        presenter.updateTask(task);
        Intent offReminderIntent = new Intent(MainActivity.this, AlarmService.class);
        offReminderIntent.putExtra("Task ID", task.getTaskID());
        offReminderIntent.putExtra("Turn Off", false);
        startService(offReminderIntent);
    }
}
