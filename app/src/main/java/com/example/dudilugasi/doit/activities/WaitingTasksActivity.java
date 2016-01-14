package com.example.dudilugasi.doit.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dudilugasi.doit.R;
import com.example.dudilugasi.doit.bl.ITaskController;
import com.example.dudilugasi.doit.bl.TaskController;
import com.example.dudilugasi.doit.bl.TaskListAdapter;
import com.example.dudilugasi.doit.common.LoginController;
import com.example.dudilugasi.doit.common.TaskItem;
import com.example.dudilugasi.doit.dal.DAO;

import java.util.Date;
import java.util.List;

public class WaitingTasksActivity extends AppCompatActivity {

    private static final String TAG = WaitingTasksActivity.class.getName();
    private ITaskController controller;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private TaskListAdapter mAdapter;
    private LoginController loginController;
    private String currentTab = "waiting";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_task);
        controller = new TaskController(this);
        loginController = new LoginController(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.task_list_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (loginController.isAdmin()) {
            mAdapter = new TaskListAdapter(this, controller.getWaitingTasks(), controller);
        }
        else {
            mAdapter = new TaskListAdapter(this, controller.getWaitingTasksByAssignee(loginController.getUserName()), controller);
        }

        mRecyclerView.setAdapter(mAdapter);

        updateTasksCount();

    }




    public void moveToAllTasks(View view) {
        if (currentTab.equals("waiting")) {
            currentTab = "all";
            if (loginController.isAdmin()) {
                mAdapter.updateList(controller.getTasks());
            }
            else {
                mAdapter.updateList(controller.getTasksByAssignee(loginController.getUserName()));
            }
            Button button = (Button) findViewById(R.id.all_tasks_button);
            button.setTextColor(Color.WHITE);
            Button button2 = (Button) findViewById(R.id.waiting_list_button);
            button2.setTextColor(Color.BLACK);
            updateTasksCount();
        }
    }
    public void moveToWaitingTasks(View view) {
        if (currentTab.equals("all")) {
            currentTab = "waiting";
            if (loginController.isAdmin()) {
                mAdapter.updateList(controller.getWaitingTasks());
            }
            else {
                mAdapter.updateList(controller.getWaitingTasksByAssignee(loginController.getUserName()));
            }
            Button button = (Button) findViewById(R.id.all_tasks_button);
            button.setTextColor(Color.BLACK);
            Button button2 = (Button) findViewById(R.id.waiting_list_button);
            button2.setTextColor(Color.WHITE);
            updateTasksCount();
        }
    }
    public void updateTasksCount() {
        //set the number of tasks
        int tasksCount = mAdapter.getItemCount();
        TextView taskCount = (TextView) findViewById(R.id.waiting_list_counter);
        taskCount.setText(Integer.toString(tasksCount));
    }
}

