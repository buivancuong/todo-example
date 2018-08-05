package com.umbala.cuongbv.todo.ui.edit;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;

import com.umbala.cuongbv.todo.R;
import com.umbala.cuongbv.todo.data.TaskRepo;
import com.umbala.cuongbv.todo.model.Task;
import com.umbala.cuongbv.todo.ui.main.AlarmService;
import com.umbala.cuongbv.todo.ui.main.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity implements EditContractor.View {

    private static String TASK = "TASK";
    Presenter presenter;

    EditText taskName, taskContent, taskEstimateTime, taskTime, taskDate;
    RadioButton greenButton, yellowButton, redButton;
    Button okButton, cancelButton;

    public static Intent getStartIntent(Context context, Task task) {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra(TASK, task);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        presenter = new Presenter(this, TaskRepo.getInstance(this));

        taskName = findViewById(R.id.textViewTaskName);
        taskContent = findViewById(R.id.textViewTaskContent);
        taskEstimateTime = findViewById(R.id.textViewEstimateTime);
        taskTime = findViewById(R.id.textViewTime);
        taskDate = findViewById(R.id.textViewDate);
        greenButton = findViewById(R.id.radioButtonGreen);
        yellowButton = findViewById(R.id.radioButtonYellow);
        redButton = findViewById(R.id.radioButtonRed);
        okButton = findViewById(R.id.buttonOK);
        cancelButton = findViewById(R.id.buttonCancel);

        findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        taskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendarTime = Calendar.getInstance();
                new TimePickerDialog(EditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        calendarTime.set(1995, 8, 2, i, i1);
                        presenter.setHour(i);
                        presenter.setMinute(i1);
                        taskTime.setText(simpleDateFormat.format(calendarTime.getTime()));
                    }
                }, calendarTime.get(Calendar.HOUR_OF_DAY), calendarTime.get(Calendar.MINUTE), true).show();
            }
        });

        taskDate.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                final Calendar calendarDate = Calendar.getInstance();
                new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        calendarDate.set(i, i1, i2);
                        presenter.setYear(i);
                        presenter.setMonth(i1);
                        presenter.setDay(i2);
                        taskDate.setText(simpleDateFormat.format(calendarDate.getTime()));
                    }
                }, calendarDate.get(Calendar.YEAR), calendarDate.get(Calendar.MONTH), calendarDate.get(Calendar.DATE)).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int priority = 0;
                    if (greenButton.isChecked()) priority = 1;
                    if (yellowButton.isChecked()) priority = 2;
                    if (redButton.isChecked()) priority = 3;
                    Calendar calendar = Calendar.getInstance();

                    if (presenter.getTask() == null) {
                        Task task = new Task.Builder()
                                .setTaskName(taskName.getText().toString())
                                .setTaskContent(taskContent.getText().toString())
                                .setTaskEstimateTime(Double.valueOf(taskEstimateTime.getText().toString()))
                                .setTaskPriority(priority)
                                .setTaskDoneState(0)
                                .setIsReminder(0)
                                .setTaskHour(presenter.getHour())
                                .setTaskMinute(presenter.getMinute())
                                .setTaskDay(presenter.getDay())
                                .setTaskMonth(presenter.getMonth())
                                .setTaskYear(presenter.getYear())
                                .builder();

                        calendar.set(presenter.getYear(),
                                presenter.getMonth(),
                                presenter.getDay(),
                                presenter.getHour(),
                                presenter.getMinute(),
                                0);

                        presenter.addTask(task);

                    } else {
                        presenter.getTask().setTaskName(taskName.getText().toString());
                        presenter.getTask().setTaskContent(taskContent.getText().toString());
                        presenter.getTask().setTaskEstimateTime(Double.valueOf(taskEstimateTime.getText().toString()));
                        presenter.getTask().setTaskPriority(priority);
                        presenter.getTask().setTaskDoneState(0);
                        presenter.getTask().setIsReminder(0);
                        if (presenter.getHour() != 0) presenter.getTask().setTaskHour(presenter.getHour());
                        if (presenter.getMinute() != 0) presenter.getTask().setTaskMinute(presenter.getMinute());
                        if (presenter.getDay() != 0) presenter.getTask().setTaskDay(presenter.getDay());
                        if (presenter.getMonth() != 0) presenter.getTask().setTaskMonth(presenter.getMonth());
                        if (presenter.getYear() != 0) presenter.getTask().setTaskYear(presenter.getYear());

                        calendar.set(presenter.getTask().getTaskYear(),
                                presenter.getTask().getTaskMonth(),
                                presenter.getTask().getTaskDay(),
                                presenter.getTask().getTaskHour(),
                                presenter.getTask().getTaskMinute(),
                                0);

                        presenter.addTask(presenter.getTask()); // addTask la them getTask vao DB

                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Task task = getIntent().getParcelableExtra(TASK);
        presenter.setTask(task);

    }

    @Override
    public void showEditTask(@NonNull Task task) {
        taskName.setText(task.getTaskName());
        taskContent.setText(task.getTaskContent());
        taskEstimateTime.setText(task.getEstimateTime().toString());
        taskTime.setText(task.getTaskHour() + ":" + task.getTaskMinute());
        taskDate.setText(task.getTaskDay() + "/" + (task.getTaskMonth() + 1) + "/" + task.getTaskYear());
        switch (task.getTaskPriority()) {
            case 1:
                greenButton.setChecked(true);
                break;
            case 2:
                yellowButton.setChecked(true);
                break;
            case 3:
                redButton.setChecked(true);
                break;
        }
    }

    @Override
    public void exitActivity() {
        finish();
    }

}
