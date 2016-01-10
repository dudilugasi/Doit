package com.example.dudilugasi.doit.dal;

import com.example.dudilugasi.doit.common.TaskItem;

import java.util.List;

/**
 * class IDataAccess
 */
public interface IDateAccess {

    List<TaskItem> GetTasks();

    List<TaskItem> getWaitingTasks();

    List<TaskItem> getTasksForMember(String member);

    List<TaskItem> getWaitingTasksForMember(String member);

    long addTask(TaskItem task);

    int removeTask(TaskItem task);

    int updateTask(TaskItem task);

}
