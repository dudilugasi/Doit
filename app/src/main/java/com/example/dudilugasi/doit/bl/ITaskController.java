package com.example.dudilugasi.doit.bl;

import com.example.dudilugasi.doit.common.TaskItem;
import java.util.List;

public interface ITaskController {
    List<TaskItem> getTasks(int orderby);
    List<TaskItem> getWaitingTasks();
    List<TaskItem> getTasksByAssignee(String assignee, int orderby);
    List<TaskItem> getWaitingTasksByAssignee(String assignee);
    TaskItem getTaskById(String id);
    int updateTask(TaskItem task);
    int removeTask(TaskItem task);
    long addTask(TaskItem task);

    List<TaskItem> checkForNewTasks();
}
