package com.example.dudilugasi.doit.dal;

import com.example.dudilugasi.doit.bl.LoginListener;
import com.example.dudilugasi.doit.bl.TaskListAdapter;
import com.example.dudilugasi.doit.bl.TaskUpdateListener;
import com.example.dudilugasi.doit.common.TaskItem;
import com.example.dudilugasi.doit.common.TeamMember;

import java.util.List;

/**
 * class IDataAccess
 */
public interface IDataAccess {

    void getTasks(int orderbyColumn);

    void getWaitingTasks();

    void getTasksForMember(String member,int orderbyColumn);

    void getWaitingTasksForMember(String member);

    void addTask(TaskItem task);

    void removeTask(TaskItem task);

    void updateTask(TaskItem task);

    void setTaskUpdateListener(TaskUpdateListener listener);
    void updateListeners(List<TaskItem> taskItems, int code);

    void setLoginListener(LoginListener listener);
    void updateLoginListener(int code);

    void login(String username , String password);

}
