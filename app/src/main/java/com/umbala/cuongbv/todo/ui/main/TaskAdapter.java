package com.umbala.cuongbv.todo.ui.main;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.umbala.cuongbv.todo.R;
import com.umbala.cuongbv.todo.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * lớp này có tác dụng là cầu nối giữa danh sách các task cần hiển thị và RecyclerView
 * hay nói cách khác, lớp này sẽ lấy danh sách task để đổ vào view (hiển thị cho người dùng)
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    List<Task> tasks;

    ClickListener clickListener;

    public TaskAdapter() {
        tasks = new ArrayList<>();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * phương thức này để cập nhật danh sách task trên view
     * giả sử danh sách task trên view cần cập nhật bằng cách thay đổi danh sách task
     * thì truyền danh sách task mới vào phương thức này, sau đó danh sách mới sẽ được
     * tự động cập nhật trên RecyclerView
     *
     * @param tasks
     */

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    /**
     * phương thức này sẽ khởi tạo ra một số lượng ViewHolder nhất định phù hợp với
     * kích thước có thể hiển thị của màn hình. ví dụ màn hình có thể hiển thị một lúc 10 item
     * thì phương thức này sẽ sinh ra trên dưới 12 ViewHolder để giữ các view phục vụ cho việc
     * hiển thị của mỗi item. tham khảo thêm {@link TaskViewHolder}
     *
     * @param viewGroup
     * @param i
     * @return
     */
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);

        return new TaskViewHolder(view);
    }

    /**
     * phương thức này sẽ đổ dữ liệu của một task vào ViewHolder
     * ví dụ khi người dùng scroll RecyclerView lên hoặc xuống thì một item bên trong
     * RecyclerView sẽ được kéo ra ngoài và một item bên ngoài RecyclerView sẽ được kéo
     * vào trong RecyclerView, lúc đó phương thức này sẽ được gọi đến để đổ dữ liệu của
     * task được kéo vào trong RecyclerView lên View được giữ bởi ViewHolder tương ứng
     *
     * @param taskViewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {

        taskViewHolder.bindTask(tasks.get(i));

    }

    /**
     * trả về số lượng item trong danh sách task
     * phục vụ cho các phương thức build-in của Adapter
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return tasks.size();
    }

    /**
     * đây là class có nhiều ý nghĩa nhất của RecyclerView
     * class này là các ViewHolder, có tác dụng lưu giữ các View đã được tạo ra bởi
     * Adapter, đổ dữ liệu từ model lên view và tái sử dụng lại các View đã được tạo ra,
     * làm tăng hiệu năng hoạt động của RecyclerView rất nhiều so với ListView
     *
     */
    public class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView taskname, taskDescription, reminder;
        ImageView imageView;
        CheckBox checkBox;
        ConstraintLayout taskItem;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            taskname = itemView.findViewById(R.id.taskname);
            taskDescription = itemView.findViewById(R.id.taskdescription);
            reminder = itemView.findViewById(R.id.reminder);
            imageView = itemView.findViewById(R.id.imageView);
            checkBox = itemView.findViewById(R.id.checkBox);
            taskItem = itemView.findViewById(R.id.taskItem);

        }

        /**
         * tham khảo thêm phương thức onBindViewHolder bên trên
         *
         * @param task
         */
        public void bindTask(final Task task) {
            taskname.setText(task.getTaskName());
            taskDescription.setText(task.getTaskContent());
            reminder.setText(task.getRemindDate());

            taskItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (clickListener != null) {
                        clickListener.onItemLongClick(task);
                    }
                    return false;
                }
            });

            taskItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        clickListener.onItemClicked(task);
                    }
                }
            });

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    boolean ischecked = (task.getTaskDoneState() == 1);
                    if (clickListener != null && ischecked != b) {
                        task.setTaskDoneState(b ? 1 : 0);
                        clickListener.onDoneStateChanged(task);

                    }
                }
            });

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

    interface ClickListener {
        void onItemClicked(Task task);
        void onItemLongClick(Task task);
        void onDoneStateChanged(Task task);
    }
}
