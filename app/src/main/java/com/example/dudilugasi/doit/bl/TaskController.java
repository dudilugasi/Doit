package com.example.dudilugasi.doit.bl;
import android.content.Context;

import com.example.dudilugasi.doit.common.TaskItem;
import com.example.dudilugasi.doit.dal.DAO;
import com.example.dudilugasi.doit.dal.IDataAccess;
import java.util.ArrayList;
import java.util.List;

public class TaskController implements ITaskController{

    private IDataAccess dao;
    private Context context;
    public TaskController(Context context)
    {
        dao = DAO.getInstance(context.getApplicationContext());
        dao.setTaskUpdateListener((TaskUpdateListener) context);
        this.context = context;
    }

    @Override
    public void getTasks(int orderby) {
        dao.getTasks(orderby);
    }

    @Override
    public TaskItem getTaskById(String id) {
        return null;
    }

    @Override
    public void getWaitingTasks() {
         dao.getWaitingTasks();
    }

    @Override
    public void getTasksByAssignee(String assignee,int orderby) {
         dao.getTasksForMember(assignee,orderby);
    }

    @Override
    public void getWaitingTasksByAssignee(String assignee) {
         dao.getWaitingTasksForMember(assignee);
    }

    @Override
    public void updateTask(TaskItem task) {
        dao.updateTask(task);
    }

    @Override
    public void removeTask(TaskItem task) {
         dao.removeTask(task);
    }

    @Override
    public void addTask(TaskItem task) {
         dao.addTask(task);
    }

    @Override
    public List<TaskItem> checkForNewTasks() {
        List<TaskItem> list = new ArrayList<TaskItem>();
        return list;

    }
}
