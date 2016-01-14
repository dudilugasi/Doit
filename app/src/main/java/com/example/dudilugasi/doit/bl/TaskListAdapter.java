package com.example.dudilugasi.doit.bl;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dudilugasi.doit.R;
import com.example.dudilugasi.doit.activities.AllTasksListActivity;
import com.example.dudilugasi.doit.activities.EditTaskActivity;
import com.example.dudilugasi.doit.activities.ReportTaskActivity;
import com.example.dudilugasi.doit.activities.WaitingTasksActivity;
import com.example.dudilugasi.doit.common.Constants;
import com.example.dudilugasi.doit.common.DoitException;
import com.example.dudilugasi.doit.common.ItemClickListener;
import com.example.dudilugasi.doit.common.LoginController;
import com.example.dudilugasi.doit.common.TaskItem;

import java.util.List;

/**
 * TaskListAdapter
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {
    private final ITaskController controller;
    private List<TaskItem> taskItems;
    private Context context;
    private LoginController loginController;

    public TaskListAdapter(Context context ,List<TaskItem> tasks, ITaskController controller) {
        this.taskItems = tasks;
        this.context = context;
        this.controller = controller;
        this.loginController = new LoginController(context);
    }

    @Override
    public TaskListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(context)
                .inflate(R.layout.task_list_view_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TaskListAdapter.ViewHolder holder, int position) {
        TaskItem item = taskItems.get(position);
        holder.mTvTaskName.setText(item.getTaskName());
        holder.mTvAssignee.setText(item.getAssignee());
        holder.mTvCategory.setText(item.getCategory());

        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                TaskItem task = taskItems.get(position);

                if (loginController.isAdmin()) {
                    Intent intent = new Intent(context, EditTaskActivity.class);
                    intent.putExtra(Constants.EDIT_TASK_NAME,task.getTaskName());
                    context.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(context, ReportTaskActivity.class);
                    intent.putExtra(Constants.EDIT_TASK_NAME,task.getTaskName());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        //Each item is a view in the card.
        private TextView mTvTaskName;
        private TextView mTvAssignee;
        private TextView mTvCategory;
        private ItemClickListener clickListener;
        public ViewHolder(View parentView) {
            super(parentView);
            mTvTaskName = (TextView) parentView.findViewById(R.id.task_card_view_name);
            mTvAssignee = (TextView) parentView.findViewById(R.id.task_card_view_assignee);
            mTvCategory = (TextView) parentView.findViewById(R.id.task_card_view_category);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition(), false);

        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onClick(v, getAdapterPosition(), true);
            return true;
        }
    }

    public void add(TaskItem task) throws DoitException{
        if (task == null) {
            throw new DoitException("error");
        }
        else {
            this.taskItems.add(task);
            notifyDataSetChanged();
        }
    }

    public void remove(TaskItem task,int position) throws  DoitException {
        if (task == null) {
            throw new DoitException("error");
        }
        else {
            this.taskItems.remove(task);
            notifyItemRemoved(position);
        }
    }

    public void updateList(List<TaskItem> data) {
        taskItems = data;
        notifyDataSetChanged();
    }


}