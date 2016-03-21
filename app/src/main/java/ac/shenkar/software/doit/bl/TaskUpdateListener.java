package ac.shenkar.software.doit.bl;


import ac.shenkar.software.doit.common.TaskItem;

import java.util.List;

public interface TaskUpdateListener {
    void onUpdate(List<TaskItem> list, int code);
}
