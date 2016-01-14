package com.example.dudilugasi.doit.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dudilugasi.doit.common.TaskItem;

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
    private TasksDbHelper dbHelper;
    private String[] tasksColumns = { TasksDbContract.TaskEntry._ID,
            TasksDbContract.TaskEntry.COLUMN_TASK_ASSIGNEE,
            TasksDbContract.TaskEntry.COLUMN_TASK_CATEGORY,
            TasksDbContract.TaskEntry.COLUMN_TASK_STATUS,
            TasksDbContract.TaskEntry.COLUMN_TASK_PRIORITY,
            TasksDbContract.TaskEntry.COLUMN_TASK_NAME,
            TasksDbContract.TaskEntry.COLUMN_TASK_CREATED,
            TasksDbContract.TaskEntry.COLUMN_TASK_DUETIME,
            TasksDbContract.TaskEntry.COLUMN_TASK_IMAGEURL,
            TasksDbContract.TaskEntry.COLUMN_TASK_LOCATION,
            TasksDbContract.TaskEntry.COLUMN_TASK_ACCEPT

    };


    private DAO(Context context) {
        this.context = context;
        dbHelper = new TasksDbHelper(this.context);
    }
    public static DAO getInstance(Context applicationContext)
    {
        if(instance ==  null)
            instance = new DAO(applicationContext);
        return instance;
    }

    @Override
    public int updateTask(TaskItem task)  {

        SQLiteDatabase database = null;
        try {
            database = dbHelper.getWritableDatabase();
            if (task == null) {
                return 0;
            }
            ContentValues values = new ContentValues();
            values.put(TasksDbContract.TaskEntry.COLUMN_TASK_ACCEPT , task.getAccept());
            values.put(TasksDbContract.TaskEntry.COLUMN_TASK_ASSIGNEE , task.getAssignee());
            values.put(TasksDbContract.TaskEntry.COLUMN_TASK_CATEGORY , task.getCategory());
            values.put(TasksDbContract.TaskEntry.COLUMN_TASK_CREATED , formatter.format(task.getCreated()));
            values.put(TasksDbContract.TaskEntry.COLUMN_TASK_DUETIME , formatter.format(task.getDueTime()));
            values.put(TasksDbContract.TaskEntry.COLUMN_TASK_IMAGEURL , task.getImageUrl());
            values.put(TasksDbContract.TaskEntry.COLUMN_TASK_LOCATION , task.getLocation());
            values.put(TasksDbContract.TaskEntry.COLUMN_TASK_STATUS , task.getStatus());
            values.put(TasksDbContract.TaskEntry.COLUMN_TASK_NAME , task.getTaskName());
            values.put(TasksDbContract.TaskEntry.COLUMN_TASK_PRIORITY, task.getPriority());

            return database.update(TasksDbContract.TaskEntry.TABLE_NAME,
                    values,TasksDbContract.TaskEntry._ID + " = ?",
                    new String[]{String.valueOf(task.getId())});
        }

        finally {
            if (database != null) {
                database.close();
            }
        }

    }

    @Override
    public int removeTask(TaskItem task) {
        SQLiteDatabase database = null;
        try {
            database = dbHelper.getWritableDatabase();
            if (task == null) {
                return 0;
            }
            return database.delete(TasksDbContract.TaskEntry.TABLE_NAME,
                    TasksDbContract.TaskEntry._ID + " = ?" ,new String[] {String.valueOf(task.getId())});
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public long addTask(TaskItem task) {
        SQLiteDatabase database = null;
        try {
            database = dbHelper.getReadableDatabase();
            if (task == null) {
                return 0;
            }
            ContentValues values = new ContentValues();
            values.put(TasksDbContract.TaskEntry.COLUMN_TASK_ACCEPT , task.getAccept());
            values.put(TasksDbContract.TaskEntry.COLUMN_TASK_ASSIGNEE , task.getAssignee());
            values.put(TasksDbContract.TaskEntry.COLUMN_TASK_CATEGORY , task.getCategory());
            values.put(TasksDbContract.TaskEntry.COLUMN_TASK_CREATED , formatter.format(task.getCreated()));
            values.put(TasksDbContract.TaskEntry.COLUMN_TASK_DUETIME , formatter.format(task.getDueTime()));
            values.put(TasksDbContract.TaskEntry.COLUMN_TASK_IMAGEURL , task.getImageUrl());
            values.put(TasksDbContract.TaskEntry.COLUMN_TASK_LOCATION , task.getLocation());
            values.put(TasksDbContract.TaskEntry.COLUMN_TASK_CATEGORY , task.getCategory());
            values.put(TasksDbContract.TaskEntry.COLUMN_TASK_STATUS , task.getStatus());
            values.put(TasksDbContract.TaskEntry.COLUMN_TASK_NAME , task.getTaskName());
            values.put(TasksDbContract.TaskEntry.COLUMN_TASK_PRIORITY, task.getPriority());
            return database.insert(TasksDbContract.TaskEntry.TABLE_NAME, null, values);
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public List<TaskItem> getTasks() {
        SQLiteDatabase database = null;
        try {
            List<TaskItem> tasks = new ArrayList<>();
            database = dbHelper.getReadableDatabase();
            Cursor cursor = database.query(TasksDbContract.TaskEntry.TABLE_NAME,tasksColumns,
                    null,null,null,null,null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                TaskItem t = cursorToTask(cursor);
                tasks.add(t);
                cursor.moveToNext();
            }

            cursor.close();
            return tasks;
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public List<TaskItem> getWaitingTasks() {
        SQLiteDatabase database = null;
        try {
            List<TaskItem> tasks = new ArrayList<>();
            database = dbHelper.getReadableDatabase();
            Cursor cursor = database.query(TasksDbContract.TaskEntry.TABLE_NAME,tasksColumns,
                    TasksDbContract.TaskEntry.COLUMN_TASK_STATUS + " = 'waiting'",null,null,null,null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                TaskItem t = cursorToTask(cursor);
                tasks.add(t);
                cursor.moveToNext();
            }
            cursor.close();
            return tasks;
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public List<TaskItem> getTasksForMember(String member) {
        SQLiteDatabase database = null;
        try {
            List<TaskItem> tasks = new ArrayList<>();
            database = dbHelper.getReadableDatabase();
            Cursor cursor = database.query(TasksDbContract.TaskEntry.TABLE_NAME,tasksColumns,
                    TasksDbContract.TaskEntry.COLUMN_TASK_ASSIGNEE + " = '" + member + "'",null,null,null,null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                TaskItem t = cursorToTask(cursor);
                tasks.add(t);
                cursor.moveToNext();
            }
            cursor.close();
            return tasks;
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public List<TaskItem> getWaitingTasksForMember(String member) {
        SQLiteDatabase database = null;
        try {
            List<TaskItem> tasks = new ArrayList<>();
            database = dbHelper.getReadableDatabase();
            Cursor cursor = database.query(TasksDbContract.TaskEntry.TABLE_NAME,tasksColumns,
                    TasksDbContract.TaskEntry.COLUMN_TASK_ASSIGNEE + " = '" + member + "'"
                            + " and " + TasksDbContract.TaskEntry.COLUMN_TASK_STATUS + " = 'waiting'",null,null,null,null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                TaskItem t = cursorToTask(cursor);
                tasks.add(t);
                cursor.moveToNext();
            }
            cursor.close();
            return tasks;
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    private TaskItem cursorToTask(Cursor cursor) {
        TaskItem t = new TaskItem();
        t.setId(cursor.getInt(cursor.getColumnIndex(TasksDbContract.TaskEntry._ID)));

        t.setAccept(cursor.getString(cursor.getColumnIndex(TasksDbContract.TaskEntry.COLUMN_TASK_ACCEPT)));

        t.setAssignee(cursor.getString(cursor.getColumnIndex(TasksDbContract.TaskEntry.COLUMN_TASK_ASSIGNEE)));

        t.setCategory(cursor.getString(cursor.getColumnIndex(TasksDbContract.TaskEntry.COLUMN_TASK_CATEGORY)));

        t.setDueTime(new Date(cursor.getLong(cursor.getColumnIndex(TasksDbContract.TaskEntry.COLUMN_TASK_DUETIME)) * 1000));

        t.setCreated(new Date(cursor.getLong(cursor.getColumnIndex(TasksDbContract.TaskEntry.COLUMN_TASK_DUETIME)) * 1000));

        t.setImageUrl(cursor.getString(cursor.getColumnIndex(TasksDbContract.TaskEntry.COLUMN_TASK_IMAGEURL)));

        t.setLocation(cursor.getString(cursor.getColumnIndex(TasksDbContract.TaskEntry.COLUMN_TASK_LOCATION)));

        t.setPriority(cursor.getString(cursor.getColumnIndex(TasksDbContract.TaskEntry.COLUMN_TASK_PRIORITY)));

        t.setStatus(cursor.getString(cursor.getColumnIndex(TasksDbContract.TaskEntry.COLUMN_TASK_STATUS)));

        t.setTaskName(cursor.getString(cursor.getColumnIndex(TasksDbContract.TaskEntry.COLUMN_TASK_NAME)));

        return t;
    }
}
