package com.example.dudilugasi.doit.bl;


import com.example.dudilugasi.doit.common.TaskItem;

import java.util.List;

public interface TaskUpdateListener {
    void onUpdate(List<TaskItem> list, int code);
}
