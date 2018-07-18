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

    ;

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
                        taskTime.setText(simpleDateFormat.format(calendarTime.getTime()));
                    }
                }, 0, 0, true).show();
            }
        });

        taskDate.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                final Calendar calendarDate = Calendar.getInstance();
                int day = calendarDate.get(Calendar.DATE);
                int month = calendarDate.get(Calendar.MONTH);
                int year = calendarDate.get(Calendar.YEAR);
                new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarDate.set(i, i1, i2);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        taskDate.setText(simpleDateFormat.format(calendarDate.getTime()));
                    }
                }, day, month, year).show();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                try {
                    int priority = 0;
                    if (greenButton.isChecked()) priority = 1;
                    if (yellowButton.isChecked()) priority = 2;
                    if (redButton.isChecked()) priority = 3;

                    Task task = new Task.Builder()
                            .setTaskName(taskName.getText().toString())
                            .setTaskContent(taskContent.getText().toString())
                            .setTaskEstimateTime(Double.valueOf(taskEstimateTime.getText().toString()))
                            .setTaskPriority(priority)
                            .setTaskDoneState(0)
                            .setTaskHour(new SimpleDateFormat("HH:mm").parse(taskTime.getText().toString()).getHours())
                            .setTaskMinute(new SimpleDateFormat("HH:mm").parse(taskTime.getText().toString()).getMinutes())
                            .setTaskDay(new SimpleDateFormat("dd/MM/yyyy").parse(taskDate.getText().toString()).getDay())
                            .setTaskMonth(new SimpleDateFormat("dd/MM/yyyy").parse(taskDate.getText().toString()).getMonth())
                            .setTaskYear(new SimpleDateFormat("dd/MM/yyyy").parse(taskDate.getText().toString()).getYear())
                            .builder();

                    presenter.addTask(task);
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
        taskEstimateTime.setText(task.getEstimateTime());
        taskTime.setText(task.getTaskHour() + ":" + task.getTaskMinute());
        taskDate.setText(task.getTaskDay() + "/" + task.getTaskMonth() + "/" + task.getTaskYear());
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
