package com.example.dudilugasi.doit.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.dudilugasi.doit.R;
import com.example.dudilugasi.doit.bl.ITaskController;
import com.example.dudilugasi.doit.bl.TaskController;
import com.example.dudilugasi.doit.bl.TaskListAdapter;

public class WaitingTasksActivity extends AppCompatActivity {

    private static final String TAG = WaitingTasksActivity.class.getName();
    private ITaskController controller;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private TaskListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_task);
        controller = new TaskController(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.task_list_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (userIsAdmin()) {
            mAdapter = new TaskListAdapter(this, controller.getWaitingTasks(), controller);
        }
        else {
            mAdapter = new TaskListAdapter(this, controller.getWaitingTasksByAssignee(getUserName()), controller);
        }

        mRecyclerView.setAdapter(mAdapter);

    }


    private boolean userIsAdmin() {
        return true;
    }

    private String getUserName() {
        return "dudi";
    }

    public void moveToAllTasks(View view) {
        Intent intent = new Intent(this, AllTasksListActivity.class);
        startActivity(intent);
    }
}

