package com.umbala.cuongbv.todo.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.umbala.cuongbv.todo.R;
import com.umbala.cuongbv.todo.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    List<Task> tasks;

    public TaskAdapter() {
        tasks = new ArrayList<>();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {

        taskViewHolder.bindTask(tasks.get(i));

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView taskname, taskDescription, reminder;
        ImageView imageView;
        CheckBox checkBox;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            taskname = itemView.findViewById(R.id.taskname);
            taskDescription = itemView.findViewById(R.id.taskdescription);
            reminder = itemView.findViewById(R.id.reminder);
            imageView = itemView.findViewById(R.id.imageView);
            checkBox = itemView.findViewById(R.id.checkBox);

        }

        public void bindTask(Task task) {
            taskname.setText(task.getTaskName());
            taskDescription.setText(task.getTaskContent());
            reminder.setText(task.getRemindDate());

            switch (task.getTaskPriority()){
                case 1:
                    imageView.setImageResource(R.drawable.green_warning);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.yellow_warning);
                    break;
                case 3:
                    imageView.setImageResource(R.drawable.red_waring);
                    break;
            }

            if (task.getTaskDoneState() == 1){
                checkBox.setChecked(true);
            } else checkBox.setChecked(false);

        }
    }

}
