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
    TaskController(Context context)
    {
        dao = DAO.getInstance(context.getApplicationContext());
        this.context = context;
    }
    @Override   
    public void SetTask(String task) {

    }

    @Override
    public List<TaskItem> GetTasks() {
        return null;
    }

    @Override
    public void SetTasks(List<TaskItem> tasks) {

    }

    @Override
    public TaskItem GetTaskById(String id) {
        return null;
    }

    @Override
    public void SetTask(TaskItem task) {

    }




}
