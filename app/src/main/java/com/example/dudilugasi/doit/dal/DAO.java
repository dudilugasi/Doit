package com.example.dudilugasi.doit.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dudilugasi.doit.bl.TaskListAdapter;
import com.example.dudilugasi.doit.bl.TaskUpdateListener;
import com.example.dudilugasi.doit.common.Constants;
import com.example.dudilugasi.doit.common.DoitException;
import com.example.dudilugasi.doit.common.TaskItem;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class DAO
 */
public class DAO implements IDataAccess {

    SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy hh:mm:ss a");

    private static DAO instance;
    private Context context;
    private ArrayList<TaskUpdateListener> listeners = new ArrayList<TaskUpdateListener>();

    private DAO(Context context) {
        this.context = context;
    }

    public static DAO getInstance(Context applicationContext) {
        if (instance == null)
            instance = new DAO(applicationContext);
        return instance;
    }

    @Override
    public void updateTask(final TaskItem task) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Task");
        query.getInBackground(task.getId(), new GetCallback<ParseObject>() {
            public void done(final ParseObject po, ParseException e) {
                if (e == null) {
                    taskToParseObject(po, task);
                    po.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            task.setId(po.getObjectId());
                            List<TaskItem> list = new ArrayList<TaskItem>();
                            list.add(task);
                            updateListeners(list, Constants.TASK_UPDATE_LISTENER_CODE_ALL_UPDATE);
                        }
                    });
                }
            }
        });

    }

    @Override
    public void removeTask(TaskItem task) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Task");
        query.getInBackground(task.getId(), new GetCallback<ParseObject>() {
            public void done(final ParseObject po, ParseException e) {
                if (e == null) {
                    po.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            TaskItem t = new TaskItem();
                            t.setId(po.getObjectId());
                            List<TaskItem> list = new ArrayList<TaskItem>();
                            list.add(t);
                            updateListeners(list, Constants.TASK_UPDATE_LISTENER_CODE_ALL_DELETE);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void addTask(final TaskItem task) {
        Log.i("addtask","got to add Task");
        final ParseObject po = new ParseObject("Task");
        taskToParseObject(po, task);
        po.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.i("addtask", "done");
                task.setId(po.getObjectId());
                List<TaskItem> list = new ArrayList<TaskItem>();
                list.add(task);
                updateListeners(list, Constants.TASK_UPDATE_LISTENER_CODE_ALL_ADD);
            }
        });
    }

    @Override
    public void getTasks(int orderbyColumn) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Task");
        query.orderByDescending(getOrderByColumnString(orderbyColumn));
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> taskList, ParseException e) {
                if (e == null) {

                    List<TaskItem> tasks = new ArrayList<>();

                    for (int i = 0; i < taskList.size(); i++) {
                        TaskItem task = parseObjectToTask(taskList.get(i));
                        tasks.add(task);
                    }
                    updateListeners(tasks, Constants.TASK_UPDATE_LISTENER_CODE_ALL_ITEMS);

                } else {

                }
            }
        });
    }

    @Override
    public void getWaitingTasks() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Task");
        query.orderByDescending(getOrderByColumnString(0));
        query.whereEqualTo("status","waiting");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> taskList, ParseException e) {
                if (e == null) {

                    List<TaskItem> tasks = new ArrayList<>();

                    for (int i = 0; i < taskList.size(); i++) {
                        TaskItem task = parseObjectToTask(taskList.get(i));
                        tasks.add(task);
                    }
                    updateListeners(tasks, Constants.TASK_UPDATE_LISTENER_CODE_ALL_ITEMS);

                } else {

                }
            }
        });
    }

    @Override
    public void getTasksForMember(String member, int orderbyColumn) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Task");
        query.orderByDescending(getOrderByColumnString(orderbyColumn));
        query.whereEqualTo("assignee",member);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> taskList, ParseException e) {
                if (e == null) {

                    List<TaskItem> tasks = new ArrayList<>();

                    for (int i = 0; i < taskList.size(); i++) {
                        TaskItem task = parseObjectToTask(taskList.get(i));
                        tasks.add(task);
                    }
                    updateListeners(tasks, Constants.TASK_UPDATE_LISTENER_CODE_ALL_ITEMS);

                } else {

                }
            }
        });
    }

    @Override
    public void getWaitingTasksForMember(String member) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Task");
        query.orderByDescending(getOrderByColumnString(0));
        query.whereEqualTo("status", "waiting");
        query.whereEqualTo("assignee",member);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> taskList, ParseException e) {
                if (e == null) {

                    List<TaskItem> tasks = new ArrayList<>();

                    for (int i = 0; i < taskList.size(); i++) {
                        TaskItem task = parseObjectToTask(taskList.get(i));
                        tasks.add(task);
                    }
                    updateListeners(tasks, Constants.TASK_UPDATE_LISTENER_CODE_ALL_ITEMS);

                } else {

                }
            }
        });
    }

    private TaskItem cursorToTask(Cursor cursor) {
        return new TaskItem();
    }

    private TaskItem parseObjectToTask(ParseObject object) {
        TaskItem t = new TaskItem();
        t.setId(object.getObjectId());
        t.setAccept(object.getString("accept"));
        t.setCategory(object.getString("category"));
        t.setAssignee(object.getString("assignee"));
        t.setDueTime(object.getDate("dueTime"));
        t.setImageUrl(object.getString("imageUrl"));
        t.setLocation(object.getString("location"));
        t.setPriority(object.getInt("priority"));
        t.setStatus(object.getString("status"));
        t.setTaskName(object.getString("name"));

        return t;
    }

    private void taskToParseObject(ParseObject po, TaskItem task) {
        po.put("accept", task.getAccept());
        po.put("assignee", task.getAssignee());
        po.put("category", task.getCategory());
        po.put("dueTime", task.getDueTime());
        po.put("imageUrl", task.getImageUrl());
        po.put("location", task.getLocation());
        po.put("status", task.getStatus());
        po.put("priority", task.getPriority());
        po.put("name", task.getTaskName());

    }

    private String getOrderByColumnString(int orderbyCode) {
        switch (orderbyCode) {
            case 1:
                return "priority";
            case 2:
                return "status";
            default:
                return "dueTime";
        }
    }

    public void setTaskUpdateListener(TaskUpdateListener listener) {
        this.listeners.add(listener);
    }

    public void updateListeners(List<TaskItem> taskItems, int code) {
        for (TaskUpdateListener listener : this.listeners) {
            listener.onUpdate(taskItems, code);
        }
    }
}
