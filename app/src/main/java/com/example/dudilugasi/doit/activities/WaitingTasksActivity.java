package com.example.dudilugasi.doit.activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dudilugasi.doit.R;
import com.example.dudilugasi.doit.bl.ITaskController;
import com.example.dudilugasi.doit.bl.TaskController;
import com.example.dudilugasi.doit.bl.TaskListAdapter;
import com.example.dudilugasi.doit.common.Constants;
import com.example.dudilugasi.doit.common.LoginController;
import com.example.dudilugasi.doit.common.TaskItem;
import com.example.dudilugasi.doit.dal.DAO;
import com.example.dudilugasi.doit.dialogs.NewTasksDialogFragment;

import java.util.Date;
import java.util.List;

public class WaitingTasksActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener ,SwipeRefreshLayout.OnRefreshListener, NewTasksDialogFragment.NewTaskDialogListener
{

    private static final String TAG = WaitingTasksActivity.class.getName();
    private ITaskController controller;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TaskListAdapter mAdapter;
    private LoginController loginController;
    private String currentTab = "waiting";
    private int currentSortByPosition = 1;
    private long newTaskDialogReturned;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_task);
        controller = new TaskController(this);
        loginController = new LoginController(this);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_waiting_list);
        swipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.task_list_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (loginController.isAdmin()) {
            mAdapter = new TaskListAdapter(this, controller.getWaitingTasks(), controller);



        } else {
            mAdapter = new TaskListAdapter(this, controller.getWaitingTasksByAssignee(loginController.getUserName()), controller);
            //hide add new task button
            ImageButton add_task_button = (ImageButton) findViewById(R.id.add_task_button);
            add_task_button.setVisibility(View.INVISIBLE);
        }

        mRecyclerView.setAdapter(mAdapter);

        updateTasksCount();

        Spinner spinner = (Spinner) findViewById(R.id.arrange_tasks_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arrange_by, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        LinearLayout spinner_layout = (LinearLayout) findViewById(R.id.tasks_sort_spinner_layout);
        spinner_layout.setVisibility(View.GONE);
    }


    /**
     * when click on all task this function will start
     * @param view
     */
    public void moveToAllTasks(View view) {
        if (currentTab.equals("waiting")) {
            currentTab = "all";
            if (loginController.isAdmin()) {
                mAdapter.updateList(controller.getTasks(1));
            } else {
                mAdapter.updateList(controller.getTasksByAssignee(loginController.getUserName(), 1));
            }
            Button button = (Button) findViewById(R.id.all_tasks_button);
            button.setTextColor(Color.WHITE);
            Button button2 = (Button) findViewById(R.id.waiting_list_button);
            button2.setTextColor(Color.BLACK);
            updateTasksCount();
            LinearLayout spinner_layout = (LinearLayout) findViewById(R.id.tasks_sort_spinner_layout);
            spinner_layout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * when click on waiting task tab this function will start
     * @param view
     */
    public void moveToWaitingTasks(View view) {
        if (currentTab.equals("all")) {
            currentTab = "waiting";
            if (loginController.isAdmin()) {
                mAdapter.updateList(controller.getWaitingTasks());
            } else {
                mAdapter.updateList(controller.getWaitingTasksByAssignee(loginController.getUserName()));
            }
            Button button = (Button) findViewById(R.id.all_tasks_button);
            button.setTextColor(Color.BLACK);
            Button button2 = (Button) findViewById(R.id.waiting_list_button);
            button2.setTextColor(Color.WHITE);
            updateTasksCount();
            LinearLayout spinner_layout = (LinearLayout) findViewById(R.id.tasks_sort_spinner_layout);
            spinner_layout.setVisibility(View.GONE);
        }
    }

    /**
     * update the task counter text
     */
    public void updateTasksCount() {
        //set the number of tasks
        int tasksCount = mAdapter.getItemCount();
        TextView taskCount = (TextView) findViewById(R.id.waiting_list_counter);
        taskCount.setText(Integer.toString(tasksCount));
    }

    /**
     * start the activity for add new task when the add task button is clicked
     * @param view
     */
    public void addNewTaskButtonClicked(View view) {
        Intent intent = new Intent(this, EditTaskActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_ADD_NEW_TASK);
    }

    /**
     * when activity is returned with data
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //if returning from adding new task
        if (requestCode == Constants.REQUEST_CODE_ADD_NEW_TASK && resultCode == Activity.RESULT_OK) {
            String category = data.getStringExtra(Constants.NEW_TASK_CATEGORY);
            String priority = data.getStringExtra(Constants.NEW_TASK_PRIORITY);
            String assignee = data.getStringExtra(Constants.NEW_TASK_ASSIGNEE);
            String location = data.getStringExtra(Constants.NEW_TASK_LOCATION);
            String status = data.getStringExtra(Constants.NEW_TASK_STATUS);
            String accept = data.getStringExtra(Constants.NEW_TASK_ACCEPT);
            String name = data.getStringExtra(Constants.NEW_TASK_NAME);
            Date dueDate = (Date) data.getSerializableExtra(Constants.NEW_TASK_DUE_DATE);
            Date created = new Date();

            TaskItem task = new TaskItem(created, category, priority, location, dueDate, assignee, status, accept, name);
            controller.addTask(task);
            try {
                mAdapter.add(task);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        else if (requestCode == Constants.REQUEST_CODE_UPDATE_TASK && resultCode == Activity.RESULT_OK) {
            String category = data.getStringExtra(Constants.NEW_TASK_CATEGORY);
            String priority = data.getStringExtra(Constants.NEW_TASK_PRIORITY);
            String assignee = data.getStringExtra(Constants.NEW_TASK_ASSIGNEE);
            String location = data.getStringExtra(Constants.NEW_TASK_LOCATION);
            String status = data.getStringExtra(Constants.NEW_TASK_STATUS);
            String accept = data.getStringExtra(Constants.NEW_TASK_ACCEPT);
            String name = data.getStringExtra(Constants.NEW_TASK_NAME);
            Date dueDate = (Date) data.getSerializableExtra(Constants.NEW_TASK_DUE_DATE);
            String imageurl = data.getStringExtra(Constants.NEW_TASK_IMAGEURL);
            Date created = new Date();

            TaskItem task = new TaskItem(created, category, priority, location, dueDate, assignee, status, accept, name);
            task.setImageUrl(imageurl);
            controller.updateTask(task);
            try {
                mAdapter.updateTask(task);
            }
            catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * when an item from the spinner is selected
     * @param parent
     * @param view
     * @param pos
     * @param id
     */
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        if (currentSortByPosition != pos) {
            if (loginController.isAdmin()) {
                mAdapter.updateList(controller.getTasks(pos));
            } else {
                mAdapter.updateList(controller.getTasksByAssignee(loginController.getUserName(), pos));
            }
            currentSortByPosition = pos;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        refreshList();
    }

    public void refreshListButtonClicked(View view) {
        refreshList();
    }

    public void refreshList() {
        swipeRefreshLayout.setRefreshing(true);
        List<TaskItem> newTasks =  controller.checkForNewTasks();
        if (newTasks != null) {
            //add the new task to the adapter

            //if there is only one task do dialog
            if (newTasks.size() == 1) {
                NewTasksDialogFragment newTasksDialogFragment = new NewTasksDialogFragment();
                newTasksDialogFragment.show(getFragmentManager(),"newTaskDialog");
                newTaskDialogReturned = newTasks.get(0).getId();
            }
            //if there is more then one task mark them
        }
        else {
            Toast.makeText(this,"no new tasks",Toast.LENGTH_SHORT).show();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Intent intent = new Intent(this, ReportTaskActivity.class);
        intent.putExtra(Constants.EDIT_TASK_ID,newTaskDialogReturned);
        startActivityForResult(intent, Constants.REQUEST_CODE_UPDATE_TASK);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}



