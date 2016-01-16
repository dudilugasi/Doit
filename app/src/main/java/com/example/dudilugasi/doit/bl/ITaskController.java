package com.example.dudilugasi.doit.bl;

import com.example.dudilugasi.doit.common.TaskItem;
import java.util.List;

public interface ITaskController {
    void getTasks(int orderby);
    void getWaitingTasks();
    void getTasksByAssignee(String assignee, int orderby);
    void getWaitingTasksByAssignee(String assignee);
    TaskItem getTaskById(String id);
    void updateTask(TaskItem task);
    void removeTask(TaskItem task);
    void addTask(TaskItem task);

    List<TaskItem> checkForNewTasks();
}
