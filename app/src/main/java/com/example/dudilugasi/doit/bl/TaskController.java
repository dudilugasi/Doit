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
        this.context = context;
    }

    @Override
    public List<TaskItem> getTasks(int orderby) {
        return dao.getTasks(orderby);
    }

    @Override
    public TaskItem getTaskById(String id) {
        return null;
    }

    @Override
    public List<TaskItem> getWaitingTasks() {
        return dao.getWaitingTasks();
    }

    @Override
    public List<TaskItem> getTasksByAssignee(String assignee,int orderby) {
        return dao.getTasksForMember(assignee,orderby);
    }

    @Override
    public List<TaskItem> getWaitingTasksByAssignee(String assignee) {
        return dao.getWaitingTasksForMember(assignee);
    }

    @Override
    public int updateTask(TaskItem task) {
        return dao.updateTask(task);
    }

    @Override
    public int removeTask(TaskItem task) {
        return dao.removeTask(task);
    }

    @Override
    public long addTask(TaskItem task) {
        return dao.addTask(task);
    }

    @Override
    public List<TaskItem> checkForNewTasks() {
        List<TaskItem> list = new ArrayList<TaskItem>();
        return list;

    }
}
