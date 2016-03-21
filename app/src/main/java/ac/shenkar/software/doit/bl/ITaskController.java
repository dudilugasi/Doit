package ac.shenkar.software.doit.bl;

import ac.shenkar.software.doit.common.TaskItem;
import com.parse.ParseFile;

import java.util.List;

public interface ITaskController {
    void getTasks(int orderby);
    void getWaitingTasks();
    void getTasksByAssignee(String assignee, int orderby);
    void getWaitingTasksByAssignee(String assignee);
    void getTaskById(String id);
    void updateTask(TaskItem task,ParseFile file);
    void updateTask(TaskItem task);
    void removeTask(TaskItem task);
    void addTask(TaskItem task);

    List<TaskItem> checkForNewTasks();
}
