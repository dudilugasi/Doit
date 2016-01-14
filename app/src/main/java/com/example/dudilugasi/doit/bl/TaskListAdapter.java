package com.example.dudilugasi.doit.bl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dudilugasi.doit.R;
import com.example.dudilugasi.doit.common.DoitException;
import com.example.dudilugasi.doit.common.TaskItem;

import java.util.List;

/**
 * TaskListAdapter
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {
    private final ITaskController controller;
    private List<TaskItem> taskItems;
    private Context context;

    public TaskListAdapter(Context context ,List<TaskItem> tasks, ITaskController controller) {
        this.taskItems = tasks;
        this.context = context;
        this.controller = controller;
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

    }

    @Override
    public int getItemCount() {
        return taskItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //Each item is a view in the card.
        private TextView mTvTaskName;
        private TextView mTvAssignee;
        private TextView mTvCategory;
        public ViewHolder(View parentView) {
            super(parentView);
            mTvTaskName = (TextView) parentView.findViewById(R.id.task_card_view_name);
            mTvAssignee = (TextView) parentView.findViewById(R.id.task_card_view_assignee);
            mTvCategory = (TextView) parentView.findViewById(R.id.task_card_view_category);
        }
    }

    public void add(TaskItem task) throws DoitException{
        if (task == null) {
            throw new DoitException("error");
        }
        else {
            this.taskItems.add(task);
        }
    }

}