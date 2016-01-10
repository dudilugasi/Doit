package com.example.dudilugasi.doit;

public interface ITaskController {
    void SetTask(String task);
    List<TaskItem> GetTasks();
    void  SetTasks(List<TaskItem> tasks);
    TaskItem GetTaskById(string id);
    void  SetTask(TaskItem task);
}
